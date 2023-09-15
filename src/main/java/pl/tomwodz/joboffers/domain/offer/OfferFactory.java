package pl.tomwodz.joboffers.domain.offer;

import pl.tomwodz.joboffers.domain.clientoffer.dto.JobOfferResponse;
import pl.tomwodz.joboffers.domain.offer.dto.OfferRequestDto;

import java.util.List;

class OfferFactory {

    Offer mapFromOfferRequestDtoToOffer(OfferRequestDto offerRequestDto){
        return new Offer(
                offerRequestDto.companyName(),
                offerRequestDto.position(),
                offerRequestDto.salary(),
                offerRequestDto.offerUrl()
                );
    }

    List<Offer> mapFromJobOfferResponseToOffers(List<JobOfferResponse> jobOffersResponse) {
        return jobOffersResponse.stream()
                .map(jobOfferResponse -> mapFromJobOfferResponseToOffer(jobOfferResponse))
                .toList();
    }

    Offer mapFromJobOfferResponseToOffer(JobOfferResponse jobOfferResponse){
        return new Offer(
                jobOfferResponse.company(),
                jobOfferResponse.title(),
                jobOfferResponse.salary(),
                jobOfferResponse.offerUrl());
    }
}

