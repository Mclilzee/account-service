package project.accountservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserRepositoryService {

    @Autowired
    UserRepository repository;

    public Optional<User> getUserByEmail(String email) {
        return Optional.ofNullable(repository.findByEmail(email));
    }

    public void addUser(User user) {
        repository.save(user);
    }
}
