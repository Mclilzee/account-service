package project.accountservice.util;

import project.accountservice.user.Role;
import project.accountservice.user.Role.Roles;

import java.util.Arrays;

public class RolesUtil {

   public static boolean roleExists(Role role) {
      return Arrays.stream(Roles.values())
              .anyMatch(enumRole -> role.getRole().equals(enumRole.toString()));
   }

    public static boolean roleExists(String role) {
        return Arrays.stream(Roles.values())
                .anyMatch(enumRole -> role.equals(enumRole.toString()));
    }
}
