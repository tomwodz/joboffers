package pl.tomwodz.joboffers.infrastructure.loginandregister.controller.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import pl.tomwodz.joboffers.domain.loginandregister.LoginAlreadyExistException;
import pl.tomwodz.joboffers.infrastructure.loginandregister.controller.RegisterRestController;

@ControllerAdvice(assignableTypes = RegisterRestController.class)
public class RegisterRestControllerErrorHandler {

    @ExceptionHandler(LoginAlreadyExistException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorRegisterResponseDto handleBadLogin(LoginAlreadyExistException exception){
        return new ErrorRegisterResponseDto(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
