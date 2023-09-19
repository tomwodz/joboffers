package pl.tomwodz.joboffers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import pl.tomwodz.joboffers.infrastructure.clientoffer.ClientOfferRestTemplateConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({ClientOfferRestTemplateConfigurationProperties.class})
@EnableMongoRepositories
public class JobOffersApplication {
    public static void main(String[] args) {
        SpringApplication.run(JobOffersApplication.class);
    }
}
