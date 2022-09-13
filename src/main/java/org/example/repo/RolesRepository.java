package org.example.repo;

import org.example.AppConfig;
import org.example.model.Role;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class RolesRepository {
    private static final RolesRepository INSTANCE = new RolesRepository();

    //key: role name, value: role
    private Map<String, Role> rolesMap;

    public static RolesRepository get() {
        return INSTANCE;
    }

    private RolesRepository() {
        initRoles();
    }

    private void initRoles() {
        this.rolesMap = new LinkedHashMap<>();
    }

    public Collection<Role> getRoles() {
        return rolesMap.values();
    }

    public boolean saveRole(Role role) {
        if (rolesMap.containsKey(role.getName())) {
            return false;
        }
        rolesMap.put(role.getName(), role);
        return true;
    }

    /**
     * @param roleName
     * @return true, if role repo has the role and removed. false, if repo doesn't have the role.
     */
    public boolean deleteRole(String roleName) {
        if (!rolesMap.containsKey(roleName)) {
            return false;
        }
        rolesMap.remove(roleName);
        return true;
    }

    public Role findRole(String name) {
        if (rolesMap.containsKey(name)) {
            return rolesMap.get(name);
        }
        return AppConfig.EMPTY_ROLE;
    }

    public boolean contains(String roleName) {
        return rolesMap.containsKey(roleName);
    }

    public void clearRoles() {
        initRoles();
    }
}
