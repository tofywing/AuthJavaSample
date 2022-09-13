package org.example.compact;

import com.sun.javafx.binding.StringFormatter;

import org.example.AppConfig;
import org.example.callback.InputCallback;
import org.example.model.Role;
import org.example.model.User;
import org.example.repo.RolesRepository;
import org.example.repo.UsersRepository;
import org.example.InputService;
import org.example.util.UserUtil;

import java.util.Collection;

import static org.example.AppConfig.COMM_USERS_ALL;

public class UserCompact implements InputCallback {
    private static final UserCompact INSTANCE = new UserCompact();

    public static UserCompact get() {
        return INSTANCE;
    }

    private final String className;

    {
        className = this.getClass().getSimpleName();
    }

    private UserCompact() {
        InputService.get().bind(className, this);
    }

    /**
     * Create user related
     */
    public void createUser() {
        printEnterUserNameMessage();
        String userName = InputService.get().awaitInput(className);
        if (userName.equals(AppConfig.COMM_EXIT)) {
            printUserCreateExitMessage();
            return;
        }
        while (!UserUtil.get().usernameChecker(userName)) {
            printReEnterUserNameMessage();
            userName = InputService.get().awaitInput(className);
            if (userName.equals(AppConfig.COMM_EXIT)) {
                printUserCreateExitMessage();
                return;
            }
        }
        printEnterPasswordMessage();
        String password = InputService.get().awaitInput(className);
        if (password.equals(AppConfig.COMM_EXIT)) {
            printUserCreateExitMessage();
            return;
        }
        while (!UserUtil.get().passwordChecker(password)) {
            printReEnterPasswordMessage();
            password = InputService.get().awaitInput(className);
            if (password.equals(AppConfig.COMM_EXIT)) {
                printUserCreateExitMessage();
                return;
            }
        }
        createUser(userName, password);
    }

    public void createUser(String userName, String password){
        User user;
        String userId = UserUtil.get().userIdGene(userName, password);
        user = UsersRepository.get().findUserById(userId);
        if (user == AppConfig.EMPTY_USER) {
            user = new User(userName, password);
        } else {
            printUserAlreadyExist(userName);
            createUser();
        }
        UsersRepository.get().saveUser(user);
        printUserNameCreateSuccessfulMessage();
    }

    public void printUserNameCreateSuccessfulMessage() {
        System.out.print("User name is created. Enter 'help' for more commands.\n");
    }

    private void printEnterUserNameMessage() {
        System.out.print("Please enter a user name in length of 3-30 word characters " +
                " a to z, 0 to 9 and non-case-sensitive.\n");
    }

    private void printReEnterUserNameMessage() {
        System.out.print("Please re-enter a valid user name in length of 3-30 word characters" +
                " a to z, 0 to 9 and non-case-sensitive. Enter 'exit' to exit.\n");
    }

    private void printEnterPasswordMessage() {
        System.out.print("Please enter a password in length of 8-30 word characters" +
                " a to z, 0 to 9 and non-case-sensitive.\n");
    }

    private void printReEnterPasswordMessage() {
        System.out.print("Please re-enter a valid password in length of 8-30 word characters" +
                " a to z, 0 to 9 and non-case-sensitive. Enter 'exit' to exit.\n");
    }

    private void printUserAlreadyExist(String user) {
        System.out.print(StringFormatter.format("The user %s is already exist, you can delete or " +
                "re-enter another user name to create. Enter 'exit' to exit.\n", user).getValueSafe());
    }

    private void printUserCreateExitMessage() {
        System.out.print("Exit user create section.  Enter 'help' for more commands.\n");
    }

    /**
     * Delete user related.
     */
    public void deleteUser() {
        printUserDeleteEnterMessage();
        String userName = InputService.get().awaitInput(className);
        if (userName.equals(AppConfig.COMM_EXIT)) {
            printUserDeleteExitMessage();
            return;
        }
        while (!UsersRepository.get().contains(userName)) {
            printUserDeleteNotExistedMessage(userName);
            userName = InputService.get().awaitInput(className);
            if (userName.equals(AppConfig.COMM_EXIT)) {
                printUserDeleteExitMessage();
                return;
            }
            deleteUser();
        }
        deleteUser(userName);
    }

