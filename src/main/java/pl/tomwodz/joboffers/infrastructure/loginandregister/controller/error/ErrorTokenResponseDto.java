package pl.tomwodz.joboffers.infrastructure.loginandregister.controller.error;

import org.springframework.http.HttpStatus;

public record ErrorTokenResponseDto(
        String message,
        HttpStatus httpStatus) {
}
