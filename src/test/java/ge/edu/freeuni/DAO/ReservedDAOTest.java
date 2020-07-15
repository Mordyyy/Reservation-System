package ge.edu.freeuni.DAO;

import ge.edu.freeuni.Models.Reservation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReservedDAOTest {
    private final String url = "jdbc:mysql://localhost/Reservation";
    private final String username = "root";
    private final String password = "4546";
    private ReservedDAO reservedDAO;
    private Reservation reservation;

    @BeforeEach
    public void init() throws SQLException {
        reservedDAO = new ReservedDAO(url,username, password);
        reservation = new Reservation("username", -1, -1);
    }

    @AfterEach
    public void destr() throws SQLException {
        reservedDAO.removeAll();
    }

    @Test
    public void addContainsTest() throws SQLException {
        assertDoesNotThrow(()-> reservedDAO.addReservation(reservation));
        assertTrue(reservedDAO.containsReservation(reservation));
    }

    @Test
    public void getAllByUser() throws SQLException {
        reservedDAO.addReservation(reservation);
        List<Reservation> lst = reservedDAO.getAllByUser("username");
        assertEquals(1, lst.size());
        Reservation reserv = lst.get(0);
        assertEquals(reserv.getUsername(), reservation.getUsername());
        assertEquals(reserv.getTime(), reservation.getTime());
        assertEquals(reserv.getCompID(), reservation.getCompID());
        reservedDAO.addReservation(new Reservation("username", -2,-2));
        lst = reservedDAO.getAllByUser("username");
        assertEquals(2, lst.size());
        reservedDAO.removeAll();
        lst = reservedDAO.getAllByUser("username");
        assertEquals(0,lst.size());
    }

    @Test
    public void getAllByTime() throws SQLException {
        reservedDAO.addReservation(reservation);
        List<Reservation> lst = reservedDAO.getAllByTime(-1);
        Reservation reserv = lst.get(0);
        assertEquals(reserv.getUsername(), reservation.getUsername());
        assertEquals(reserv.getTime(), reservation.getTime());
        assertEquals(reserv.getCompID(), reservation.getCompID());
        reservedDAO.removeAll();
        lst = reservedDAO.getAllByTime(-2);
        assertEquals(0,lst.size());
    }

}