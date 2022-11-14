package project.accountservice.admin;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class AccessRequest {

    @NotBlank
    private final String user;

    @Pattern(regexp = "LOCK|UNLOCK")
    private final String operation;

    public AccessRequest(String user, String operation) {
        this.user = user;
        this.operation = operation;
    }

    public String getOperation() {
        return operation;
    }

    public String getUser() {
        return this.user;
    }
}
