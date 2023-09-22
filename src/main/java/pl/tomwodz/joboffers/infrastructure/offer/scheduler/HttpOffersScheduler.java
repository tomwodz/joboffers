package pl.tomwodz.joboffers.infrastructure.offer.scheduler;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.tomwodz.joboffers.domain.offer.OfferFacade;
import pl.tomwodz.joboffers.domain.offer.dto.OfferResponseDto;

import java.util.List;

@Component
@AllArgsConstructor
@Log4j2
public class HttpOffersScheduler {

    private final OfferFacade offerFacade;

    @Scheduled(fixedDelayString = "${job-offers.offer.scheduler.request.delay}")
    public List<OfferResponseDto> fetchAllOffersAndSaveAllIfNotExists(){
        log.info("Client offer scheduler started.");
        List<OfferResponseDto> addedOffers = offerFacade.fetchAllOffersAndSaveAllIfNotExists();
        log.info("Added offers: " + addedOffers.size());
        return addedOffers;
    }
}
