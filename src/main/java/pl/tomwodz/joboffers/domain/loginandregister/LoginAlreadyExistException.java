package pl.tomwodz.joboffers.domain.loginandregister;

public class LoginAlreadyExistException extends RuntimeException {
    public LoginAlreadyExistException(String message) {
        super(message);
    }
}
