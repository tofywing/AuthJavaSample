package org.example.repo;

import org.example.AppConfig;
import org.example.model.User;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class UsersRepository {
    private static final UsersRepository INSTANCE = new UsersRepository();

    //key: id, value: user
    private Map<String, User> usersMap;

    public static UsersRepository get() {
        return INSTANCE;
    }

    private UsersRepository() {
        initUsers();
    }

    private void initUsers() {
        usersMap = new LinkedHashMap<>();
    }

    public Collection<User> getUsers() {
        return usersMap.values();
    }

    public boolean saveUser(User user) {
        if (usersMap.containsKey(user.getId())) {
            return false;
        }
        usersMap.put(user.getName(), user);
        return true;
    }

    /**
     * @param userName
     * @return true, if user repo has the user and removed. false, if repo doesn't have the user.
     */
    public boolean deleteUser(String userName) {
        if (!usersMap.containsKey(userName)) {
            return false;
        }
        usersMap.remove(userName);
        return true;
    }

    public User findUserById(String id) {
        if (usersMap.containsKey(id)) {
            return usersMap.get(id);
        }
        return AppConfig.EMPTY_USER;
    }

    public User findUserByName(String name) {
        if (usersMap.containsKey(name)) {
            return usersMap.get(name);
        }
        return AppConfig.EMPTY_USER;
    }

    public boolean contains(String userName) {
        return usersMap.containsKey(userName);
    }

    public void clearUsers() {
        initUsers();
    }
}
