package project.accountservice.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import project.accountservice.util.PasswordUtil;

import java.util.Set;

@Service
public class UserService {

    @Autowired
    BCryptPasswordEncoder encoder;

    @Autowired
    UserRepository userRepository;

    public User addUser(User user) {
        checkIfPasswordBreached(user.getPassword());
        checkIfUserAlreadyExist(user);
        setRole(user);
        user.setPassword(encoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public void changeUserPassword(UserDetails userDetails, Password newPassword) {
        checkIfPasswordBreached(newPassword.getPassword());

        User user = userRepository.findByEmail(userDetails.getUsername());
        checkIfSamePassword(newPassword, user);

        user.setPassword(encoder.encode(newPassword.getPassword()));
        userRepository.save(user);
    }

    private void setRole(User user) {
        if (userRepository.count() == 0){
            user.setRoles(Set.of(Role.getAdministrator()));
        } else {
            user.setRoles(Set.of(Role.getUser()));
        }
    }

    private void checkIfUserAlreadyExist(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User exist!");
        }
    }

    private void checkIfSamePassword(Password newPassword, User user) {
        if (encoder.matches(newPassword.getPassword(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The passwords must be different!");
        }
    }

    private void checkIfPasswordBreached(String password) {
        if (PasswordUtil.isBreached(password)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The password is in the hacker's database!");
        }
    }
}
