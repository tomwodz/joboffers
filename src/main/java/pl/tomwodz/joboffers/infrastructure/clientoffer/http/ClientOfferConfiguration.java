package pl.tomwodz.joboffers.infrastructure.clientoffer.http;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import pl.tomwodz.joboffers.domain.clientoffer.ClientOfferQuery;

import java.time.Duration;

@Configuration
public class ClientOfferConfiguration {

    @Bean
    public RestTemplateResponseErrorHandler restTemplateResponseErrorHandler() {
        return new RestTemplateResponseErrorHandler();
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateResponseErrorHandler restTemplateResponseErrorHandler, ClientOfferRestTemplateConfigurationProperties properties) {
        return new RestTemplateBuilder()
                .errorHandler(restTemplateResponseErrorHandler)
                .setConnectTimeout(Duration.ofMillis(properties.connectionTimeout()))
                .setReadTimeout(Duration.ofMillis(properties.readTimeout()))
                .build();
    }

    @Bean
    public ClientOfferQuery remoteClientOfferQueryRestTemplate(RestTemplate restTemplate, ClientOfferRestTemplateConfigurationProperties properties
    ) {
        return new ClientOfferRestTemplate(restTemplate, properties.uri(), properties.port());
    }

}
