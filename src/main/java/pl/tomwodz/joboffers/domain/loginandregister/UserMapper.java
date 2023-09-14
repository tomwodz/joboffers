package pl.tomwodz.joboffers.domain.loginandregister;

import pl.tomwodz.joboffers.domain.loginandregister.dto.UserDto;

public class UserMapper {

    public static UserDto mapFromUserToUserDto(User user){
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .build();
    }
}
