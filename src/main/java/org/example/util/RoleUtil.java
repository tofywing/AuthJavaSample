package org.example.util;

import java.util.Locale;
import java.util.regex.Pattern;

public class RoleUtil {
    private static final RoleUtil INSTANCE = new RoleUtil();

    public static RoleUtil get() {
        return INSTANCE;
    }

    /**
     * @param roleName input
     * @return if match the rule of the input. Current input rule includes the length of input (3
     * to 30) and in word characters (a to z, 0 to 9) and non-case-sensitive
     */
    public boolean roleNameChecker(String roleName) {
        if (roleName == null || roleName.length() == 0) {
            return false;
        }
        roleName = roleName.toLowerCase(Locale.ROOT);
        return Pattern.matches("\\w{3,30}", roleName);
    }

}
