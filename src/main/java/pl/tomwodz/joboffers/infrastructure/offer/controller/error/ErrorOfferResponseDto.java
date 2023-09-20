package pl.tomwodz.joboffers.infrastructure.offer.controller.error;

import org.springframework.http.HttpStatus;

public record ErrorOfferResponseDto(
        String message,
        HttpStatus httpStatus) {
}
