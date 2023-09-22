package pl.tomwodz.joboffers.infrastructure.loginandregister.controller;


import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.tomwodz.joboffers.domain.loginandregister.LoginAndRegisterFacade;
import pl.tomwodz.joboffers.domain.loginandregister.dto.RegistrationResultDto;
import pl.tomwodz.joboffers.domain.loginandregister.dto.UserRegisterRequestDto;

@RestController
@AllArgsConstructor
public class RegisterRestController {

    private final LoginAndRegisterFacade loginAndRegisterFacade;
    private final PasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/register")
    public ResponseEntity<RegistrationResultDto> register(@RequestBody UserRegisterRequestDto userRegisterRequestDto) {
        String encodedPassword = bCryptPasswordEncoder.encode(userRegisterRequestDto.password());
        RegistrationResultDto registerResult = loginAndRegisterFacade.register(
                new UserRegisterRequestDto(userRegisterRequestDto.username(), encodedPassword));
        return ResponseEntity.status(HttpStatus.CREATED).body(registerResult);
    }

}
