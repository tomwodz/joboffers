package pl.tomwodz.joboffers.infrastructure.offer.controller.error;

import org.springframework.http.HttpStatus;

import java.util.List;

public record ErrorPostOfferResponseDto(
        List<String> message,
        HttpStatus httpStatus) {
}
