package pl.tomwodz.joboffers.domain.loginandregister;

import java.util.Optional;

interface UserRepository {

    Optional<User> findByUsername(String username);

    User save(User user);

    boolean existsByUsername(String username);


}
