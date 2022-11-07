package project.accountservice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class AccountServiceController {

    @PostMapping("/api/auth/signup")
    public User signUp(@RequestBody User user) {
        if (!isValid(user)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return user;
    }

    private boolean isValid(User user) {
        return false;
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
