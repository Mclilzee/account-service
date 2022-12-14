package project.accountservice.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import project.accountservice.user.Role;
import project.accountservice.user.RoleDetails;
import project.accountservice.user.User;
import project.accountservice.user.UserRepository;
import project.accountservice.util.RolesUtil;

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
        if (user.getAuthorities().contains(RoleDetails.getAdministratorRole().getAuthority())) {
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
        RoleDetails roleDetails = new RoleDetails(Role.valueOf(roleRequest.getRoleString()));
        if ("GRANT".equals(roleRequest.getOperation())) {
            addRole(user, roleDetails);
        } else {
            removeRole(user, roleDetails);
        }
    }

    private void addRole(User user, RoleDetails roleDetails) {
        if (RolesUtil.combiningBusinessRoleWithAdministratorRole(user, roleDetails.getRole())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The user cannot combine administrative and business roles!");
        }
        user.addRole(roleDetails);
    }

    private void removeRole(User user, RoleDetails roleDetails) {
        if (roleDetails.getRole() == Role.ADMINISTRATOR) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can't remove ADMINISTRATOR role!");
        }
        if (!user.getRoles().contains(roleDetails.getRole())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The user does not have a role!");
        }

        if (user.getAuthorities().size() == 1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The user must have at least one role!");
        }
        user.removeRole(roleDetails);
    }

    public void changeUserAccess(AccessRequest accessRequest) {
        User user = getUser(accessRequest.getUser());
        if ("LOCK".equals(accessRequest.getOperation())) {
            lockUser(user);
        } else {
            user.setLocked(false);
        }

        userRepository.save(user);
    }

    private void lockUser(User user) {
        if (RolesUtil.containsAdministratorRole(user.getRoles())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can't lock the ADMINISTRATOR!");
        }

        user.setLocked(true);
    }
}
