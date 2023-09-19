package pl.tomwodz.joboffers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import pl.tomwodz.joboffers.infrastructure.clientoffer.ClientOfferRestTemplateConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({ClientOfferRestTemplateConfigurationProperties.class})
public class JobOffersApplication {
    public static void main(String[] args) {
        SpringApplication.run(JobOffersApplication.class);
    }
}
