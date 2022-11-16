package project.accountservice.user;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity(name = "users")
@JsonPropertyOrder({"id", "name", "lastname", "email"})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String lastname;

    @Pattern(regexp = ".*@acme.com")
    @Column(unique = true)
    private String email;

    @NotBlank
    @Size(min = 12, message = "The password length must be at least 12 chars!")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "user_id", nullable = false)
    private Set<RoleDetails> roleDetails;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private boolean locked;

    public User() {
    }

    @JsonCreator
    public User(String name, String lastname, String email, String password) {
        this.name = name;
        this.lastname = lastname;
        this.email = email.toLowerCase();
        this.password = password;
        this.locked = false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Role> getRoles() {
        return this.roleDetails.stream()
                .map(RoleDetails::getRole)
                .collect(Collectors.toList());
    }

    @JsonProperty("roles")
    public List<String> getAuthorities() {
        return roleDetails.stream()
                .map(RoleDetails::getAuthority)
                .sorted()
                .collect(Collectors.toList());
    }

    public void addRole(RoleDetails roleDetails) {
        this.roleDetails.add(roleDetails);
    }

    public void setRoles(Set<RoleDetails> roleDetails) {
        this.roleDetails = roleDetails;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email.toLowerCase();
    }

    public void removeRole(RoleDetails roleDetails) {
        this.roleDetails.remove(roleDetails);
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public boolean isLocked() {
        return this.locked;
    }
}
