package pl.tomwodz.joboffers.domain.offer.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record OfferRequestDto(

        @NotNull(message = "{offer-request.company-name.not.null}")
        @NotEmpty(message = "{offer-request.company-name.not.empty}")
        String companyName,
        @NotNull(message = "{offer-request.position.not.null}")
        @NotEmpty(message = "{offer-request.position.not.empty}")
        String position,
        @NotNull(message = "{offer-request.salary.not.null}")
        @NotEmpty(message = "{offer-request.salary.not.empty}")
        String salary,
        @NotNull(message = "{offer-request.url.not.null}")
        @NotEmpty(message = "{offer-request.url.not.empty}")
        String offerUrl) {
}
