package org.example.model;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class Role {
    private final String name;

    //key: user name, value: user
    private Map<String, User> usersMap;

    public Role(String name) {
        initUsers();
        this.name = name;
    }

    private void initUsers() {
        usersMap = new LinkedHashMap<>();
    }

    public String getName() {
        return this.name;
    }

    public Collection<User> getRelatedUsers() {
        return usersMap.values();
    }

    public boolean addUser(User user) {
        if (usersMap.containsKey(user.getName())) {
            return false;
        }
        usersMap.put(user.getName(), user);
        return true;
    }

    public void clearUsers() {
        initUsers();
    }
}
