package pl.tomwodz.joboffers.domain.loginandregister.dto;

import lombok.Builder;

@Builder
public record UserRegisterRequestDto(
        String username,
        String password
) {
}
