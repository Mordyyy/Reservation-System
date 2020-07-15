package ge.edu.freeuni.DAO;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BlacklistDAOTest {
    private final String url = "jdbc:mysql://localhost/Reservation";
    private final String username = "root";
    private final String password = "4546";
    private BlacklistDAO blacklistDAO;

    @BeforeEach
    public void init() throws SQLException {
        blacklistDAO = new BlacklistDAO(url,username,password);
    }

    @Test
    public void successfulConnection() throws SQLException {
        BlacklistDAO bl = new BlacklistDAO(url,username,password);
    }

    @Test
    public void addRemoveGet() throws SQLException {
        assertTrue(blacklistDAO.addUser("user1"));
        assertTrue(blacklistDAO.getUser("user1"));
        assertFalse(blacklistDAO.getUser("user2"));
        assertTrue(blacklistDAO.removeUser("user1"));
        assertFalse(blacklistDAO.getUser("user1"));
    }

    @Test
    public void getAll() throws SQLException {
        blacklistDAO.addUser("user1");
        List<String> result = blacklistDAO.getAll();
        assertTrue(result.contains("user1"));
        assertTrue(blacklistDAO.removeUser("user1"));
    }

}