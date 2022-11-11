package project.accountservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import project.accountservice.user.User;
import project.accountservice.user.UserRepository;
import project.accountservice.util.PasswordUtil;

import javax.validation.Valid;

@RestController
public class AccountServiceController {

    @Autowired
    BCryptPasswordEncoder encoder;

    @Autowired
    UserRepository userRepository;

    @PostMapping("/api/auth/signup")
    public User signUp(@Valid @RequestBody User user) {
        if (PasswordUtil.passwordIsBreached(user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The password is in the hacker's database!");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User exist!");
        }

        user.setPassword(encoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @GetMapping("/api/empl/payment")
    public User payment(@AuthenticationPrincipal UserDetails user) {
        return userRepository.findByEmail(user.getUsername());
    }

    @GetMapping("/public")
    public String getPublic() {
        return "this is public";
    }
}
