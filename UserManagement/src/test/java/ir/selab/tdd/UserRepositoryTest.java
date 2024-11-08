package ir.selab.tdd;

import ir.selab.tdd.domain.User;
import ir.selab.tdd.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class UserRepositoryTest {
    private UserRepository repository;

    @Before
    public void setUp() {
        List<User> userList = Arrays.asList(
                new User("admin", "1234"),
                new User("ali", "qwert"),
                new User("mohammad", "123asd"),
                new User("abcd", "123asd", ""),

                new User("amirmahdi", "kousheshi", "amk_amir82@yahoo.com"),
                new User("amirhossein", "arabzadeh", "amirarab888@yahoo.com"),
                new User("ahmadreza", "khenari", "arezekhanari@gmail.com"));

        repository = new UserRepository(userList);
    }

    @Test
    public void getContainingUser__ShouldReturn() {
        User ali = repository.getUserByUsername("ali");
        assertNotNull(ali);
        assertEquals("ali", ali.getUsername());
        assertEquals("qwert", ali.getPassword());
    }

    @Test
    public void getNotContainingUser__ShouldReturnNull() {
        User user = repository.getUserByUsername("reza");
        assertNull(user);
    }

    @Test
    public void createRepositoryWithDuplicateUsers__ShouldThrowException() {
        User user1 = new User("ali", "1234");
        User user2 = new User("ali", "4567");
        assertThrows(IllegalArgumentException.class, () -> {
            new UserRepository(List.of(user1, user2));
        });
    }

    @Test
    public void createRepositoryWithDuplicateUsersWIthDuplicateEmails__ShouldThrowException() {
        User user1 = new User("test_a", "test_psw", "test@test.com");
        User user2 = new User("reza", "4567", "test@test.com");
        assertThrows(IllegalArgumentException.class, () -> {
            new UserRepository(List.of(user1, user2));
        });
    }

    @Test
    public void addNewUser__ShouldIncreaseUserCount() {
        int oldUserCount = repository.getUserCount();

        // Given
        String username = "reza";
        String password = "123abc";
        String email = "reza@sharif.edu";
        User newUser = new User(username, password);

        // When
        repository.addUser(newUser);

        // Then
        assertEquals(oldUserCount + 1, repository.getUserCount());
    }

    @Test
    public void addNewUserEmptyEmail__ShouldIncreaseUserCount() {
        int oldUserCount = repository.getUserCount();

        // Given
        String username = "test_a";
        String password = "test_psw";
        String email = "";
        User newUser = new User(username, password, email);

        // When
        repository.addUser(newUser);

        // Then
        assertEquals(oldUserCount + 1, repository.getUserCount());
    }

    @Test
    public void getContainingUserByEmail__ShouldReturn() {
        User ali = repository.getUserByEmail("amk_amir82@yahoo.com");
        assertNotNull(ali);
        assertEquals("amirmahdi", ali.getUsername());
        assertEquals("kousheshi", ali.getPassword());
        assertEquals("amk_amir82@yahoo.com", ali.getEmail());
    }

    @Test
    public void getNotContainingUserByEmail__ShouldReturnNull() {
        User user = repository.getUserByEmail("amkamir96@gmail.com");
        assertNull(user);
    }

    @Test
    public void removeExistingUser__ShouldReturnTrue() {
        int oldUserCount = repository.getUserCount();
        // Given
        String username = "ahmadreza";

        // When
        boolean result = repository.removeUser(username);

        // Then
        assertTrue(result);
        assertNull(repository.getUserByUsername(username));
        assertNull(repository.getUserByEmail("arezekhanari@gmail.com"));
        assertEquals(oldUserCount - 1, repository.getUserCount());
    }

    @Test
    public void removeNonExistingUser__ShouldReturnFalse() {
        int oldUserCount = repository.getUserCount();
        // Given
        String username = "test_a";

        // When
        boolean result = repository.removeUser(username);

        // Then
        assertFalse(result);
        assertEquals(oldUserCount, repository.getUserCount());
    }

    @Test
    public void removeExistingUserWithEmptyEmail__ShouldReturnTrue() {
        int oldUserCount = repository.getUserCount();
        // Given
        String username = "abcd";

        // When
        boolean result = repository.removeUser(username);

        // Then
        assertTrue(result);
        assertNull(repository.getUserByEmail(""));
        assertEquals(oldUserCount - 1, repository.getUserCount());
    }

    @Test
    public void removeExistingUserWithNullEmail__ShouldReturnTrue() {
        int oldUserCount = repository.getUserCount();
        // Given
        String username = "admin";

        // When
        boolean result = repository.removeUser(username);

        // Then
        assertTrue(result);
        assertEquals(oldUserCount - 1, repository.getUserCount());
    }

}
