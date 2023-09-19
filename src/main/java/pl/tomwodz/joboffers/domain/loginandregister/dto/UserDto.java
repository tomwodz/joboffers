package pl.tomwodz.joboffers.domain.loginandregister.dto;

import lombok.Builder;

@Builder
public record UserDto(
        String id,
        String password,

        String username

) {
}
