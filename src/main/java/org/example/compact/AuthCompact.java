package org.example.compact;

import com.sun.javafx.binding.StringFormatter;

import org.example.AppConfig;
import org.example.callback.InputCallback;
import org.example.model.Token;
import org.example.model.User;
import org.example.repo.AuthRepository;
import org.example.repo.UsersRepository;
import org.example.util.AuthUtil;
import org.example.InputService;

import java.time.LocalDateTime;

import static org.example.AppConfig.COMM_ALL_TOKEN;
import static org.example.AppConfig.COMM_USERS_ALL;
import static org.example.AppConfig.TOKEN_LIFE;

public class AuthCompact implements InputCallback {
    private static final AuthCompact INSTANCE = new AuthCompact();

    public static AuthCompact get() {
        return INSTANCE;
    }

    private final String className;

    {
        className = this.getClass().getSimpleName();
    }


    private AuthCompact() {
        InputService.get().bind(className, this);
    }

    /**
     * Authenticate related
     */
    public void authenticate() {
        printEnterUserNameAuthMessage();
        String userName = InputService.get().awaitInput(className);
        if (userName.equals(AppConfig.COMM_EXIT)) {
            printAuthenticateExitMessage();
            return;
        }
        if (!UsersRepository.get().contains(userName)) {
            while (UsersRepository.get().findUserByName(userName) != AppConfig.EMPTY_USER) {
                printReEnterUserNameAuthMessage();
                userName = InputService.get().awaitInput(className);
                if (userName.equals(AppConfig.COMM_EXIT)) {
                    printAuthenticateExitMessage();
                    return;
                }
            }
        }
        printEnterPasswordAuthMessage();
        String password = InputService.get().awaitInput(className);
        if (password.equals(AppConfig.COMM_EXIT)) {
            printAuthenticateExitMessage();
            return;
        }
        User user = UsersRepository.get().findUserByName(userName);
        while (!user.validation(password)) {
            printReEnterPasswordAuthMessage();
            password = InputService.get().awaitInput(className);
            if (password.equals(AppConfig.COMM_EXIT)) {
                printAuthenticateExitMessage();
                return;
            }
        }
        authenticate(userName);
    }

    public void authenticate(String userName) {
        String tokenValue = AuthUtil.get().tokenGene();
        long generateTime = System.currentTimeMillis();
        Token token = new Token(tokenValue, TOKEN_LIFE, generateTime, userName);
        AuthRepository.get().saveToken(userName, token);
        printAuthFinishedMessage(userName, token.getTokenValue());
    }

    private void printAuthFinishedMessage(String user, String token) {
        System.out.print(StringFormatter.format("User %s authenticate is finished.\ntoken: %s is " +
                        "generated at %s\n",
                user, token, LocalDateTime.now().toString()).getValueSafe());
    }

    private void printEnterUserNameAuthMessage() {
        System.out.print("Authenticate, please enter user name.\n");
    }

    public void printEnterPasswordAuthMessage() {
        System.out.print("Authenticate, please enter password.\n");
    }

    private void printReEnterUserNameAuthMessage() {
        System.out.print("Authenticate, please re-enter a user name. Enter 'user -a' to show all " +
                "users.\n");
    }

    private void printReEnterPasswordAuthMessage() {
        System.out.print("Authenticate, please re-enter a password. Enter 'exit' to exit.\n");
    }

    private void printAuthenticateExitMessage() {
        System.out.print("Authentication section is exited. Enter 'help' for more commands.\n");
    }

    public void invalidate() {
        printEnterTokenInvalidMessage();
        String tokenVal = InputService.get().awaitInput(className);
        if (tokenVal.equals(AppConfig.COMM_EXIT)) {
            printTokenInvalidExitMessage();
            return;
        }
        while (AuthRepository.get().findTokenByValue(tokenVal) == AppConfig.EMPTY_TOKEN) {
            printReEnterTokenInvalidMessage();
            tokenVal = InputService.get().awaitInput(className);
            if (tokenVal.equals(AppConfig.COMM_EXIT)) {
                printTokenInvalidExitMessage();
                return;
            }
        }
        invalidate(tokenVal);
    }

    public void invalidate(String tokenVal) {
        Token token = AuthRepository.get().findTokenByValue(tokenVal);
        if (token.isNotExpired()) {
            token.invalidate();
            printTokenInvalidSuccessMessage(token.getUserName(), token.getTokenValue());
        } else {
            printTokenExpiredMessage(token.getUserName(), token.getTokenValue());
        }
        AuthRepository.get().saveToken(token.getUserName(), token);
    }

    private void printTokenExpiredMessage(String userName, String tokenValue) {
        System.out.print(StringFormatter.format
                ("Token %s of user %s can not be invalidated due it is already expired. Enter " +
                                "'help' for more commands.\n",
                        tokenValue, userName, LocalDateTime.now().toString()).getValueSafe());
    }

    private void printTokenInvalidSuccessMessage(String userName, String tokenValue) {
        System.out.print(StringFormatter.format
                ("Token %s of user %s is invalidated. Enter 'help' for more commands.\n",
                        tokenValue, userName, LocalDateTime.now().toString()).getValueSafe());
    }

    private void printEnterTokenInvalidMessage() {
        System.out.print("Please enter a valid token to invalidate.\n'");
    }

    private void printReEnterTokenInvalidMessage() {
        System.out.print("Please re-enter a valid token to invalidate. Enter 'token -a' to " +
                "check all valid tokens\n");
    }


    private void printTokenInvalidExitMessage() {
        System.out.print("Token invalidation section is exited. Enter 'help' for more commands.\n");
    }

    @Override
    public void input(String input) {
        //to handle some operations in current section
        if (input.equals(COMM_ALL_TOKEN)) {
            printTokensReport();
        } else if (input.equals(COMM_USERS_ALL)) {
            UserCompact.get().showUsers();
        }
    }

    public void printTokensReport() {
        System.out.print(AuthRepository.get().tokenReport() + "\n");
    }
}
