package project.accountservice;

import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserRepository {

    public Optional<User> findUserByUsername(String username) {
        return Optional.empty();
    }

    public void save(User user) {

    }
}
