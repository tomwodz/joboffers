package pl.tomwodz.joboffers.infrastructure.loginandregister.controller.error;

import org.springframework.http.HttpStatus;

public record ErrorRegisterResponseDto(
        String message,
        HttpStatus httpStatus) {
}
