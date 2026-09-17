import za.ac.iie.prog5121_poe.Login;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LoginTest {

    Login login = new Login();

    @Test
    public void testUsernameCorrectlyFormatted() {
        assertTrue(login.checkUserName("kyl_1"));
    }

    @Test
    public void testUsernameIncorrectlyFormatted() {
        assertFalse(login.checkUserName("kyle!!!!!!!"));
    }

    @Test
    public void testPasswordComplexityTrue() {
        assertTrue(login.checkPasswordComplexity("Ch@sec@ke99!"));
    }

    @Test
    public void testPasswordComplexityFalse() {
        assertFalse(login.checkPasswordComplexity("password"));
    }

    @Test
    public void testCellCorrectlyFormatted() {
        assertTrue(login.checkCellPhoneNumber("+27838968976"));
    }

    @Test
    public void testCellIncorrectlyFormatted() {
        assertFalse(login.checkCellPhoneNumber("08966553"));
    }

    @Test
    public void testRegisterUserSuccess() {
        String result = login.registerUser("kyl_1", "Ch@sec@ke99!", "+27838968976");
        assertEquals("User successfully registered.", result);
    }

    @Test
    public void testLoginSuccess() {
        boolean result = login.loginUser("kyl_1", "Ch@sec@ke99!");
        assertTrue(result);
    }
}