    public void deleteUser(String userName) {
        UsersRepository.get().deleteUser(userName);
        printUserDeleteSuccessfulMessage(userName);
    }

    private void printUserDeleteNotExistedMessage(String userName) {
        System.out.print(StringFormatter
                .format("User %s is not existed, please re-enter another valid user name to " +
                        "delete. Enter 'user -a' to show all users.\n", userName).getValueSafe());
    }

    private void printUserDeleteEnterMessage() {
        System.out.print("Please enter a saved user name to delete.\n");
    }

    private void printUserDeleteReEnterMessage() {
        System.out.print("Please re-enter a saved user name to delete. Enter 'user -a' to check " +
                "all users.\n");
    }

    private void printUserDeleteSuccessfulMessage(String username) {
        System.out.print(StringFormatter.format("User name %s is deleted. Enter 'help' for more " +
                "commands.\n", username).getValueSafe());
    }

    private void printUserDeleteExitMessage() {
        System.out.print("Exit user delete section.  Enter 'help' for more commands.\n");
    }

    /**
     * Show all users related.
     */
    public void showUsers() {
        Collection<User> userList = UsersRepository.get().getUsers();
        StringBuilder listMsg = new StringBuilder();
        for (User user : userList) {
            listMsg.append(user.getName()).append("\n");
        }
        printAllUsersMessage(listMsg.toString());
    }

    private void printAllUsersMessage(String msg) {
        System.out.print(StringFormatter.format("All users are shown below\n%s.\n", msg).getValueSafe());
    }

    /**
     * Delete add role to user related.
     */
    public void addRoleToUser() {
        printEnterUserNameToAddMessage();
        String userName = InputService.get().awaitInput(className);
        if (userName.equals(AppConfig.COMM_EXIT)) {
            printAddRoleToUserExitMessage();
            return;
        }
        while (UsersRepository.get().findUserById(userName) == AppConfig.EMPTY_USER) {
            printReEnterUserNameToAddMessage();
            userName = InputService.get().awaitInput(className);
            if (userName.equals(AppConfig.COMM_EXIT)) {
                printAddRoleToUserExitMessage();
                return;
            }
        }
        printEnterRoleNameToAddMessage();
        String roleName = InputService.get().awaitInput(className);
        if (roleName.equals(AppConfig.COMM_EXIT)) {
            printAddRoleToUserExitMessage();
            return;
        }
        addRoleToUser(userName, roleName);
    }

    public void addRoleToUser(String userName, String roleName){
        User user = UsersRepository.get().findUserById(userName);
        if (user.addRole(roleName)) {
            printAddRoleToUserSuccessfulMessage(userName, roleName);
        } else {
            printAddRoleToUserFailedMessage(userName, roleName);
        }
        Role role = user.findRoleByName(roleName);
        if (role != AppConfig.EMPTY_ROLE) {
            RolesRepository.get().saveRole(role);
        }
    }

    private void printEnterUserNameToAddMessage() {
        System.out.print("Please enter a user name to add.\n");
    }

    private void printReEnterUserNameToAddMessage() {
        System.out.print("Please re-enter a saved user name to add. Enter 'exit' to exit.\n");
    }

    private void printEnterRoleNameToAddMessage() {
        System.out.print("Please enter a role name to add.\n");
    }

    private void printAddRoleToUserSuccessfulMessage(String user, String role) {
        System.out.print(StringFormatter
                .format("Add role %s to user %s action is finished. " +
                        "Enter 'help' for more commands.\n", role, user).getValueSafe());
    }

    private void printAddRoleToUserFailedMessage(String user, String role) {
        System.out.print(StringFormatter
                .format("Role %s can not add to user %s. " +
                        "Enter 'help' for more commands.\n", role, user).getValueSafe());
    }

    private void printAddRoleToUserExitMessage() {
        System.out.print("Exit add role to user section.  Enter 'help' for more commands.\n");
    }

    @Override
    public void input(String input) {
        //to handle some operations in current section
        if (input.equals(COMM_USERS_ALL)) {
            UserCompact.get().showUsers();
        }
    }
}
