package project.accountservice.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.WebRequest;
import project.accountservice.user.User;
import project.accountservice.user.UserRepository;

@Component
public class AuthenticationHandler {

    @Autowired
    private WebRequest request;

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
        String url = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getRequestURI();
        loginAttemptService.loginSucceeded(event.getAuthentication().getName());
    }
}
