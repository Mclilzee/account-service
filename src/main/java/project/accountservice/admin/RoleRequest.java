package project.accountservice.admin;

import com.fasterxml.jackson.annotation.JsonCreator;
import project.accountservice.user.Role.Roles;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class RoleRequest {

    @NotBlank
    private String user;

    private String role;

    @Pattern(regexp = "GRANT|REMOVE", message = "Request should be GRANT or REMOVE only")
    private String operation;

    @JsonCreator
    public RoleRequest(String user, String role, String operation) {
        this.role = role;
        this.user = user.toLowerCase();
        this.operation = operation;
    }

    public RoleRequest() {
    }

    public String getUser() {
        return user;
    }

    public String getRole() {
        return role;
    }

    public String getOperation() {
        return operation;
    }


}
