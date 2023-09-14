package pl.tomwodz.joboffers.domain.loginandregister;

public class LoginAndRegisterConfiguration {

    LoginAndRegisterFacade loginAndRegisterFacade (UserRepository userRepository){
        UserFactory userFactory = new UserFactory();
        return new LoginAndRegisterFacade(userRepository, userFactory);
    }
}
