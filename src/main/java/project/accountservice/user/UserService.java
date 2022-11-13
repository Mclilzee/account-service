package project.accountservice.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import project.accountservice.util.PasswordUtil;

import java.util.List;

@Service
public class UserService {

    @Autowired
    BCryptPasswordEncoder encoder;

    @Autowired
    UserRepository userRepository;

    public User addUser(User user) {
        validateUser(user);
        setRole(user);
        user.setPassword(encoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    private void setRole(User user) {
        if (userRepository.count() == 0){
            user.setRoles(List.of(Roles.ROLE_ADMINISTRATOR));
        } else {
            user.setRoles(List.of(Roles.ROLE_USER));
        }
    }

    private void validateUser(User user) {
        if (PasswordUtil.isBreached(user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The password is in the hacker's database!");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User exist!");
        }
    }
}
