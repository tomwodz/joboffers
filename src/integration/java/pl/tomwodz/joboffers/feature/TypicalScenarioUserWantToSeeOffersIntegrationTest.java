package pl.tomwodz.joboffers.feature;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import pl.tomwodz.joboffers.BaseIntegrationTest;
import pl.tomwodz.joboffers.domain.clientoffer.ClientOfferQuery;
import pl.tomwodz.joboffers.domain.clientoffer.dto.JobOfferResponse;
import pl.tomwodz.joboffers.domain.offer.dto.OfferResponseDto;
import pl.tomwodz.joboffers.infrastructure.offer.HttpOffersScheduler;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TypicalScenarioUserWantToSeeOffersIntegrationTest
        extends BaseIntegrationTest
        implements SampleResponseIntegrationTest {

  @Autowired
    ClientOfferQuery clientOfferQuery;

  @Autowired
  HttpOffersScheduler httpOffersScheduler;

  @Test
  public void UserShouldBe() throws Exception {
    //step 1: there are no offers in external HTTP server
    //given
    wireMockServer.stubFor(WireMock.get("/offers")
            .willReturn(WireMock.aResponse()
                    .withStatus(HttpStatus.OK.value())
                    .withHeader("Content-Type", "application/json;charset=UTF-8")
                    .withBody(bodyWithZeroOffersJson())));

    //when
    List<JobOfferResponse> expected = clientOfferQuery.fetchOffers();

    //then
    assertThat(expected.size()).isEqualTo(0);

    //step 2: scheduler ran 1st time and made GET to external server and system added 0 offers to database
    //given
    //when
    List<OfferResponseDto> expectedList = httpOffersScheduler.fetchAllOffersAndSaveAllIfNotExists();
    //then
    assertThat(expectedList).isEmpty();

    //step 7: user made GET /offers with header “Authorization: Bearer AAAA.BBBB.CCC” and system returned OK(200) with 0 offers
    //given
    String offersUrl = "/offers";

    //when
    ResultActions performGetOffers = mockMvc.perform(get(offersUrl)
            .contentType(MediaType.APPLICATION_JSON));

    //then
    MvcResult mvcResultStep7 = performGetOffers.andExpect(status().isOk()).andReturn();
    String resultWithOffers = mvcResultStep7.getResponse().getContentAsString();
    List<OfferResponseDto> offers = objectMapper.readValue(resultWithOffers, new TypeReference<>() {});
    assertThat(offers).isEmpty();
    //assertThat(offers).hasSize(3);

    //step 11: user made GET /offers/9999 and system returned NOT_FOUND(404) with message “Offer with id 9999 not found”
    //given
    String offersUrlWithId = "/offers/9999";

    //when
    ResultActions performGetOffersNotExistingId = mockMvc.perform(get(offersUrlWithId)
            .contentType(MediaType.APPLICATION_JSON));
    //then
    performGetOffersNotExistingId.andExpect(status().isNotFound())
            .andExpect(content().json(
                    """
                    {
                    "message": "Offer not found: 9999",
                    "httpStatus": "NOT_FOUND"
                    }
                    """.trim()));


    //step 16: user made POST /offers with header “Authorization: Bearer AAAA.BBBB.CCC” and offer as body and system returned CREATED(201) with saved offer
    //given
    String postUrl = "/offers";

    //when
    ResultActions performPostOffer = mockMvc.perform(post(postUrl).
            content("""
                    {
                    "companyName": "Java (CMS) Developer",
                    "position": "Efigence SA",
                    "salary": "16 000 - 18 000 PLN",
                    "offerUrl": "https://nofluffjobs.com/pl/job/java-cms-developer-efigence-warszawa-b4qs8loh"
                   }
                       """)
            .contentType(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
    );

    //then
    String createdOfferResponse = performPostOffer.andExpect(status().isCreated())
            .andReturn()
            .getResponse()
            .getContentAsString();

    OfferResponseDto parsedCreatedOfferResponse = objectMapper.readValue(createdOfferResponse, new TypeReference<>() {});
    String id = parsedCreatedOfferResponse.id();
    assertAll(
            () ->  assertThat(id).isNotNull(),
            () -> assertThat(parsedCreatedOfferResponse.companyName()).isEqualTo("Java (CMS) Developer"),
            () -> assertThat(parsedCreatedOfferResponse.position()).isEqualTo("Efigence SA"),
            () -> assertThat(parsedCreatedOfferResponse.salary()).isEqualTo("16 000 - 18 000 PLN"),
            () -> assertThat(parsedCreatedOfferResponse.offerUrl()).isEqualTo("https://nofluffjobs.com/pl/job/java-cms-developer-efigence-warszawa-b4qs8loh")
    );

    //step 17: user made GET /offers with header “Authorization: Bearer AAAA.BBBB.CCC” and system returned OK(200) with 5 offers (1 offer)
    //given
    String getUrl = "/offers";
    //when
    ResultActions performGetOneOffers = mockMvc.perform(get(getUrl)
            .contentType(MediaType.APPLICATION_JSON_VALUE));

    //then
    String oneOffersResponse = performGetOneOffers.andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();
    List<OfferResponseDto> parsedOneOffersResponse =objectMapper.readValue(oneOffersResponse,new TypeReference<>() {});
    assertThat(parsedOneOffersResponse).hasSize(1);


  }

  @Test
  public void ShouldBeReturnThreeResponseWhenConnectedToExternal() throws Exception {

    //given
    wireMockServer.stubFor(WireMock.get("/offers")
            .willReturn(WireMock.aResponse()
                    .withStatus(HttpStatus.OK.value())
                    .withHeader("Content-Type", "application/json;charset=UTF-8")
                    .withBody(bodyWithThreeOffersJson())));

    //when
    List<OfferResponseDto> expectedList = httpOffersScheduler.fetchAllOffersAndSaveAllIfNotExists();

    //then
    assertThat(expectedList.size()).isEqualTo(3);

  }


  //step 3: user tried to get JWT token by requesting POST /token with username=someUser, password=somePassword and system returned UNAUTHORIZED(401)
  //step 4: user made GET /offers with no jwt token and system returned UNAUTHORIZED(401)
  //step 5: user made POST /register with username=someUser, password=somePassword and system registered user with status OK(200)
  //step 6: user tried to get JWT token by requesting POST /token with username=someUser, password=somePassword and system returned OK(200) and jwttoken=AAAA.BBBB.CCC
  //step 8: there are 2 new offers in external HTTP server
  //step 9: scheduler ran 2nd time and made GET to external server and system added 2 new offers with ids: 1000 and 2000 to database
  //step 10: user made GET /offers with header “Authorization: Bearer AAAA.BBBB.CCC” and system returned OK(200) with 2 offers with ids: 1000 and 2000

  //step 12: user made GET /offers/1000 and system returned OK(200) with offer
  //step 13: there are 2 new offers in external HTTP server
  //step 14: scheduler ran 3rd time and made GET to external server and system added 2 new offers with ids: 3000 and 4000 to database
  //step 15: user made GET /offers with header “Authorization: Bearer AAAA.BBBB.CCC” and system returned OK(200) with 4 offers with ids: 1000,2000, 3000 and 4000




}
