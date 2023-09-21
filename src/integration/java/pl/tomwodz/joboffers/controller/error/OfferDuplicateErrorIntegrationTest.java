package pl.tomwodz.joboffers.controller.error;

import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.ResultActions;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;
import pl.tomwodz.joboffers.BaseIntegrationTest;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class OfferDuplicateErrorIntegrationTest extends BaseIntegrationTest {

    @Container
    public static  final MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:4.0.1"));

    @DynamicPropertySource
    public static  void propertyOverride(DynamicPropertyRegistry registry){
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
        registry.add("job-offers.client-offer.http.client.config.uri", () -> WIRE_MOCK_HOST);
        registry.add("job-offers.client-offer.http.client.config.port", () -> wireMockServer.getPort());
    }

    @Test
    public void ShouldReturnConflict409WhenAddedOfferWithUrlExists() throws Exception {

        //step 1: saved offer
        //given && when
        String postUrl = "/offers";
        ResultActions performSavedFirstOffer = mockMvc.perform(post(postUrl).
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
        performSavedFirstOffer.andExpect(status().isCreated());

        //step 2: try save offer with the same url
        //given && when
        ResultActions performSavedSecondOffer = mockMvc.perform(post(postUrl).
                content("""
                    {
                    "companyName": "Java (CMS) Developer test",
                    "position": "Efigence SA test ",
                    "salary": "10 000 - 18 000 PLN",
                    "offerUrl": "https://nofluffjobs.com/pl/job/java-cms-developer-efigence-warszawa-b4qs8loh"
                   }
                       """)
                .contentType(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
        );
        //then
        performSavedSecondOffer.andExpect(status().isConflict());

    }


}
