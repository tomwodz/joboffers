package pl.tomwodz.joboffers.domain.loginandregister;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;

@Configuration
public class LoginAndRegisterConfiguration {

    @Bean
    UserRepository userRepository(){
        return new UserRepository() {
            @Override
            public Optional<User> findByUsername(String username) {
                return Optional.empty();
            }

            @Override
            public User save(User user) {
                return null;
            }

            @Override
            public boolean existsByUsername(String username) {
                return false;
            }
        };
    }

    @Bean
    LoginAndRegisterFacade loginAndRegisterFacade (UserRepository userRepository){
        UserFactory userFactory = new UserFactory();
        return new LoginAndRegisterFacade(userRepository, userFactory);
    }
}
