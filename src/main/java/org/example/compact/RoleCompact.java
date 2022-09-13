package org.example.compact;

import com.sun.javafx.binding.StringFormatter;

import org.example.AppConfig;
import org.example.callback.InputCallback;
import org.example.model.Role;
import org.example.model.Token;
import org.example.model.User;
import org.example.repo.AuthRepository;
import org.example.repo.RolesRepository;
import org.example.InputService;
import org.example.repo.UsersRepository;
import org.example.util.RoleUtil;

import java.util.Collection;

import static org.example.AppConfig.COMM_ALL_TOKEN;
import static org.example.AppConfig.COMM_ROLES_ALL;
import static org.example.AppConfig.COMM_USERS_ALL;
import static org.example.AppConfig.uid;

public class RoleCompact implements InputCallback {
    private static final RoleCompact INSTANCE = new RoleCompact();

    public static RoleCompact get() {
        return INSTANCE;
    }

    private final String className;

    {
        className = this.getClass().getSimpleName();
    }

    private RoleCompact() {
        InputService.get().bind(className, this);
    }

    /**
     * Create role related
     */
    public void createRole() {
        printRoleNameCreateEnterMessage();
        String roleName = InputService.get().awaitInput(className);
        if (roleName.equals(AppConfig.COMM_EXIT)) {
            printCreateRoleExitMessage();
            return;
        }
        while (!RoleUtil.get().roleNameChecker(roleName)) {
            printRoleNameCreateReEnterMessage();
            roleName = InputService.get().awaitInput(className);
            if (roleName.equals(AppConfig.COMM_EXIT)) {
                printCreateRoleExitMessage();
                return;
            }
        }
        Role role = RolesRepository.get().findRole(roleName);
        if (role == AppConfig.EMPTY_ROLE) {
            role = new Role(roleName);
        } else {
            printRoleAlreadyExist(roleName);
            createRole();
        }
        createRole(role);
    }

    public void createRole(Role role) {
        RolesRepository.get().saveRole(role);
        printCreateRoleSuccessfulMessage(role.getName());
    }

    private void printRoleNameCreateEnterMessage() {
        System.out.print("Please enter a role name to add. in length of 3-30 word characters a to" +
                " z, 0 to 9 and non-case-sensitive.\n");
    }

    private void printRoleNameCreateReEnterMessage() {
        System.out.print("Please re-enter a role name to add. in length of 3-30 word characters a" +
                " to z, 0 to 9 and non-case-sensitive. Enter 'exit' to exit.\n");
    }

    private void printRoleAlreadyExist(String roleName) {
        System.out.print(StringFormatter.format("The role %s is already exist, you can delete or " +
                "re-enter another role name to create.\n", roleName).getValueSafe());
    }

    private void printCreateRoleSuccessfulMessage(String role) {
        System.out.print(StringFormatter
                .format("Role %s is added. Enter 'help' for more commands.\n", role).getValueSafe());
    }

    private void printCreateRoleExitMessage() {
        System.out.print("Exit add role section.  Enter 'help' for more commands.\n");
    }

    /**
     * Delete role related.
     */
    public void deleteRole() {
        printRoleNameDeleteEnterMessage();
        String roleName = InputService.get().awaitInput(className);
        if (roleName.equals(AppConfig.COMM_EXIT)) {
            printDeleteRoleExitMessage();
            return;
        }
        while (!RoleUtil.get().roleNameChecker(roleName)) {
            printRoleNameDeleteReEnterMessage();
            roleName = InputService.get().awaitInput(className);
            if (roleName.equals(AppConfig.COMM_EXIT)) {
                printDeleteRoleExitMessage();
                return;
            }
        }

        printRoleDeleteEnterMessage();
        while (!RoleUtil.get().roleNameChecker(roleName)) {
            printRoleDeleteReEnterMessage();
            roleName = InputService.get().awaitInput(className);
            if (roleName.equals(AppConfig.COMM_EXIT)) {
                printDeleteRoleExitMessage();
                return;
            }
        }
        if (!deleteRole(roleName)) {
            printRoleDeleteNotExistedMessage(roleName);
            deleteRole();
        } else {
            printRoleDeleteFinishedMessage(roleName);
        }
    }

