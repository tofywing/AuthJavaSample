package org.example;

import org.example.compact.AuthCompact;
import org.example.compact.RoleCompact;
import org.example.compact.UserCompact;
import org.example.repo.AuthRepository;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.example.AppConfig.COMM_ADD_ROLE_TO_USER;
import static org.example.AppConfig.COMM_ALL_TOKEN;
import static org.example.AppConfig.COMM_AUTHENTICATE;
import static org.example.AppConfig.COMM_CHECK_ROLE;
import static org.example.AppConfig.COMM_CREATE_ROLE;
import static org.example.AppConfig.COMM_CREATE_USER;
import static org.example.AppConfig.COMM_DELETE_ROLE;
import static org.example.AppConfig.COMM_DELETE_USER;
import static org.example.AppConfig.COMM_EXIT;
import static org.example.AppConfig.COMM_HELP;
import static org.example.AppConfig.COMM_INVALIDATE;
import static org.example.AppConfig.COMM_ROLES_ALL;
import static org.example.AppConfig.COMM_USERS_ALL;
import static org.example.AppConfig.COMM_USER_ALL_ROLES;
import static org.example.AppConfig.SHOW_COMM_LIST;

public class Main {
    private static String className;

    {
        className = this.getClass().getSimpleName();
    }

    private static ExecutorService executor;
    private static Runnable task;

    public static void main(String[] args) {
        bindService();
        initExecutor();
        initTask();
        executor.execute(task);
    }

    private static void bindService() {
        InputService.get().bind(className, input -> {
            switch (input) {
                case COMM_HELP:
                    printCommandList();
                    break;
                case COMM_CREATE_USER:
                    UserCompact.get().createUser();
                    break;
                case COMM_DELETE_USER:
                    UserCompact.get().deleteUser();
                    break;
                case COMM_USERS_ALL:
                    UserCompact.get().showUsers();
                    break;
                case COMM_CREATE_ROLE:
                    RoleCompact.get().createRole();
                    break;
                case COMM_DELETE_ROLE:
                    RoleCompact.get().deleteRole();
                    break;
                case COMM_ROLES_ALL:
                    RoleCompact.get().showRoles();
                    break;
                case COMM_ADD_ROLE_TO_USER:
                    UserCompact.get().addRoleToUser();
                    break;
                case COMM_AUTHENTICATE:
                    AuthCompact.get().authenticate();
                    break;
                case COMM_INVALIDATE:
                    AuthCompact.get().invalidate();
                    break;
                case COMM_CHECK_ROLE:
                    RoleCompact.get().checkRole();
                    break;
                case COMM_USER_ALL_ROLES:
                    RoleCompact.get().checkAllRoles();
                    break;
                case COMM_EXIT:
                    break;
                case COMM_ALL_TOKEN:
                    System.out.println(AuthRepository.get().tokenReport());
                    break;
                default:
                    System.out.print("Please re-enter a valid command. Enter " +
                            "'help' for helps.\n");
                    break;
            }
        });
    }

    private static void initExecutor() {
        executor = Executors.newFixedThreadPool(10);
    }

    private static void initTask() {
        task = () -> {
            //System.in is a standard input stream
            System.out.print("Enter a command. Enter 'help' for helps.\n");
            String inputString;
            while (true) {
                if(InputService.get().awaitInput(className).equals(COMM_EXIT)){
                    System.out.print("Program is exited.\n");
                    executor.shutdown();
                    return;
                }
            }
        };
    }

    private static void printCommandList() {
        System.out.print(SHOW_COMM_LIST);
    }
}