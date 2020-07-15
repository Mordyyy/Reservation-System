package ge.edu.freeuni.DAO;

import ge.edu.freeuni.Models.Cell;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class TimeTableDAOTest {
    TimeTableDAO timeTableDAO;
    private final String url = "jdbc:mysql://localhost/Reservation";
    private final String username = "root";
    private final String password = "4546";

    @BeforeEach
    public void init() throws SQLException {
        timeTableDAO = new TimeTableDAO(url, username, password);
        timeTableDAO.reset();
    }

    @Test
    public void addGetUpdateTest() throws SQLException {
        Cell cell = new Cell(-1,-1,"text", "color");
        assertTrue(timeTableDAO.add(-1,cell));
        Cell cell2 = timeTableDAO.get(-1,-1);
        assertEquals(cell.getColor(), cell2.getColor());
        assertEquals(cell.getComputerID(), cell2.getComputerID());
        assertEquals(cell.getText(), cell2.getText());
        assertEquals(cell.getTime(), cell2.getTime());
        assertNull(timeTableDAO.get(-1,-100));
        timeTableDAO.update(new Cell(-1,-1, "newtext", "newcolor"));
        cell2 = timeTableDAO.get(-1,-1);
        assertNotEquals(cell.getColor(), cell2.getColor());
        assertEquals(cell.getComputerID(), cell2.getComputerID());
        assertNotEquals(cell.getText(), cell2.getText());
        assertEquals(cell.getTime(), cell2.getTime());
        Connection con = DriverManager.getConnection(url,username,password);
        String query = "delete from time_table where id = ?";
        PreparedStatement st = con.prepareStatement(query);
        st.setInt(1,-1);
        st.executeUpdate();
        st.close();
        timeTableDAO.getAll();
    }



}