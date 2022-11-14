package project.accountservice.admin;

import com.fasterxml.jackson.annotation.JsonCreator;
import project.accountservice.user.Role;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class RoleRequest {

    @NotBlank
    private String user;

    private Role.Roles role;

    @Pattern(regexp = "GRANT|REMOVE")
    private String operation;

    @JsonCreator
    public RoleRequest(String user, String role, String operation) {
        this.user = user;
        this.role = Role.Roles.valueOf(role);
        this.operation = operation;
    }

    public RoleRequest() {
    }
}