    public boolean deleteRole(String roleName) {
        return RolesRepository.get().deleteRole(roleName);
    }

    private void printRoleNameDeleteEnterMessage() {
        System.out.print("Please enter a saved role name to delete.\n");
    }

    private void printRoleNameDeleteReEnterMessage() {
        System.out.print("Please re-enter a saved role name to delete. Enter 'role -a' to check " +
                "all valid roles.\n");
    }

    private void printRoleDeleteNotExistedMessage(String roleName) {
        System.out.print(StringFormatter
                .format("Role %s is not existed, please re-enter another valid role name to " +
                        "delete. Enter 'role -a' to check all valid roles.\n", roleName).getValueSafe());
    }

    private void printRoleDeleteEnterMessage() {
        System.out.print("Please enter a role name to delete.\n");
    }

    private void printDeleteRoleExitMessage() {
        System.out.print("Exit delete role section. Enter 'help' for more commands.\n");
    }

    /**
     * Show all roles related.
     */
    public void showRoles() {
        Collection<Role> roleList = RolesRepository.get().getRoles();
        StringBuilder listMsg = new StringBuilder();
        for (Role role : roleList) {
            listMsg.append(role.getName()).append("\n");
        }
        printAllRolesMessage(listMsg.toString());
    }

    private void printAllRolesMessage(String msg) {
        System.out.print(StringFormatter.format("All roles are shown below\n%s.\n", msg).getValueSafe());
    }

    private void printRoleDeleteReEnterMessage() {
        System.out.print("Please re-enter a valid role name to delete. Enter 'exit' to exit.\n");
    }

    private void printRoleDeleteFinishedMessage(String roleName) {
        System.out.print(StringFormatter.format("Role name %s is deleted. Enter 'help' for more " +
                "commands.\n", roleName).getValueSafe());
    }

    public void checkRole() {
        printCheckRoleEnterUserNameMessage();
        String userName = InputService.get().awaitInput(className);
        if (userName.equals(AppConfig.COMM_EXIT)) {
            printCheckRoleExitMessage();
            return;
        }
        while (!UsersRepository.get().contains(userName)) {
            printCheckRoleReEnterUserNameMessage();
            userName = InputService.get().awaitInput(className);
            if (userName.equals(AppConfig.COMM_EXIT)) {
                printCheckRoleExitMessage();
                return;
            }
            checkRole();
        }
        printCheckRoleEnterRoleMessage();
        String roleName = InputService.get().awaitInput(className);
        if (roleName.equals(AppConfig.COMM_EXIT)) {
            printCheckRoleExitMessage();
            return;
        }
        while (!RolesRepository.get().contains(roleName)) {
            printCheckRoleReEnterRoleMessage();
            roleName = InputService.get().awaitInput(className);
            if (roleName.equals(AppConfig.COMM_EXIT)) {
                printCheckRoleExitMessage();
                return;
            }
            checkRole();
        }

        printCheckRoleEnterTokenMessage();
        String tokenVal = InputService.get().awaitInput(className);
        if (tokenVal.equals(AppConfig.COMM_EXIT)) {
            printCheckRoleExitMessage();
            return;
        }

        while (AuthRepository.get().findTokenByValue(tokenVal) == AppConfig.EMPTY_TOKEN) {
            printCheckRoleReEnterTokenMessage();
            tokenVal = InputService.get().awaitInput(className);
            if (tokenVal.equals(AppConfig.COMM_EXIT)) {
                printCheckRoleExitMessage();
                return;
            }
        }
        checkRole(userName, roleName, tokenVal);
    }

    public boolean checkRole(String userName, String roleName, String tokenVal) {
        Token token = AuthRepository.get().findTokenByValue(tokenVal);
        User user = UsersRepository.get().findUserByName(userName);
        boolean userRoleChecker = user.getRolesNames().contains(roleName);
        boolean tokenChecker = token.isValid();
        boolean checker = userRoleChecker && tokenChecker;
        if (checker) {
            printCheckRoleSuccessfulMessage(userName, roleName, tokenVal);
        } else {
            printCheckRoleUnSuccessfulMessage(userName, roleName);
        }
        return checker;
    }

