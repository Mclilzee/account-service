package project.accountservice.user;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Size;

public class Password {

    @Size(min = 12, message = "The password length must be at least 12 chars!")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String new_password;

    public Password(String new_password) {
        this.new_password = new_password;
    }

    public Password() {}

    public String getPassword() {
        return new_password;
    }
}
