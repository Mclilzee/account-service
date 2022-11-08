package project.accountservice;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class User {

    @NotBlank
    private String name;

    @NotBlank
    private String lastname;

    @Pattern(regexp = ".*@acme.com")
    private String email;

    @NotBlank
    private String password;

    public User(String name, String lastname, String email, String password) {
        if (name.isBlank() || lastname.isBlank() || email.isBlank() || password.isBlank() || !isValidEmail(email)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
    }

    private boolean isValidEmail(String email) {
        return email.endsWith("acme.com");
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastName(String lastName) {
        this.lastname = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
