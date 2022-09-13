package org.example.model;

public class Token {
    private final String tokenValue;

    private final long generateTime;

    private final long life;

    private boolean isValid;

    //related info
    private final String userName;

    public String getUserName() {
        return userName;
    }

    /**
     * The token validation is based on its expiration. Only case if it is not expired we
     * still can consider the token as invalid.
     *
     * @return token is valid or not
     */
    public boolean isValid() {
        if (isValid) {
            isValid = isNotExpired();
        }
        return isValid;
    }

    public void setValidation(boolean isValid) {
        this.isValid = isValid;
    }

    public boolean isNotExpired() {
        long duration = System.currentTimeMillis() - generateTime;
        return isValid = duration <= life;
    }

    public void invalidate() {
        this.isValid = false;
    }

    public String getTokenValue() {
        return tokenValue;
    }

    public long getGenerateTime() {
        return generateTime;
    }

    public Token(String token, long life, long generateTime, String userName) {
        this.tokenValue = token;
        this.life = life;
        this.generateTime = generateTime;
        this.userName = userName;
        this.isValid = true;
        System.currentTimeMillis();
    }
}
