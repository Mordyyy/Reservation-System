package ge.edu.freeuni.DAO;

import ge.edu.freeuni.Models.Challenge;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ChallengesDAOTest {
    private final String url = "jdbc:mysql://localhost/Reservation";
    private final String username = "root";
    private final String password = "4546";
    private ChallengesDAO challengesDAO;
    private Challenge chal;
    private int id;

    @BeforeEach
    public void init() throws SQLException {
        challengesDAO = new ChallengesDAO(url,username,password);
        chal = new Challenge("user1", "user2", -1,10);
        assertTrue(challengesDAO.addChallenge(chal));
        id = challengesDAO.getChallengeId("user1", "user2", -1, 10);
    }


    @Test
    public void addRemoveChallenge() throws SQLException {
        Challenge chala = challengesDAO.getChallenge(id);
        assertTrue(chala.getTime() == chal.getTime() && chala.getComputerID() == chal.getComputerID());
        assertTrue(chala.getToUser().equals(chal.getToUser()) && chala.getFromUser().equals(chal.getFromUser()));
        assertTrue(challengesDAO.deleteChallenge(id));
        assertNull(challengesDAO.getChallenge(id));
        assertTrue(challengesDAO.addChallenge(chal));
        assertTrue(challengesDAO.deleteChallenge(chal));
        assertEquals(challengesDAO.getChallengeId("b", "b", 1, 2), -1);
    }

    @Test
    public void timedOutChallenges() throws SQLException {
        Challenge chala = new Challenge("user2", "user1", -1, 9);
        assertTrue(challengesDAO.addChallenge(chala));
        assertTrue(challengesDAO.deleteTimedOutChallenges(-1));
        assertNull(challengesDAO.getChallenge(id));
        assertTrue(challengesDAO.addChallenge(chal));
        assertTrue(challengesDAO.addChallenge(chala));
        id = challengesDAO.getChallengeId("user1", "user2", -1, 10);
        assertFalse(challengesDAO.removeChallengesByChallenge(chal));
        assertNull(challengesDAO.getChallenge(id));
    }

    @Test
    public void removeAllForComputerTime() throws SQLException {
        assertTrue(challengesDAO.removeAllForComputerTime(chal));
        assertNull(challengesDAO.getChallenge(id));
    }

    @Test
    public void getAllForTwo() throws SQLException {
        Challenge chala = new Challenge("user1", "user2", -2,10);
        assertTrue(challengesDAO.addChallenge(chala));
        List<Challenge> lst = challengesDAO.getAllForTwo("user1", "user2");
        assertEquals(2, lst.size());
        assertTrue(challengesDAO.deleteChallenge(chal));
        assertTrue(challengesDAO.deleteChallenge(chala));
    }

    @Test
    public void removeAll() throws SQLException {
        challengesDAO.removeAll();
        List<Challenge> lst = challengesDAO.getAllForTwo("user1", "user2");
        assertEquals(0, lst.size());
    }

    @Test
    public void getAllForComputerTime() throws SQLException {
        List<Challenge> lst = challengesDAO.getAllForComputerTime(-1,10);
        Challenge chala = lst.get(0);
        assertTrue(chala.getTime() == chal.getTime() && chala.getComputerID() == chal.getComputerID());
        assertTrue(chala.getToUser().equals(chal.getToUser()) && chala.getFromUser().equals(chal.getFromUser()));
        chala = new Challenge("user3", "user4", -1,10);
        assertTrue(challengesDAO.addChallenge(chala));
        lst = challengesDAO.getAllForComputerTime(-1, 10);
        assertEquals(2, lst.size());
        assertTrue(challengesDAO.deleteChallenge(chala));
        assertTrue(challengesDAO.deleteChallenge(chal));
        lst = challengesDAO.getAllForComputerTime(-1, 10);
        assertEquals(0,lst.size());
    }

    @Test
    public void getAllSent() throws SQLException {
        List<Challenge> lst = challengesDAO.getAllSent("unknown");
        assertEquals(0,lst.size());
        Challenge chala = new Challenge("user1", "user3", -1,-1);
        assertTrue(challengesDAO.addChallenge(chala));
        lst = challengesDAO.getAllSent("user1");
        System.out.println(lst.toString());
        assertEquals(2,lst.size());
        assertTrue(challengesDAO.deleteChallenge(chal));
        assertTrue(challengesDAO.deleteChallenge(chala));
    }

    @Test
    public void getAllReceived() throws SQLException {
        List<Challenge> lst = challengesDAO.getAllReceived("unknown");
        assertEquals(0,lst.size());
        Challenge chala = new Challenge("user1", "user2", -1,-1);
        assertTrue(challengesDAO.addChallenge(chala));
        lst = challengesDAO.getAllReceived("user2");
        System.out.println(lst.toString());
        assertEquals(2,lst.size());
        assertTrue(challengesDAO.deleteChallenge(chal));
        assertTrue(challengesDAO.deleteChallenge(chala));
    }

}