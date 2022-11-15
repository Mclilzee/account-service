package project.accountservice.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;
import project.accountservice.logger.EventLogService;

import javax.validation.Valid;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    EventLogService eventLogService;

    @PostMapping("/api/auth/signup")
    public User signUp(@Valid @RequestBody User user, ServletWebRequest request) {
        User newUser = userService.addUser(user);
        eventLogService.logCreateUserEvent(user.getEmail(), request.getRequest().getRequestURI());
        return newUser;
    }

    @PostMapping("/api/auth/changepass")
    public Map<String, String> changePassword(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody Password newPassword,
            ServletWebRequest request) {
        userService.changeUserPassword(userDetails, newPassword);
        eventLogService.logPasswordChangeEvent(userDetails.getUsername(), request.getRequest().getRequestURI());
        return getPasswordChangeSuccessResponse(userDetails);
    }

    private Map<String, String> getPasswordChangeSuccessResponse(UserDetails userDetails) {
        Map<String, String> responseBody = new LinkedHashMap<>();
        responseBody.put("email", userDetails.getUsername());
        responseBody.put("status", "The password has been updated successfully");
        return responseBody;
    }
}
