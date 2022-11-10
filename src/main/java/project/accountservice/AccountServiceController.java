package project.accountservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class AccountServiceController {

    @PostMapping("/api/auth/signup")
    public User signUp(@Valid @RequestBody User user) {
        return user;
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
