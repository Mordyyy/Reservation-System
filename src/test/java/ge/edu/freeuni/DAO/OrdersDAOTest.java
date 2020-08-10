package ge.edu.freeuni.DAO;

import ge.edu.freeuni.Models.Challenge;
import ge.edu.freeuni.Models.User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class OrdersDAOTest {
    private final String url = "jdbc:mysql://localhost/Reservation";
    private final String username = "root";
    private final String password = "4546";
    private OrdersDAO ordersDAO;
    private UsersDAO usersDAO;
    private User user;

    @BeforeEach
    public void init() throws SQLException {
        ordersDAO = new OrdersDAO(url,username,password);
        usersDAO = new UsersDAO(url, username, password);
        user = new User("Bot3");
    }

    @AfterEach
    public void destructor() throws SQLException {
        Connection con = DriverManager.getConnection(url,username,password);
        PreparedStatement st = con.prepareStatement("delete from orders where username = 'Bot3';");
        st.executeUpdate();
    }


    @Test
    public void AddUser() throws SQLException {
        assertEquals(-1, ordersDAO.getUserBonus(user));
        assertTrue(ordersDAO.addUser(user));
        assertEquals(0, ordersDAO.getUserOrders(user.getUsername()));
    }

    @Test
    public void getSet() throws SQLException {
        assertEquals(-1, ordersDAO.getUserOrders(user.getUsername()));
        assertTrue(ordersDAO.addUser(user));
        assertEquals(0, ordersDAO.getUserBonus(user));
        assertEquals(0,ordersDAO.getUserOrders(user.getUsername()));
        user.setBonus(5);
        user.setOrders(5);
        ordersDAO.updateUserBonus(user);
        ordersDAO.updateUserOrders(user);
        assertEquals(5, ordersDAO.getUserBonus(user));
        assertEquals(5,ordersDAO.getUserOrders(user.getUsername()));
        assertEquals(0, ordersDAO.getTodayOrders(user.getUsername()));
        ordersDAO.updateTodayOrders(user.getUsername());
        assertEquals(1, ordersDAO.getTodayOrders(user.getUsername()));
        ordersDAO.reset();
        assertEquals(0, ordersDAO.getTodayOrders(user.getUsername()));

    }

}