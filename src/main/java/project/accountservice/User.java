package project.accountservice;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class User {

    enum Role {
        ADMINISTRATOR,
        ACCOUNTANT,
        USER
    }

    private String name;
    private String lastName;
    private String email;
    private String password;
    private Role role;

    public User(String name, String lastName, String email, String password) {
        if (name.isBlank() || lastName.isBlank() || email.isBlank() || password.isBlank() || !isValidEmail(email)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role = Role.USER;
    }

    private boolean isValidEmail(String email) {
        return email.endsWith("acme.com");
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
