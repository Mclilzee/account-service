package project.accountservice.util;

public class StringUtil {

    public static String capitalize(String string) {
        if (string == null || string.isEmpty()) {
            return "";
        }

        return string.substring(0, 1).toUpperCase() + string.substring(1).toLowerCase();
    }
}
