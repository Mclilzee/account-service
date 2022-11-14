package project.accountservice.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import project.accountservice.user.UserDetailsServiceImp;

@Configuration
public class SecurityConfiguration {

    @Autowired
    AccessDeniedCustomHandler accessDeniedCustomHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .mvcMatchers("/api/admin/**").hasRole("ADMINISTRATOR")
                .mvcMatchers("/api/acct/**").hasRole("ACCOUNTANT")
                .mvcMatchers("/api/empl/payment").hasAnyRole("USER", "ACCOUNTANT")
                .mvcMatchers("/api/auth/changepass").hasAnyRole("USER", "ACCOUNTANT", "ADMINISTRATOR")
                .mvcMatchers("/api/auth/signup", "/actuator/shutdown").permitAll()
                .and().httpBasic()
                .and().formLogin()
                .and().csrf().disable().headers().frameOptions().disable()
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().exceptionHandling().accessDeniedHandler(accessDeniedCustomHandler);

        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider authProvider(@Autowired BCryptPasswordEncoder encoder, @Autowired UserDetailsServiceImp userDetailsService) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(encoder);
        authenticationProvider.setUserDetailsService(userDetailsService);
        return authenticationProvider;
    }
}
