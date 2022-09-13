package org.example;

import com.sun.javafx.binding.StringFormatter;

import org.example.model.Role;
import org.example.model.Token;
import org.example.model.User;

import java.util.UUID;

public class AppConfig {
    public static final UUID uid;

    static {
        uid = new UUID(15, 10);
    }

    public static final User EMPTY_USER;

    static {
        EMPTY_USER = new User("invalid" + uid, "invalid" + uid);
    }

    public static final Role EMPTY_ROLE;

    static {
        EMPTY_ROLE = new Role("emptyRole " + uid);
    }

    public static final Token EMPTY_TOKEN;

    static {
        EMPTY_TOKEN = new Token("emptyToken" + uid, 0, 0, EMPTY_USER.getName());
    }

    //Commands
    public static final String COMM_HELP = "help";
    public static final String COMM_EXIT = "exit";
    public static final String COMM_CREATE_USER = "user -c";
    public static final String COMM_DELETE_USER = "user -d";
    public static final String COMM_USERS_ALL = "user -a";
    public static final String COMM_CREATE_ROLE = "role -c";
    public static final String COMM_DELETE_ROLE = "role -d";
    public static final String COMM_ROLES_ALL = "role -a";
    public static final String COMM_ADD_ROLE_TO_USER = "user -add";
    public static final String COMM_AUTHENTICATE = "auth";
    public static final String COMM_INVALIDATE = "auth -i";
    public static final String COMM_CHECK_ROLE = "role -check";
    public static final String COMM_USER_ALL_ROLES = "user -a -r";

    public static final String COMM_ALL_TOKEN = "token -a";

    public static final String SHOW_COMM_LIST = StringFormatter.format(
            "Command list is shown below:\n" +
                    "%s \nshow all the command\n" +
                    "%s \ncreate user by user name and password\n" +
                    "%s \ndelete user by user name\n" +
                    "%s \nshow all saved user\n" +
                    "%s \ncreate role by role name\n" +
                    "%s \ndelete role by role name\n" +
                    "%s \nshow all saved roles\n" +
                    "%s \nadd role to user by user name and role name\n" +
                    "%s \nauthenticate by user name and password  and return a token\n" +
                    "%s \ninvalidate the auth token\n" +
                    "%s \ncheck roles by auth token and role name\n" +
                    "%s \nget all roles for the user thru the auth token\n" +
                    "%s \nexit the program\n",
            COMM_HELP,
            COMM_CREATE_USER,
            COMM_DELETE_USER,
            COMM_USERS_ALL,
            COMM_CREATE_ROLE,
            COMM_DELETE_ROLE,
            COMM_ROLES_ALL,
            COMM_ADD_ROLE_TO_USER,
            COMM_AUTHENTICATE,
            COMM_INVALIDATE,
            COMM_CHECK_ROLE,
            COMM_USER_ALL_ROLES,
            COMM_EXIT).getValueSafe();

    //currently token life is 2 hours.
    public static final long TOKEN_LIFE = 2 * 60 * 60 * 1000;
}