    private void printCheckRoleEnterUserNameMessage() {
        System.out.print("Please renter a user name to check.\n");
    }

    private void printCheckRoleReEnterUserNameMessage() {
        System.out.print
                ("Please re-enter a user name to check. Enter 'user -a' to show all users.\n");
    }

    private void printCheckRoleEnterRoleMessage() {
        System.out.print("Please enter a saved role name to check.\n");
    }

    private void printCheckRoleReEnterRoleMessage() {
        System.out.print("Please re-enter a saved role name to delete. Enter 'role -a' to check " +
                "all valid roles.\n");
    }

    private void printCheckRoleEnterTokenMessage() {
        System.out.print("Please enter a valid token to check.\n");
    }

    private void printCheckRoleReEnterTokenMessage() {
        System.out.print("Please re-enter a valid token to check. Enter 'token -a' to " +
                "check all valid tokens.\n");
    }


    private void printCheckRoleExitMessage() {
        System.out.print("Exit check role section.  Enter 'help' for more commands.\n");
    }

    private void printCheckRoleSuccessfulMessage(String username, String roleName,
                                                 String tokenVal) {
        System.out.print(StringFormatter.format("User %s is identified by the token %s and belong" +
                " the role %s. Enter 'help' for more commands.\n", username, tokenVal, roleName).getValueSafe());
    }


    private void printCheckRoleUnSuccessfulMessage(String username, String roleName) {
        System.out.print(StringFormatter
                .format("Checking user %s and the role %s is unsuccessful.\n",
                        username, roleName).getValueSafe());
    }

    public void checkAllRoles() {
        printCheckAllRolesEnterTokenMessage();
        String tokenVal = InputService.get().awaitInput(className);
        if (tokenVal.equals(AppConfig.COMM_EXIT)) {
            printCheckRoleExitMessage();
            return;
        }
        Token token;
        while ((token = AuthRepository.get().findTokenByValue(tokenVal)) == AppConfig.EMPTY_TOKEN
                && token.isValid()) {
            printCheckAllRolesReEnterTokenMessage();
            tokenVal = InputService.get().awaitInput(className);
            if (tokenVal.equals(AppConfig.COMM_EXIT)) {
                printCheckAllRolesExitMessage();
                return;
            }
        }
        checkAllRoles(tokenVal);
    }

    public void checkAllRoles(String tokenVal) {
        User user = AuthRepository.get().findUserByToken(tokenVal);
        if (user != AppConfig.EMPTY_USER) {
            String report = user.rolesReport();
            printCheckAllRolesSuccessfulMessage(tokenVal, user.getName(), report);
        } else {
            printCheckAllRolesUnSuccessfulMessage(tokenVal, user.getName());
        }
    }

    private void printCheckAllRolesEnterTokenMessage() {
        System.out.print("Please enter a valid token to check all roles.\n");
    }

    private void printCheckAllRolesReEnterTokenMessage() {
        System.out.print("Please re-enter a valid token to check all roles. Enter 'token -a' to " +
                "check all valid tokens.\n");
    }


    private void printCheckAllRolesExitMessage() {
        System.out.print("Exit check all roles section. Enter 'help' for more commands.\n");
    }

    private void printCheckAllRolesSuccessfulMessage(String tokenVal, String username,
                                                     String report) {
        System.out.print(StringFormatter.format("User %s with the token %s has the roles " +
                        "below\n%s\nEnter 'help' for more commands.\n",
                username, tokenVal, report).getValueSafe());
    }


    private void printCheckAllRolesUnSuccessfulMessage(String tokenVal, String username) {
        System.out.print(StringFormatter.format("User %s with the token %s is not valid." +
                "\nEnter 'help' for more commands.\n", username, tokenVal).getValueSafe());
    }

    @Override
    public void input(String input) {
        //to handle some operations in current section
        switch (input) {
            case COMM_ROLES_ALL:
                RoleCompact.get().showRoles();
                break;
            case COMM_USERS_ALL:
                UserCompact.get().showUsers();
                break;
            case COMM_ALL_TOKEN:
                AuthCompact.get().printTokensReport();
                break;
        }
    }
}

