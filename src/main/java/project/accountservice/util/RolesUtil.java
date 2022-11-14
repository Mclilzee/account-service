package project.accountservice.util;

import project.accountservice.user.Role;

import java.util.Arrays;

public class RolesUtil {

   public static boolean roleExists(Role role) {
      return Arrays.stream(Role.values())
              .anyMatch(enumRole -> role == enumRole);
   }

    public static boolean authorityExist(String authority) {
       return Arrays.stream(Role.values())
               .anyMatch(role -> role.getAuthority().equals(authority));
    }
}
