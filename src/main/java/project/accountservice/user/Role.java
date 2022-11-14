package project.accountservice.user;
public enum Role {
    ADMINISTRATOR,
    ACCOUNTANT,
    USER;

    public String getAuthority() {
        return "ROLE_" + this.name();
    }
}
