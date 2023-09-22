package pl.tomwodz.joboffers.domain.loginandregister;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import pl.tomwodz.joboffers.domain.loginandregister.dto.RegistrationResultDto;
import pl.tomwodz.joboffers.domain.loginandregister.dto.UserDto;
import pl.tomwodz.joboffers.domain.loginandregister.dto.UserRegisterRequestDto;

import static pl.tomwodz.joboffers.domain.loginandregister.MessageResponse.USERNAME_ALREADY_EXISTS;
import static pl.tomwodz.joboffers.domain.loginandregister.MessageResponse.USER_NOT_FOUND;

@AllArgsConstructor
public class LoginAndRegisterFacade {

    private final UserRepository userRepository;
    private final UserFactory userFactory;
    public UserDto findByUsername(String username){
        return this.userRepository.findByUsername(username)
                .map(UserMapper::mapFromUserToUserDto)
                .orElseThrow(() -> new BadCredentialsException(USER_NOT_FOUND.info));
    }

    public RegistrationResultDto register(UserRegisterRequestDto userRegisterRequestDto){
        if(this.userRepository.existsByUsername(userRegisterRequestDto.username())){
            throw new LoginAlreadyExistException(USERNAME_ALREADY_EXISTS.info + userRegisterRequestDto.username());
        }
        User userToSave = userFactory.mapFromUserRegisterRequestDto(userRegisterRequestDto);
        User userSaved = userRepository.save(userToSave);
        return RegistrationResultDto.builder()
                .id(userSaved.getId())
                .username(userSaved.getUsername())
                .registered(true)
                .build();
    }

}
