package ge.edu.freeuni.DAO;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BlacklistDAOTest {
    private final String url = "jdbc:mysql://localhost/Reservation";
    private final String username = "root";
    private final String password = "4546";
    @Test
    public void AddUser(){
        BlacklistDAO bd = new BlacklistDAO(url,username,password);
        bd.addUser("Achi");
    }
}