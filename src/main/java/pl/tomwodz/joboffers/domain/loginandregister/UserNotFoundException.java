package pl.tomwodz.joboffers.domain.loginandregister;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
