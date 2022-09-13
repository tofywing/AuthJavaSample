package org.example.model;

import org.example.AppConfig;
import org.example.util.UserUtil;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class User {
    private String password;
    private String id;
    private String name;

    //key: role name, value: role
    private Map<String, Role> rolesMap;

    private User() {
    }

    public User(String name, String password) {
        initRoles();
        this.password = password;
        this.name = name;
        this.id = UserUtil.get().userIdGene(name, password);
    }

    public boolean validation(String password) {
        return password.equals(this.password);
    }

    public String getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean addRole(String roleName) {
        if (rolesMap.containsKey(roleName)) {
            return false;
        }
        Role role = new Role(roleName);
        rolesMap.put(role.getName(), role);
        return true;
    }

    public void initRoles() {
        this.rolesMap = new LinkedHashMap<>();
    }

    public Collection<Role> getRolesMap() {
        return rolesMap.values();
    }

    public Set<String> getRolesNames() {
        return rolesMap.keySet();
    }

    public void clearRoles() {
        initRoles();
    }

    public Role findRoleByName(String name) {
        if (rolesMap.containsKey(name)) {
            return rolesMap.get(name);
        }
        return AppConfig.EMPTY_ROLE;
    }

    public String rolesReport() {
        StringBuilder report = new StringBuilder("Roles under " + name + " are:\n");
        for (String roleName : rolesMap.keySet()) {
           report.append(roleName).append("\n");
        }
        return report.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof User) {
            return ((User) obj).getName().equals(name);
        }
        return super.equals(obj);
    }
}