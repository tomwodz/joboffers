package pl.tomwodz.joboffers.domain.offer.dto;

import lombok.Builder;

@Builder
public record OfferResponseDto(
        Long id,
        String companyName,
        String position,
        String salary,
        String offerUrl
) {
}
