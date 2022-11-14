package project.accountservice.util;

import project.accountservice.user.Role;
import project.accountservice.user.User;

import java.util.Arrays;
import java.util.List;

public class RolesUtil {

    public static boolean roleStringExist(String roleString) {
       return Arrays.stream(Role.values())
               .anyMatch(role -> role.name().equals(roleString));
    }

    public static boolean combiningBusinessRoleWithAdministratorRole(User user, Role role) {
        return containsBusinessRole(user.getRoles()) && containsAdministratorRole(List.of(role)) ||
                containsAdministratorRole(user.getRoles()) && containsBusinessRole(List.of(role));
    }

    public static boolean containsBusinessRole(List<Role> roles) {
        return roles.stream()
                .anyMatch(role -> role == Role.USER || role == Role.ACCOUNTANT || role == Role.AUDITOR);
    }

    public static boolean containsAdministratorRole(List<Role> roles) {
        return roles.stream()
                .anyMatch(role -> role == Role.ADMINISTRATOR);
    }
}
