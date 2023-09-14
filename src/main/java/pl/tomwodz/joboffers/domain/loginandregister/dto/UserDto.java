package pl.tomwodz.joboffers.domain.loginandregister.dto;

import lombok.Builder;

@Builder
public record UserDto(
        Long id,
        String password,

        String username

) {
}
