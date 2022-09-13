package org.example.util;

import java.util.Locale;
import java.util.regex.Pattern;

public class UserUtil {
    private static final UserUtil INSTANCE = new UserUtil();

    public static UserUtil get() {
        return INSTANCE;
    }

    private UserUtil() {
    }

    /**
     * @param username input
     * @return if match the rule of the input. Current input rule includes the length of input (3
     * to 30) and in word characters (a to z, 0 to 9) and non-case-sensitive
     */
    public boolean usernameChecker(String username) {
        if (username == null || username.length() == 0) {
            return false;
        }
        username = username.toLowerCase(Locale.ROOT);
        return Pattern.matches("\\w{3,30}", username);
    }

    /**
     * @param password input
     * @return if match the rule of the input. Current input rule includes the length of input (8
     * to 30) and in word characters (a to z, 0 to 9) and non-case-sensitive
     */
    public boolean passwordChecker(String password) {
        if (password == null || password.length() == 0) {
            return false;
        }
        password = password.toLowerCase(Locale.ROOT);
        return Pattern.matches("\\w{8,30}", password);
    }

    public String userIdGene(String name, String password) {
        //return StringFormatter.format("%s,%s", name, password).getValueSafe();
        //the id is as same as the name, since no 2 names co-exist in current situation
        return name;
    }
}
