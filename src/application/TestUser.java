package application;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class TestUser {

    @Test
    public void testGetUsername() {
        User user = new User("can", "password123", false);
        String username = user.getUsername();
        Assertions.assertEquals("can", username);
    }

    @Test
    public void testSetUsername() {
        User user = new User("can", "password123", false);
        user.setUsername("tahir");
        String username = user.getUsername();
        Assertions.assertEquals("tahir", username);
    }

    @Test
    public void testGetPassword() {
        User user = new User("can", "password123", false);
        String password = user.getPassword();
        Assertions.assertEquals("password123", password);
    }

    @Test
    public void testSetPassword() {
        User user = new User("can", "password123", false);
        user.setPassword("newpassword");
        String password = user.getPassword();
        Assertions.assertEquals("newpassword", password);
    }

    @Test
    public void testIsAdmin() {
        User user = new User("can", "password123", true);
        boolean isAdmin = user.isAdmin();
        Assertions.assertTrue(isAdmin);
    }

    @Test
    public void testSetAdmin() {
        User user = new User("can", "password123", false);
        user.setAdmin(true);
        boolean isAdmin = user.isAdmin();
        Assertions.assertTrue(isAdmin);
    }

}
