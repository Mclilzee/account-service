package project.accountservice.util;

import java.util.List;

public class PasswordUtil {
    private static final List<String> BREACHED_PASSWORDS = List.of("PasswordForJanuary", "PasswordForFebruary", "PasswordForMarch", "PasswordForApril",
            "PasswordForMay", "PasswordForJune", "PasswordForJuly", "PasswordForAugust",
            "PasswordForSeptember", "PasswordForOctober", "PasswordForNovember", "PasswordForDecember");

    public static boolean passwordIsBreached(String password) {
        return BREACHED_PASSWORDS.contains(password);
    }
}
