package tests.testDatabase;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import model.database.UserDao;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class UserDaoTest {
    private UserDao userDao;

    @Before
    public void setUp() {
        userDao = new UserDao();
    }

    @After
    public void tearDown() {
        userDao = null;
    }

    @Test
    public void testAuthenticateUserSuccess() {
        assertTrue(userDao.authorizeUser(1, "123"));
    }

    @Test
    public void testAuthenticateUserFailure() {
        assertFalse(userDao.authorizeUser(5, "5"));
    }

    @Test
    public void testSQLInjection() {
        int MALICIOUS_INPUT = 1;
        String SQL_QUERY = "SELECT * FROM users WHERE username = 1 AND password = 123";
        assertFalse(userDao.authorizeUser(MALICIOUS_INPUT, SQL_QUERY));
    }
}



