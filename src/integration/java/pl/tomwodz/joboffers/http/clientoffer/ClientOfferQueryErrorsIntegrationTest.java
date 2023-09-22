package pl.tomwodz.joboffers.http.clientoffer;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.http.Fault;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import pl.tomwodz.joboffers.domain.clientoffer.ClientOfferQuery;
import pl.tomwodz.joboffers.infrastructure.clientoffer.http.ClientOfferConfiguration;
import pl.tomwodz.joboffers.infrastructure.clientoffer.http.ClientOfferRestTemplateConfigurationProperties;
import wiremock.org.apache.hc.core5.http.HttpStatus;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;

public class ClientOfferQueryErrorsIntegrationTest extends ClientOfferConfiguration {

    public static final String CONTENT_TYPE_HEADER_KEY = "Content-Type";
    public static final String APPLICATION_JSON_CONTENT_TYPE_VALUE = "application/json";

    @RegisterExtension
    public static WireMockExtension wireMockServer = WireMockExtension.newInstance()
            .options(wireMockConfig().dynamicPort())
            .build();


    public ClientOfferQuery remoteClientOfferQuery() {
        ClientOfferRestTemplateConfigurationProperties properties = ClientOfferRestTemplateConfigurationProperties
                .builder()
                .uri("http://localhost")
                .port(wireMockServer.getPort())
                .readTimeout(1500)
                .build();
        RestTemplate restTemplate = restTemplate(restTemplateResponseErrorHandler(), properties);
        return remoteClientOfferQueryRestTemplate(restTemplate, properties);
    }

    @Test
    public void ShouldThrowException500WhenFaultConnectionResetByPeer() {

        //given
        wireMockServer.stubFor(WireMock.get("/offers")
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.SC_OK)
                        .withHeader(CONTENT_TYPE_HEADER_KEY, APPLICATION_JSON_CONTENT_TYPE_VALUE)
                        .withFault(Fault.CONNECTION_RESET_BY_PEER)));

        //when
        Throwable throwable = catchThrowable(() -> remoteClientOfferQuery().fetchOffers());

        //then
        assertThat(throwable).isInstanceOf(ResponseStatusException.class);
        assertThat(throwable.getMessage()).isEqualTo("500 INTERNAL_SERVER_ERROR");

    }

    @Test
    public void ShouldThrowException500WhenFaultEmptyResponse() {

        //given
        wireMockServer.stubFor(WireMock.get("/offers")
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.SC_OK)
                        .withHeader(CONTENT_TYPE_HEADER_KEY, APPLICATION_JSON_CONTENT_TYPE_VALUE)
                        .withFault(Fault.EMPTY_RESPONSE)));

        //when
        Throwable throwable = catchThrowable(() -> remoteClientOfferQuery().fetchOffers());

        //then
        assertThat(throwable).isInstanceOf(ResponseStatusException.class);
        assertThat(throwable.getMessage()).isEqualTo("500 INTERNAL_SERVER_ERROR");
    }

    @Test
    public void ShouldThrowException500WhenFaultMalformedResponseChunk() {
        //given
        wireMockServer.stubFor(WireMock.get("/offers")
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.SC_OK)
                        .withHeader(CONTENT_TYPE_HEADER_KEY, APPLICATION_JSON_CONTENT_TYPE_VALUE)
                        .withFault(Fault.MALFORMED_RESPONSE_CHUNK)));
        //when
        Throwable throwable = catchThrowable(() -> remoteClientOfferQuery().fetchOffers());

        //then
        assertThat(throwable).isInstanceOf(ResponseStatusException.class);
        assertThat(throwable.getMessage()).isEqualTo("500 INTERNAL_SERVER_ERROR");
    }

    @Test
    public void ShouldThrowException500WhenFaultRandomDataThenClose() {

        //given
        wireMockServer.stubFor(WireMock.get("/offers")
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.SC_OK)
                        .withHeader(CONTENT_TYPE_HEADER_KEY, APPLICATION_JSON_CONTENT_TYPE_VALUE)
                        .withFault(Fault.RANDOM_DATA_THEN_CLOSE)));

        //when
        Throwable throwable = catchThrowable(() -> remoteClientOfferQuery().fetchOffers());

        //then
        assertThat(throwable).isInstanceOf(ResponseStatusException.class);
        assertThat(throwable.getMessage()).isEqualTo("500 INTERNAL_SERVER_ERROR");
    }

    @Test
    public void ShouldThrowException204WhenStatusIs204NoContent() {

        //given
        wireMockServer.stubFor(WireMock.get("/offers")
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.SC_NO_CONTENT)
                        .withHeader(CONTENT_TYPE_HEADER_KEY, APPLICATION_JSON_CONTENT_TYPE_VALUE)
                        .withBody("""
                                                          
                                """.trim()
                        )));

        //when
        Throwable throwable = catchThrowable(() -> remoteClientOfferQuery().fetchOffers());

        // then
        assertThat(throwable).isInstanceOf(ResponseStatusException.class);
        assertThat(throwable.getMessage()).isEqualTo("204 NO_CONTENT");
    }

    @Test
    void ShouldThrowException500WhenResponseDelayIs5000MsAndClientHas1000MsReadTimeout() {

        //given
        wireMockServer.stubFor(WireMock.get("/offers")
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.SC_OK)
                        .withHeader(CONTENT_TYPE_HEADER_KEY, APPLICATION_JSON_CONTENT_TYPE_VALUE)
                        .withBody("""
                                                             
                                """.trim()
                        )
                        .withFixedDelay(5000)));

        //when
        Throwable throwable = catchThrowable(() -> remoteClientOfferQuery().fetchOffers());

        //then
        assertThat(throwable).isInstanceOf(ResponseStatusException.class);
        assertThat(throwable.getMessage()).isEqualTo("500 INTERNAL_SERVER_ERROR");
    }

    @Test
    void ShouldThrowException404WhenHttpServiceReturningNotFoundStatus() {

        //given
        wireMockServer.stubFor(WireMock.get("/offers")
                .willReturn(WireMock.aResponse()
                        .withHeader(CONTENT_TYPE_HEADER_KEY, APPLICATION_JSON_CONTENT_TYPE_VALUE)
                        .withStatus(HttpStatus.SC_NOT_FOUND))
        );

        //when
        Throwable throwable = catchThrowable(() -> remoteClientOfferQuery().fetchOffers());

        //then
        assertThat(throwable).isInstanceOf(ResponseStatusException.class);
        assertThat(throwable.getMessage()).isEqualTo("404 NOT_FOUND");
    }

}
