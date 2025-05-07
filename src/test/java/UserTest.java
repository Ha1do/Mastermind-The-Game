import sk.tuke.gamestudio.entity.User;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    @Test
    public void testDefaultConstructor() {
        User user = new User("", "harold@gmail.com", "123");
        assertEquals("", user.getName(), "Default name should be an empty string.");
        assertEquals(0, user.getScore(), "Default score should be 0.");
    }

    @Test
    public void testSetName() {
        User user = new User("Alice", "harold@gmail.com", "123");
        assertEquals("Alice", user.getName(), "Name should be set to 'Alice'.");
    }

    @Test
    public void testSetScore() {
        User user = new User("Harold", "harold@gmail.com", "123");
        user.setScore(100);
        assertEquals(100, user.getScore(), "Score should be set to 100.");
    }
}