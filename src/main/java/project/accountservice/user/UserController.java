package project.accountservice.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import project.accountservice.util.PasswordUtil;

import javax.validation.Valid;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
public class UserController {

    @Autowired
    BCryptPasswordEncoder encoder;

    @Autowired
    UserRepository userRepository;

    @PostMapping("/api/auth/signup")
    public User signUp(@Valid @RequestBody User user) {
        if (PasswordUtil.isBreached(user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The password is in the hacker's database!");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User exist!");
        }

        user.setPassword(encoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @PostMapping("/api/auth/changepass")
    public Map<String, String> changePassword(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody Password newPassword) {

        if (PasswordUtil.isBreached(newPassword.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The password is in the hacker's database!");
        }

        saveUserToDatabase(userDetails, newPassword);
        return getPasswordChangeSuccessResponse(userDetails);
    }

    private Map<String, String> getPasswordChangeSuccessResponse(UserDetails userDetails) {
        Map<String, String> responseBody = new LinkedHashMap<>();
        responseBody.put("email", userDetails.getUsername());
        responseBody.put("status", "The password has been updated successfully");
        return responseBody;
    }

    private void saveUserToDatabase(UserDetails userDetails, Password newPassword) {
        User user = userRepository.findByEmail(userDetails.getUsername());

        if (encoder.matches(newPassword.getPassword(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The passwords must be different!");
        }

        user.setPassword(encoder.encode(newPassword.getPassword()));
        userRepository.save(user);
    }
}
