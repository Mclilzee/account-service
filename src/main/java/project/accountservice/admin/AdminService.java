package project.accountservice.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import project.accountservice.user.Role;
import project.accountservice.user.User;
import project.accountservice.user.UserRepository;

import java.util.List;

@Service
public class AdminService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public void deleteUser(String email) {
        User user = getUser(email);
        if (user.getRoles().contains(Role.getAdministrator().getRole())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can't remove ADMINISTRATOR role!");
        }
        userRepository.delete(user);
    }

    public User changeUserRole(RoleRequest roleRequest) {
        User user = getUser(roleRequest.getUser());
        changeUserRole(user, roleRequest);
        return userRepository.save(user);
    }

    private User getUser(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found!");
        }

        return user;
    }

    private void changeUserRole(User user, RoleRequest roleRequest) {
        Role role = new Role(Role.Roles.valueOf(roleRequest.getRole()));
        if ("GRANT".equals(roleRequest.getOperation())) {
            addRole(user, role);
        } else {
            removeRole(user, role);
        }
    }

    private void addRole(User user, Role role) {
        if (combiningBusinessRoleWithAdministratorRole(user, role)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The user cannot combine administrative and business roles!");
        }
        user.addRole(role);
    }

    private void removeRole(User user, Role role) {
        if (role.getRole().equals(Role.Roles.ADMINISTRATOR.toString())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can't remove ADMINISTRATOR role!");
        }
        if (!user.getRoles().contains(role.getRole())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The user does not have a role!");
        }

        if (user.getRoles().size() == 1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The user must have at least one role!");
        }
        user.removeRole(role);
    }

    private boolean combiningBusinessRoleWithAdministratorRole(User user, Role role) {
        return containsBusinessRole(user.getRoles()) && containsAdministratorRole(List.of(role.getRole())) ||
                containsAdministratorRole(user.getRoles()) && containsBusinessRole(List.of(role.getRole()));
    }

    private boolean containsBusinessRole(List<String> roles) {
        return roles.stream()
                .anyMatch(role -> role.equals(Role.Roles.USER.toString()) || role.equals(Role.Roles.ACCOUNTANT.toString()));
    }

    private boolean containsAdministratorRole(List<String> roles) {
        return roles.stream()
                .anyMatch(role -> role.equals(Role.Roles.ADMINISTRATOR.toString()));
    }
}
