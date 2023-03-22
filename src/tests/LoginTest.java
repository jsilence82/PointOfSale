package tests;

import model.database.UserDao;
import org.junit.jupiter.api.Test;
import view.Login;
import view.SelectScreen;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class LoginTest {

    @Test
    void testSQLInjection() {
        Login login = new Login(new SelectScreen());

        try {
            Field userDaoField = Login.class.getDeclaredField("passwordCheck");
            userDaoField.setAccessible(true);
            UserDao userDao = (UserDao) userDaoField.get(login);

            String maliciousUsername = "1'; DROP TABLE users; --";
            String password = "password";
            userDao.authorizeUser(Integer.parseInt(maliciousUsername), password);

            assertFalse(userDao.authorizeUser(Integer.parseInt(maliciousUsername), password));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            fail("Exception thrown during test: " + e.getMessage());
        } catch (ClassCastException af){
            System.out.println("Exception thrown during test: " + af.getMessage());
        }
    }
}
