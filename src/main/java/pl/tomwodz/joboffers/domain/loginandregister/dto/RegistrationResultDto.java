package pl.tomwodz.joboffers.domain.loginandregister.dto;

import lombok.Builder;

@Builder
public record RegistrationResultDto (
        Long id,
        String username,
        boolean registered
) {
}
