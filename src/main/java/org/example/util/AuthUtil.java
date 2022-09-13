package org.example.util;

import java.util.UUID;

public class AuthUtil {
    private static final AuthUtil INSTANCE = new AuthUtil();

    /**
     * Currently use uuid as the token
     * @return token in string format
     */
    public String tokenGene() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    public static AuthUtil get() {
        return INSTANCE;
    }
}
