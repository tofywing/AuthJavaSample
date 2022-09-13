package org.example.repo;

import org.example.AppConfig;
import org.example.model.Token;
import org.example.model.User;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class AuthRepository {
    private static final AuthRepository INSTANCE = new AuthRepository();

    //key: user name value: token
    private final Map<String, Token> tokenMapByName;

    //key: token value value: token
    private final Map<String, Token> tokenMapByValue;

    public void saveToken(String userName, Token token) {
        tokenMapByName.put(userName, token);
        tokenMapByValue.put(token.getTokenValue(), token);
    }

    public Token findTokenByUserName(String userName) {
        if (tokenMapByName.containsKey(userName)) {
            return tokenMapByName.get(userName);
        }
        return AppConfig.EMPTY_TOKEN;
    }

    public Token findTokenByValue(String tokenVal) {
        if (tokenMapByValue.containsKey(tokenVal)) {
            return tokenMapByValue.get(tokenVal);
        }
        return AppConfig.EMPTY_TOKEN;
    }

    public User findUserByToken(String tokenVal) {
        Token token = findTokenByValue(tokenVal);
        if (!token.equals(AppConfig.EMPTY_TOKEN)) {
            String userName = token.getUserName();
            return UsersRepository.get().findUserByName(userName);
        } else {
            return AppConfig.EMPTY_USER;
        }
    }

    public static AuthRepository get() {
        return INSTANCE;
    }

    public Collection<Token> getTokens(){
        return tokenMapByValue.values();
    }
    private AuthRepository() {
        tokenMapByName = new LinkedHashMap<>();
        tokenMapByValue = new LinkedHashMap<>();
    }

    public boolean contains(String tokenVal) {
        return tokenMapByValue.containsKey(tokenVal);
    }

    public String tokenReport() {
        StringBuilder report = new StringBuilder();
        for (String userName : tokenMapByName.keySet()) {
            report.append(userName).append(" : ").append(tokenMapByName.get(userName).getTokenValue()).append("\n");
        }
        return report.toString();
    }

}
