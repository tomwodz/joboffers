package pl.tomwodz.joboffers.domain.offer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.tomwodz.joboffers.domain.clientoffer.ClientOfferQuery;

@Configuration
class OfferConfiguration {

    @Bean
    public OfferFacade offerFacade (OfferRepository offerRepository, ClientOfferQuery clientOfferQuery){
        OfferFactory offerFactory = new OfferFactory();
        return new OfferFacade(offerRepository, offerFactory, clientOfferQuery);
    }
}
