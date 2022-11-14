package project.accountservice.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;
import project.accountservice.user.User;
import project.accountservice.user.UserRepository;
import project.accountservice.user.UserService;

import javax.servlet.http.HttpServletRequest;

@Component
public class AuthenticationFailureListener {

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private LoginAttemptService loginAttemptService;

    @Autowired
    UserRepository userRepository;

    @EventListener
    public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent event) {
        String userName = event.getAuthentication().getName();
        if (!userRepository.existsByEmail(userName)) {
            return;
        }

        loginAttemptService.loginFailed(userName);
        if (loginAttemptService.isBlocked(userName)) {
            User user = userRepository.findByEmail(userName);
            user.setLocked(true);
            userRepository.save(user);
        }
    }

    @EventListener
    public void onApplicationEvent(AuthenticationSuccessEvent event) {
        loginAttemptService.loginSucceeded(event.getAuthentication().getName());
    }
}

