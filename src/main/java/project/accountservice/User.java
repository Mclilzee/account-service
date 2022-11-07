package project.accountservice;

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

    public User(String name, String lastName, String email, String password, Role role) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role = role;
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

    public Role getRole() {
        return role;
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
