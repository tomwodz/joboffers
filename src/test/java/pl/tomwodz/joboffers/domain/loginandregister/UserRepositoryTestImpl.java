package pl.tomwodz.joboffers.domain.loginandregister;

import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public class UserRepositoryTestImpl implements UserRepository {
    Map<Long, User> inMemoryDatabase = new ConcurrentHashMap<>();
    Random random = new Random();

    @Override
    public Optional<User> findByUsername(String username) {
        return inMemoryDatabase.values()
                .stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst();
    }

    @Override
    public User save(User user) {
        Long generateId = random.nextLong();
        this.inMemoryDatabase.put(generateId, user);
        return new User(generateId,
                inMemoryDatabase.get(generateId).getUsername(),
                inMemoryDatabase.get(generateId).getPassword());
    }

    @Override
    public boolean existsByUsername(String username) {
        return inMemoryDatabase.values()
                .stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst().isPresent();
    }
}
