package ir.selab.tdd;

import ir.selab.tdd.domain.User;
import ir.selab.tdd.repository.UserRepository;
import ir.selab.tdd.service.UserService;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class UserServiceTest {
    private UserService userService;

    @Before
    public void setUp() {
        UserRepository userRepository = new UserRepository(List.of());
        userService = new UserService(userRepository);
        userService.registerUser("admin", "1234");
        userService.registerUser("ali", "qwert");
        userService.registerUser("amirmahdi", "kousheshi", "amk_amir82@yahoo.com");
        userService.registerUser("amirhossein", "arabzadeh", "amirarab888@yahoo.com");
        userService.registerUser("ahmadreza", "khenari", "arezekhanari@gmail.com");
    }

    @Test
    public void createNewValidUser__ShouldSuccess() {
        String username = "reza";
        String password = "123abc";
        boolean b = userService.registerUser(username, password);
        assertTrue(b);
    }

    @Test
    public void createNewDuplicateUser__ShouldFail() {
        String username = "ali";
        String password = "123abc";
        boolean b = userService.registerUser(username, password);
        assertFalse(b);
    }

    @Test
    public void createNewUserWithEmail_ShouldSuccess() {
        String username = "test_a_usrnm";
        String password = "test_a_psw";
        String email = "test@test.com";
        boolean b = userService.registerUser(username, password, email);
        assertTrue(b);
    }

    @Test
    public void createNewDuplicateUserWithEmail__ShouldFail() {
        String username = "test_a";
        String password = "test_a";
        String email = "amk_amir82@yahoo.com";
        boolean b = userService.registerUser(username, password, email);
        assertFalse(b);
    }

    @Test
    public void loginWithValidUsernameAndPassword__ShouldSuccess() {
        boolean login = userService.loginWithUsername("admin", "1234");
        assertTrue(login);
    }

    @Test
    public void loginWithValidUsernameAndInvalidPassword__ShouldFail() {
        boolean login = userService.loginWithUsername("admin", "abcd");
        assertFalse(login);
    }

    @Test
    public void loginWithInvalidUsernameAndInvalidPassword__ShouldFail() {
        boolean login = userService.loginWithUsername("ahmad", "abcd");
        assertFalse(login);
    }
}
