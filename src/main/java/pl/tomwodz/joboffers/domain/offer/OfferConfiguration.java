package pl.tomwodz.joboffers.domain.offer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.tomwodz.joboffers.domain.clientoffer.ClientOfferQuery;

import java.util.List;
import java.util.Optional;

@Configuration
class OfferConfiguration {

    @Bean
    OfferRepository offerRepository(){
        return new OfferRepository() {
            @Override
            public Optional<Offer> findById(Long id) {
                return Optional.empty();
            }

            @Override
            public List<Offer> findAll() {
                return null;
            }

            @Override
            public Offer save(Offer offer) {
                return null;
            }

            @Override
            public boolean existsByOfferUrl(String url) {
                return false;
            }
        };
    }

    @Bean
    public OfferFacade offerFacade (OfferRepository offerRepository, ClientOfferQuery clientOfferQuery){
        OfferFactory offerFactory = new OfferFactory();
        return new OfferFacade(offerRepository, offerFactory, clientOfferQuery);
    }
}
