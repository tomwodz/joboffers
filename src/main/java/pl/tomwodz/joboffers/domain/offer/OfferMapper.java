package pl.tomwodz.joboffers.domain.offer;

import pl.tomwodz.joboffers.domain.offer.dto.OfferResponseDto;

class OfferMapper {

    public static OfferResponseDto mapFromOfferToOfferResponseDto(Offer offer){
        return OfferResponseDto.builder()
                .id(offer.getId())
                .companyName(offer.getCompanyName())
                .position(offer.getPosition())
                .salary(offer.getSalary())
                .offerUrl(offer.getOfferUrl())
                .build();
    }


}
