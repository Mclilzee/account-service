package project.accountservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountServiceController {

    @PostMapping("api/auth/signup")
    public void signUp(@RequestParam User user) {
        System.out.println(user);
    }
}
