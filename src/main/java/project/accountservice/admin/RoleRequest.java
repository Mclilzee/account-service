package project.accountservice.admin;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class RoleRequest {

    @NotBlank
    private String user;

    @Column(name = "role")
    @JsonProperty("role")
    private String roleString;

    @Pattern(regexp = "GRANT|REMOVE", message = "Request should be GRANT or REMOVE only")
    private String operation;

    @JsonCreator
    public RoleRequest(String user, String roleString, String operation) {
        this.roleString = roleString;
        this.user = user.toLowerCase();
        this.operation = operation;
    }

    public RoleRequest() {
    }

    public String getUser() {
        return user;
    }

    public String getRoleString() {
        return roleString;
    }

    public String getOperation() {
        return operation;
    }
}
