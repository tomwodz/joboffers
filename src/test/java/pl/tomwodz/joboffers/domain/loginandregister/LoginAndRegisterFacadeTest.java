package pl.tomwodz.joboffers.domain.loginandregister;

import org.junit.jupiter.api.Test;
import pl.tomwodz.joboffers.domain.loginandregister.dto.RegistrationResultDto;
import pl.tomwodz.joboffers.domain.loginandregister.dto.UserDto;
import pl.tomwodz.joboffers.domain.loginandregister.dto.UserRegisterRequestDto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class LoginAndRegisterFacadeTest {

    UserRepository userRepository = new UserRepositoryTestImpl();

    LoginAndRegisterFacade loginAndRegisterFacade =new LoginAndRegisterConfiguration().loginAndRegisterFacade(userRepository);

    UserRegisterRequestDto userRegisterRequestDto = UserRegisterRequestDto
            .builder()
            .username("tomwodz")
            .password("tomwodz")
            .build();

    @Test
    void ShouldBeAbleToRegisterUser () {

        //given

        //when
        RegistrationResultDto registrationResultDto = loginAndRegisterFacade.register(userRegisterRequestDto);

        //then
        assertThat(registrationResultDto.id()).isNotNull();
        assertThat(registrationResultDto.username()).isEqualTo(userRegisterRequestDto.username());
        assertTrue(registrationResultDto.registered());

    }

    @Test
    void ShouldBeNotAbleToRegisterUserWhenLoginIsBusy () {

        //given
        UserRegisterRequestDto userRegisterWithSameUsernameRequestDto = UserRegisterRequestDto
                .builder()
                .username("tomwodz")
                .password("tomwodz")
                .build();

        //when
        loginAndRegisterFacade.register(userRegisterRequestDto);

        //then
        assertThrows(LoginAlreadyExistException.class, () -> loginAndRegisterFacade.register(userRegisterWithSameUsernameRequestDto));

    }

    @Test
    void ShouldBeAbleToFindUserByUsername() {

        //given
        loginAndRegisterFacade.register(userRegisterRequestDto);

        //when
        UserDto userDto = loginAndRegisterFacade.findByUsername(userRegisterRequestDto.username());

        //then
        assertThat(userDto).isNotNull();
        assertThat(userDto.username()).isEqualTo(userRegisterRequestDto.username());
    }

    @Test
    void ShouldThrowExceptionWhenUserNotFound(){

        //given
        //when
        //then
        assertThrows(UserNotFoundException.class, () -> loginAndRegisterFacade.findByUsername(userRegisterRequestDto.username()));

    }

}