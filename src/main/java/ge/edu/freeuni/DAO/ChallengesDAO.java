package ge.edu.freeuni.DAO;

import ge.edu.freeuni.Models.Challenge;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/*
* This database has 5 fields.
* 1. id, which is unique for challenges
* 2. fromUser - user's username, who is challenging another user
* 3. toUser - user's username, who is challenged
* 4. meeting_time - reservation's time
* 5. computerID - computer's id
* */

public class ChallengesDAO {

    private Connection con;

    public ChallengesDAO(String url, String user_name, String password) throws SQLException {
        this.con = DriverManager.getConnection(url, user_name, password);
    }

    // returns challenge's id from all these information below
    public int getChallengeId(String fromUser, String toUser, int time, int compID) throws SQLException {
        String query = "select id from challenges where fromUser = ? and toUser = ?" +
                "and meeting_time = ? and computerID = ?";
        PreparedStatement st = con.prepareStatement(query);
        st.setString(1,fromUser);
        st.setString(2, toUser);
        st.setInt(3,time);
        st.setInt(4,compID);
        ResultSet rs = st.executeQuery();
        if (!rs.next()) {
            st.close();
            return -1;
        }
        return rs.getInt(1);
    }

    // returns challenge using this id
    public Challenge getChallenge(int id) throws SQLException {
        PreparedStatement st;
        st = con.prepareStatement("select * from challenges where id = ?");
        st.setInt(1, id);
        ResultSet rs = st.executeQuery();
        if (!rs.next()) {
            st.close();
            return null;
        }
        Challenge toRet = new Challenge(rs.getInt("id"), rs.getString("fromUser"),
                rs.getString("toUSer"), rs.getInt("meeting_time"), rs.getInt("computerID"));
        return toRet;
    }

    // adding challenge
    public boolean addChallenge(Challenge challenge) throws SQLException {
        PreparedStatement st = null;
        st = con.prepareStatement("Insert into challenges (fromUser, toUser, meeting_time," +
                " computerID) values (?, ?, ?, ?)");
        st.setString(1, challenge.getFromUser());
        st.setString(2, challenge.getToUser());
        st.setInt(3, challenge.getTime());
        st.setInt(4, challenge.getComputerID());
        int res = st.executeUpdate();
        st.close();
        return (res == 1);
    }

    public boolean deleteChallenge(int id) throws SQLException {
        PreparedStatement st;
        st = con.prepareStatement("Delete from challenges where id = ?");
        st.setInt(1, id);
        int res = st.executeUpdate();
        return res == 1;
    }

    // removes challenge if it is less than given time
    public boolean deleteTimedOutChallenges(int time) throws SQLException {
        PreparedStatement st = con.prepareStatement("delete from challenges where meeting_time <= ?");
        st.setInt(1, time);
        int res = st.executeUpdate();
        return res > 0;
    }

    public boolean deleteChallenge(Challenge chall) throws SQLException {
        PreparedStatement st;
        st = con.prepareStatement("Delete from challenges where fromUSer = ? and toUser = ?" +
                " and meeting_time = ? and computerID = ?");
        st.setString(1, chall.getFromUser());
        st.setString(2, chall.getToUser());
        st.setInt(3, chall.getTime());
        st.setInt(4, chall.getComputerID());
        int res = st.executeUpdate();
        return res == 1;
    }

    public boolean removeChallengesByChallenge(Challenge chall) throws SQLException {
        return removeAllForFromUser(chall) && removeAllForToUser(chall) && removeAllForComputerTime(chall);
    }

    public boolean removeAllForComputerTime(Challenge chall) throws SQLException {
        PreparedStatement st;
        st = con.prepareStatement("Delete from challenges where meeting_time = ? and computerID = ?");
        st.setInt(1, chall.getTime());
        st.setInt(2, chall.getComputerID());
        int res = st.executeUpdate();
        return res > 0;
    }


    private boolean removeAllForToUser(Challenge chall) throws SQLException {
        return removeUserAsToUser(chall, chall.getToUser()) && removeUserAsFromUser(chall, chall.getToUser());
    }

    private boolean removeAllForFromUser(Challenge chall) throws SQLException {
        return removeUserAsToUser(chall, chall.getFromUser()) && removeUserAsFromUser(chall, chall.getFromUser());
    }

    private boolean removeUserAsFromUser(Challenge chall, String user) throws SQLException {
        PreparedStatement st;
        st = con.prepareStatement("Delete from challenges where fromUser = ? and meeting_time = ?");
        st.setString(1, user);
        st.setInt(2, chall.getTime());
        int res = st.executeUpdate();
        return res > 0;
    }

