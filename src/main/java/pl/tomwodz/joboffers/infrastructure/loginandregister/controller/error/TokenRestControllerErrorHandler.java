package pl.tomwodz.joboffers.infrastructure.loginandregister.controller.error;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import pl.tomwodz.joboffers.domain.loginandregister.LoginAlreadyExistException;

@ControllerAdvice()
public class TokenRestControllerErrorHandler {

    private static final String BAD_CREDENTIALS = "Bad Credentials";

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorTokenResponseDto handleBadCredentials(){
        return new  ErrorTokenResponseDto(BAD_CREDENTIALS, HttpStatus.UNAUTHORIZED);
    }

}
