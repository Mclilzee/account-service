package project.accountservice.user;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "user_role")
public class RoleDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Role role;

    public RoleDetails() {
    }

    public RoleDetails(Role role) {
        this.role = role;
    }

    public String getAuthority() {
        return this.role.getAuthority();
    }

    public static RoleDetails getAdministratorRole() {
        return new RoleDetails(Role.ADMINISTRATOR);
    }

    public static RoleDetails getUserRole() {
        return new RoleDetails(Role.USER);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoleDetails roleDetails1 = (RoleDetails) o;
        return role == roleDetails1.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(role);
    }

    public Role getRole() {
        return this.role;
    }

}
