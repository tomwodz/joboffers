package pl.tomwodz.joboffers.domain.loginandregister.dto;

import lombok.Builder;

@Builder
public record RegistrationResultDto (
        String id,
        String username,
        boolean registered
) {
}
