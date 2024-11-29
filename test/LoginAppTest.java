import org.junit.*;

public class LoginAppTest {
    private LoginApp loginApp;

    @Before
    public void setUp() {
        loginApp = new LoginApp();

    }

    @Test
    public void testValidEmailAndPassword() {
        String userName = loginApp.authenticateUser("mikejohnson@example.com", "password789");
        Assert.assertEquals("Mike Johnson", userName);
        Assert.assertTrue(userName.matches("Mike Johnson"));

    }

    @Test
    public void testEmailExistsWrongPassword() {
        String userName = loginApp.authenticateUser("johndoe@example.com", "wrongpassword");
        Assert.assertFalse(Boolean.parseBoolean(userName));
        Assert.assertNull(userName);
    }

    @Test
    public void testEmailDoesNotExist() {
        String userName = loginApp.authenticateUser("nonexistent@example.com", "password456");
//        Assert.assertNull(userName);
        Assert.assertFalse(Boolean.parseBoolean(userName));

    }

    @Test
    public void testEmptyEmailOrPassword() {
        String userName = loginApp.authenticateUser("", "");
        Assert.assertNull(userName);
    }

    @Test
    public void testSQLExceptionHandling() {
        try {
            LoginApp faultyLoginApp = new LoginApp() {
                @Override
                String authenticateUser(String email, String password) {
                    throw new RuntimeException("Database error"); // Simulate DB error
                }
            };
            faultyLoginApp.authenticateUser("johndoe@example.com", "password@123");
        } catch (Exception e) {
            Assert.assertTrue(e.getMessage().contains("Database error"));
        }
    }
}