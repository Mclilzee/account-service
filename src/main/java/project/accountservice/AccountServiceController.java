package project.accountservice;

import org.springframework.web.bind.annotation.*;

@RestController
public class AccountServiceController {

    @PostMapping("/api/auth/signup")
    public User signUp(@RequestBody User user) {
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
