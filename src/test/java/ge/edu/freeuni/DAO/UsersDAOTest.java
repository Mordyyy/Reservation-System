package ge.edu.freeuni.DAO;

import ge.edu.freeuni.Models.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UsersDAOTest {
    private final String url = "jdbc:mysql://localhost/Reservation";
    private final String username = "root";
    private final String password = "4546";
    private UsersDAO usersDAO;
    private User user;

    @BeforeEach
    public void init() throws SQLException {
        usersDAO = new UsersDAO(url, username, password);
        usersDAO.removeALl();
        user = new User("username", "someHash",
                "example@gmail.com", "pic.jpg");
    }

    @AfterEach
    public void destr() throws SQLException {
        usersDAO.removeUser("username");
    }

    @Test
    public void addContainsTest() throws SQLException {
        assertDoesNotThrow(()-> usersDAO.addUser(user));
        assertTrue(usersDAO.contains(user.getUsername()));
    }

    @Test
    public void getAll() throws SQLException {
        usersDAO.addUser(user);
        List<User> lst = usersDAO.getAll();
        assertEquals(1, lst.size());
        User curUser = lst.get(0);
        assertEquals(curUser.getUsername(), user.getUsername());
        assertEquals(curUser.getPassword(), user.getPassword());
        assertEquals(curUser.getMail(), user.getMail());
        assertEquals(curUser.getAvatar(), user.getAvatar());
        usersDAO.addUser(new User("username1", "someHash",
                "example1@gmail.com", "1.png"));
        lst = usersDAO.getAll();
        assertEquals(2, lst.size());
        usersDAO.removeUser("username");
        usersDAO.removeUser("username1");
        lst = usersDAO.getAll();
        assertEquals(0,lst.size());
    }

    @Test
    public void getUserByMailAndUsername() throws SQLException {
        usersDAO.addUser(user);
        User curUser = usersDAO.getUserByeMail("example@gmail.com");
        assertEquals(curUser.getUsername(), user.getUsername());
        curUser = usersDAO.getUserByUsername("username");
        assertEquals(curUser.getMail(), "example@gmail.com");
        curUser = usersDAO.getUserByUsername("bla");
        assertNull(curUser);
        curUser = usersDAO.getUserByeMail("bla@gmail.com");
        assertNull(curUser);
    }

    @Test
    public void changePasswordAvatar() throws SQLException {
        usersDAO.addUser(user);
        usersDAO.changePassword("username", "password");
        assertEquals("password", usersDAO.getUserByUsername("username").getPassword());
        user.setAvatar("pic1.jpg");
        usersDAO.changeAvatar(user);
        assertEquals("pic1.jpg", usersDAO.getUserByUsername("username").getAvatar());
    }

}