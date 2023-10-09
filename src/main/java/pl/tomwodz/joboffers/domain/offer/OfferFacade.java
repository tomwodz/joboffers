package pl.tomwodz.joboffers.domain.offer;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.Cacheable;
import pl.tomwodz.joboffers.domain.clientoffer.ClientOfferQuery;
import pl.tomwodz.joboffers.domain.clientoffer.dto.JobOfferResponse;
import pl.tomwodz.joboffers.domain.offer.dto.OfferRequestDto;
import pl.tomwodz.joboffers.domain.offer.dto.OfferResponseDto;

import java.util.List;

import static pl.tomwodz.joboffers.domain.offer.MessageResponse.OFFER_NOT_FOUND;

@AllArgsConstructor
@Log4j2
public class OfferFacade {

    private final OfferRepository offerRepository;
    private final OfferFactory offerFactory;
    private final ClientOfferQuery clientOfferQuery;
    public OfferResponseDto findOfferById(String id) {
        return this.offerRepository.findById(id)
                .map(OfferMapper::mapFromOfferToOfferResponseDto)
                .orElseThrow(() -> new OfferNotFoundException(OFFER_NOT_FOUND.info + id));
    }

    @Cacheable("jobOffers")
    public List<OfferResponseDto> findAllOffers() {
        return this.offerRepository.findAll()
                .stream()
                .map(OfferMapper::mapFromOfferToOfferResponseDto)
                .toList();
    }

    public OfferResponseDto saveOffer(OfferRequestDto offerRequestDto) {
        Offer offerToSave = offerFactory.mapFromOfferRequestDtoToOffer(offerRequestDto);
        Offer offerSaved = offerRepository.save(offerToSave);
        log.info("added offer" + offerSaved.getOfferUrl());
        return OfferMapper.mapFromOfferToOfferResponseDto(offerSaved);
    }

    public List<OfferResponseDto> fetchAllOffersAndSaveAllIfNotExists() {
        List<JobOfferResponse> jobOffersResponse = this.clientOfferQuery.fetchOffers();
        List<Offer> offers = this.offerFactory.mapFromJobOfferResponseToOffers(jobOffersResponse);
        List<Offer> offersToSave = filterNotExistsByOfferUrl(offers);
        this.offerRepository.saveAll(offersToSave);
        return offersToSave.stream()
                .map(offerSaved -> OfferMapper.mapFromOfferToOfferResponseDto(offerSaved))
                .toList();
    }

    private List<Offer> filterNotExistsByOfferUrl(List<Offer> offers) {
        return offers.stream()
                .filter(offer -> !offer.getOfferUrl().isEmpty())
                .filter(offer -> !this.offerRepository.existsByOfferUrl(offer.getOfferUrl()))
                .toList();
    }

}
