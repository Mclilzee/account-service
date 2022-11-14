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
        return this.role.toString();
    }

    public static Role getAdministrator() {
        return new Role(Roles.ADMINISTRATOR);
    }

    public static Role getAccountant() {
        return new Role(Roles.ACCOUNTANT);
    }

    public static Role getUser() {
        return new Role(Roles.USER);
    }

    public enum Roles {
        ADMINISTRATOR,
        ACCOUNTANT,
        USER;

        @Override
        public String toString() {
            return "ROLE_" + this.name();
        }
    }
}
