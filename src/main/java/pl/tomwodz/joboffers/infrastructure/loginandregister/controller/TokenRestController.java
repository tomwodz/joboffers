package pl.tomwodz.joboffers.infrastructure.loginandregister.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.tomwodz.joboffers.infrastructure.loginandregister.controller.dto.JwtResponseDto;
import pl.tomwodz.joboffers.infrastructure.loginandregister.controller.dto.TokenRequestDto;
import pl.tomwodz.joboffers.infrastructure.security.jwt.JwtAuthenticatorFacade;

@RestController
@AllArgsConstructor
public class TokenRestController {

    private final JwtAuthenticatorFacade jwtAuthenticatorFacade;
    @PostMapping("/token")
    public ResponseEntity<JwtResponseDto> authenticateAndGenerateToken(@Valid @RequestBody TokenRequestDto tokenRequestDto) {
        JwtResponseDto jwtResponseDto = jwtAuthenticatorFacade.authenticateAndGenerateToken(tokenRequestDto);
        return ResponseEntity.ok(jwtResponseDto);
    }

}
