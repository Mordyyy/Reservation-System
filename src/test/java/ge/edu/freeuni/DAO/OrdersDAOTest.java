package ge.edu.freeuni.DAO;

import ge.edu.freeuni.Models.Challenge;
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

    @BeforeEach
    public void init() throws SQLException {
        ordersDAO = new OrdersDAO(url,username,password);
    }

    @AfterEach
    public void destructor() throws SQLException {
        Connection con = DriverManager.getConnection(url,username,password);
        PreparedStatement st = con.prepareStatement("delete from orders where username = 'Bot1';");
        st.executeUpdate();
    }


    @Test
    public void AddUser() throws SQLException {
        assertEquals(-1, ordersDAO.getUserBonus("Bot1"));
        assertTrue(ordersDAO.addUser("Bot1"));
    }

    @Test
    public void getSet() throws SQLException {
        assertEquals(-1, ordersDAO.getUserOrders("Bot1"));
        assertTrue(ordersDAO.addUser("Bot1"));
        assertEquals(0, ordersDAO.getUserBonus("Bot1"));
        assertEquals(0,ordersDAO.getUserOrders("Bot1"));
        ordersDAO.updateUserBonus("Bot1", 5);
        ordersDAO.updateUserOrders("Bot1", 5);
        assertEquals(5, ordersDAO.getUserBonus("Bot1"));
        assertEquals(5,ordersDAO.getUserOrders("Bot1"));

    }

}