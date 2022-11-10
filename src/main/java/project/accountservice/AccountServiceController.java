package project.accountservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.Optional;

@RestController
public class AccountServiceController {

    @Autowired
    UserRepositoryService userRepositoryService;

    @Autowired
    BCryptPasswordEncoder encoder;

    @PostMapping("/api/auth/signup")
    public User signUp(@Valid @RequestBody User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        Optional<User> newUser = userRepositoryService.getUserByEmail(user.getEmail());
        if (newUser.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User exist!");
        }

        userRepositoryService.addUser(user);
        return userRepositoryService.getUserByEmail(user.getEmail()).get();
    }

    @GetMapping("/api/empl/payment")
    public String payment() {
        return "hello payment!";
    }

    @GetMapping("/public")
    public String getPublic() {
        return "this is public";
    }
}
