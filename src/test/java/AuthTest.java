import org.example.compact.AuthCompact;
import org.example.compact.RoleCompact;
import org.example.compact.UserCompact;
import org.example.model.Role;
import org.example.model.Token;
import org.example.model.User;
import org.example.repo.AuthRepository;
import org.example.repo.RolesRepository;
import org.example.repo.UsersRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AuthTest {

    UserCompact mUserCompact;

    String username1;
    String password1;
    String username2;
    String password2;
    String username3;
    String password3;
    String role1;
    String role2;
    String role3;

    @Before
    public void initAll() {
        username1 = "tim";
        password1 = "12345678";
        username2 = "john";
        password2 = "87654Ssa32w1";
        username3 = "jason";
        password3 = "3af2sfa3";
        role1 = "engineer";
        role2 = "worker";
        role3 = "doctor";
    }

    @Test
    public void testAdd() {
        String str = "Junit is working fine";
        assertEquals("Junit is working fine", str);
    }

    @Test
    public void testCreateUser() {
        UserCompact.get().createUser(username1, password1);
        UserCompact.get().createUser(username2, password2);
        UserCompact.get().createUser(username3, password3);
        assertEquals(UsersRepository.get().getUsers().size(), 3);
    }

    @Test
    public void deleteUser() {
        UserCompact.get().deleteUser(username1);
        assertEquals(UsersRepository.get().getUsers().size(), 2);
        Assertions.assertFalse(UsersRepository.get().contains(username1));
    }

    @Test
    public void creatRole() {
        RoleCompact.get().createRole(new Role(role1));
        RoleCompact.get().createRole(new Role(role2));
        RoleCompact.get().createRole(new Role(role3));
        assertEquals(RolesRepository.get().getRoles().size(), 3);
    }

    @Test
    public void deleteRole() {
        RoleCompact.get().deleteRole(role1);
        assertEquals(RolesRepository.get().getRoles().size(), 2);
        Assertions.assertFalse(RolesRepository.get().contains(role1));
    }

    @Test
    public void addRoleToUser() {
        UserCompact.get().addRoleToUser(username1, role1);
        Assertions.assertTrue(UsersRepository.get().findUserByName(username1).getRolesNames().contains(role1));
    }

    @Test
    public void authenticate() {
        User user1 = UsersRepository.get().findUserByName(username1);
        User user2 = UsersRepository.get().findUserByName(username2);
        User user3 = UsersRepository.get().findUserByName(username3);
        assertEquals(user1.getPassword(), password1);
        assertEquals(user2.getPassword(), password2);
        assertEquals(user3.getPassword(), password3);
        AuthCompact.get().authenticate(username1);
        AuthCompact.get().authenticate(username2);
        AuthCompact.get().authenticate(username3);
        Assertions.assertEquals(AuthRepository.get().getTokens().size(), 4);
    }

    @Test
    public void invalidate() {
        Token token = AuthRepository.get().findTokenByUserName(username1);
        String tokenVal = token.getTokenValue();
        AuthCompact.get().invalidate(tokenVal);
        Assertions.assertFalse(AuthRepository.get().findTokenByValue(tokenVal).isValid());
    }

    @Test
    public void checkRole() {
        String tokenVal = AuthRepository.get().findTokenByUserName(username1).getTokenValue();
        RoleCompact.get().checkRole(username1, role1, tokenVal);
        Assertions.assertFalse(RoleCompact.get().checkRole(username1, role2, tokenVal));
    }

    @Test
    public void allRoles() {
        String tokenVal = AuthRepository.get().findTokenByUserName(username1).getTokenValue();
        RoleCompact.get().checkAllRoles(tokenVal);
        assertEquals(2, RolesRepository.get().getRoles().size());
    }

    @After
    public void tearDownAll() {

    }
}
