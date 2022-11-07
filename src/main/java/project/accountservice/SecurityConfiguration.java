package project.accountservice;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .mvcMatchers("api/admin/**").hasRole("ADMINISTRATOR")
                .mvcMatchers("api/acct/**").hasRole("ACCOUNTANT")
                .mvcMatchers("api/empl/payment").hasAnyRole("USER, ACCOUNTANT")
                .mvcMatchers("api/auth/changepass").hasAnyRole("USER", "ACCOUNTANT", "ADMINISTRATOR")
                .mvcMatchers("api/auth/signup").permitAll();

        return http.build();
    }
}
