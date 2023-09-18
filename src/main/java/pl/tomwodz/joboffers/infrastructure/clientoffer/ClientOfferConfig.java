package pl.tomwodz.joboffers.infrastructure.clientoffer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import pl.tomwodz.joboffers.domain.clientoffer.ClientOfferQuery;

import java.time.Duration;

@Configuration
public class ClientOfferConfig {

    @Bean
    public RestTemplateResponseErrorHandler restTemplateResponseErrorHandler() {
        return new RestTemplateResponseErrorHandler();
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateResponseErrorHandler restTemplateResponseErrorHandler) {
        return new RestTemplateBuilder()
                .errorHandler(restTemplateResponseErrorHandler)
                .setConnectTimeout(Duration.ofMillis(1000))
                .setReadTimeout(Duration.ofMillis(1000))
                .build();
    }

    @Bean
    public ClientOfferQuery remoteClientOfferQueryRestTemplate(RestTemplate restTemplate,
                                                               @Value("${job-offers.client-offer.http.client.config.uri}") String uri,
                                                               @Value("${job-offers.client-offer.http.client.config.port}") int port
    ) {
        return new ClientOfferRestTemplate(restTemplate, uri, port);
    }

}
