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
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found!");
        } else if (user.getRoles().contains(Role.getAdministrator().getRole())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can't remove ADMINISTRATOR role!");
        }
        userRepository.delete(user);
    }

    public User changeUserRole(RoleRequest roleRequest) {
        User user = userRepository.findByEmail(roleRequest.getUser());
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User doesn't exist");
        }

        changeUserRole(user, roleRequest);
        return userRepository.save(user);
    }

    private void changeUserRole(User user, RoleRequest roleRequest) {
        Role role = new Role(Role.Roles.valueOf(roleRequest.getRole()));
        if ("GRANT".equals(roleRequest.getOperation())) {
            user.addRole(role);
        } else {
            removeRole(user, role);
        }
    }

    private void removeRole(User user, Role role) {
        if (user.getRoles().size() == 1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The user must have at least one role!");
        } else if (!user.getRoles().contains(role.getRole())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The user does not have a role!");
        }
        user.removeRole(role);
    }
}