    private boolean removeUserAsToUser(Challenge chall, String user) throws SQLException {
        PreparedStatement st;
        st = con.prepareStatement("Delete from challenges where toUser = ? and meeting_time = ?");
        st.setString(1, user);
        st.setInt(2, chall.getTime());
        int res = st.executeUpdate();
        return res > 0;
    }

    public void removeAll() throws SQLException {
        PreparedStatement st;
        st = con.prepareStatement("Delete from challenges");
        st.executeUpdate();
    }

    // returns all challenges between these to users
    public List<Challenge> getAllForTwo(String fromUser, String toUser) throws SQLException {
        PreparedStatement st = null;
        List<Challenge> lst = new ArrayList<>();
        st = con.prepareStatement("Select * from challenges where fromUser = ? and toUser = ?");
        st.setString(1, fromUser);
        st.setString(2, toUser);
        ResultSet res = st.executeQuery();
        if (!res.next()) {
            st.close();
            return lst;
        }
        List<Challenge> challenges = new ArrayList<>();
        Challenge chall = new Challenge(res.getInt("id"), res.getString("fromUser"), res.getString("toUser"),
                res.getInt("meeting_time"), res.getInt("computerID"));
        challenges.add(chall);
        while (res.next()) {
            chall = new Challenge(res.getInt("id"), res.getString("fromUser"), res.getString("toUser"),
                    res.getInt("meeting_time"), res.getInt("computerID"));
            challenges.add(chall);
        }
        st.close();
        return challenges;
    }

    // gives all challenges for this computer and time
    public List<Challenge> getAllForComputerTime(int time, int computer) throws SQLException {
        PreparedStatement st = null;
        List<Challenge> lst = new ArrayList<>();
        st = con.prepareStatement("Select * from challenges where meeting_time = ? and computerID = ?");
        st.setInt(1, time);
        st.setInt(2, computer);
        ResultSet res = st.executeQuery();
        if (!res.next()) {
            st.close();
            return lst;
        }
        List<Challenge> challenges = new ArrayList<>();
        Challenge chall = new Challenge(res.getInt("id"), res.getString("fromUser"), res.getString("toUser"),
                res.getInt("meeting_time"), res.getInt("computerID"));
        challenges.add(chall);
        while (res.next()) {
            chall = new Challenge(res.getInt("id"), res.getString("fromUser"), res.getString("toUser"),
                    res.getInt("meeting_time"), res.getInt("computerID"));
            challenges.add(chall);
        }
        st.close();
        return challenges;
    }

    // gives all challenges which is sent by this user
    public List<Challenge> getAllSent(String fromUser) throws SQLException {
        PreparedStatement st = null;
        List<Challenge> lst = new ArrayList<>();
        st = con.prepareStatement("Select * from challenges where fromUser = ?");
        st.setString(1, fromUser);
        ResultSet res = st.executeQuery();
        if (!res.next()) {
            st.close();
            return lst;
        }
        List<Challenge> challenges = new ArrayList<>();
        Challenge chall = new Challenge(res.getInt("id"), res.getString("fromUser"), res.getString("toUser"),
                res.getInt("meeting_time"), res.getInt("computerID"));
        challenges.add(chall);
        while (res.next()) {
            chall = new Challenge(res.getInt("id"), res.getString("fromUser"), res.getString("toUser"),
                    res.getInt("meeting_time"), res.getInt("computerID"));
            challenges.add(chall);
        }
        st.close();
        return challenges;
    }

    // gives all received challenges for this user
    public List<Challenge> getAllReceived(String toUser) throws SQLException {
        PreparedStatement st;
        List<Challenge> lst = new ArrayList<>();
        st = con.prepareStatement("Select * from challenges where toUser = ?");
        st.setString(1, toUser);
        ResultSet res = st.executeQuery();
        if (!res.next()) {
            st.close();
            return lst;
        }
        List<Challenge> challenges = new ArrayList<>();
        Challenge chall = new Challenge(res.getInt("id"), res.getString("fromUser"), res.getString("toUser"),
                res.getInt("meeting_time"), res.getInt("computerID"));
        challenges.add(chall);
        while (res.next()) {
            chall = new Challenge(res.getInt("id"), res.getString("fromUser"), res.getString("toUser"),
                    res.getInt("meeting_time"), res.getInt("computerID"));
            challenges.add(chall);
        }
        st.close();
        return challenges;
    }

    // removes all challenges that are connected with this user
    public boolean removeAllForUser(String user) throws SQLException {
        PreparedStatement st = con.prepareStatement("delete from challenges where fromUser = ? or toUser = ? ");
        st.setString(1, user);
        st.setString(2, user);
        int res = st.executeUpdate();
        return res > 0;
    }
}
