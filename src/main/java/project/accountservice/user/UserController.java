package project.accountservice.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/api/auth/signup")
    public User signUp(@Valid @RequestBody User user) {
        return userService.addUser(user);
    }

    @PostMapping("/api/auth/changepass")
    public Map<String, String> changePassword(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody Password newPassword) {
        userService.changeUserPassword(userDetails, newPassword);
        return getPasswordChangeSuccessResponse(userDetails);
    }

    private Map<String, String> getPasswordChangeSuccessResponse(UserDetails userDetails) {
        Map<String, String> responseBody = new LinkedHashMap<>();
        responseBody.put("email", userDetails.getUsername());
        responseBody.put("status", "The password has been updated successfully");
        return responseBody;
    }
}
