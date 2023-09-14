package pl.tomwodz.joboffers.domain.loginandregister;

import pl.tomwodz.joboffers.domain.loginandregister.dto.UserRegisterRequestDto;

class UserFactory {

    User mapFromUserRegisterRequestDto(UserRegisterRequestDto userRegisterRequestDto){
        return new User(
                userRegisterRequestDto.username(),
                userRegisterRequestDto.password());
    }

}
