package pl.tomwodz.joboffers.infrastructure.clientoffer.http;

import lombok.Builder;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "job-offers.client-offer.http.client.config")
@Builder
public record ClientOfferRestTemplateConfigurationProperties(
        String uri,
        int port,
        int connectionTimeout,
        int readTimeout) {
}
