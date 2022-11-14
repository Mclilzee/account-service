package project.accountservice.user;

import javax.persistence.*;

@Entity
@Table(name = "user_role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Roles role;

    public Role() {
    }

    public Role(Roles role) {
        this.role = role;
    }

    public String getRole() {
        return this.role.name();
    }

    public static Role getAdministrator() {
        return new Role(Roles.ROLE_ADMINISTRATOR);
    }

    public static Role getAccountant() {
        return new Role(Roles.ROLE_ACCOUNTANT);
    }

    public static Role getUser() {
        return new Role(Roles.ROLE_USER);
    }

    enum Roles {
        ROLE_ADMINISTRATOR,
        ROLE_ACCOUNTANT,
        ROLE_USER
    }
}
