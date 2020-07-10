package ge.edu.freeuni.DAO;

import ge.edu.freeuni.Models.Challenge;
import ge.edu.freeuni.Models.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ChallengesDAO {

    private Connection con;

    public ChallengesDAO(String url, String user_name, String password) {
        try {
            this.con = DriverManager.getConnection(url, user_name, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean addChallenge(Challenge challenge) {
        PreparedStatement st = null;
        try {
            st = con.prepareStatement("Insert into callenges " +
                    "values (?, ?, ?, ?)");
            st.setString(1, challenge.getFromUser());
            st.setString(2, challenge.getToUser());
            st.setInt(3, challenge.getTime());
            st.setInt(4, challenge.getComputerID());
            int res = st.executeUpdate();
            st.close();
            return (res == 1);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public boolean deleteChallenge(Challenge chall){
        PreparedStatement st;
        try {
            st = con.prepareStatement("Delete from challenges where fromUSer = ? and toUser = ?" +
                    " and meeting_time = ? and computerID = ?");
            st.setString(1, chall.getFromUser());
            st.setString(2, chall.getToUser());
            st.setInt(3, chall.getTime());
            st.setInt(4, chall.getComputerID());
            int res = st.executeUpdate();
            return res == 1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public boolean removeChallengesByChallenge(Challenge chall){
       return removeAllForFromUser(chall) && removeAllForToUser(chall) && removeAllForComputerTime(chall);
    }

    private boolean removeAllForComputerTime(Challenge chall) {
        PreparedStatement st;
        try {
            st = con.prepareStatement("Delete from challenges where meeting_time = ? and computerID = ?");
            st.setInt(1, chall.getTime());
            st.setInt(2, chall.getComputerID());
            st.executeUpdate();
            return true;
        } catch (SQLException throwables) {
            return false;
        }
    }


    private boolean removeAllForToUser(Challenge chall) {
        return removeUserAsToUser(chall, chall.getToUser()) && removeUserAsFromUser(chall, chall.getToUser());
    }

    private boolean removeAllForFromUser(Challenge chall) {
        return removeUserAsToUser(chall, chall.getFromUser()) && removeUserAsFromUser(chall, chall.getFromUser());
    }

    private boolean removeUserAsFromUser(Challenge chall, String user) {
        PreparedStatement st;
        try {
            st = con.prepareStatement("Delete from challenges where fromUser = ? and meeting_time = ?");
            st.setString(1, user);
            st.setInt(2, chall.getTime());
            st.executeUpdate();
            return true;
        } catch (SQLException throwables) {
            return false;
        }
    }

    private boolean removeUserAsToUser(Challenge chall, String user) {
        PreparedStatement st;
        try {
            st = con.prepareStatement("Delete from challenges where toUser = ? and meeting_time = ?");
            st.setString(1, user);
            st.setInt(2, chall.getTime());
            st.executeUpdate();
            return true;
        } catch (SQLException throwables) {
            return false;
        }
    }

    public List<Challenge> getAllForTwo(String fromUser, String toUser) {
        PreparedStatement st = null;
        List<Challenge> lst = new ArrayList<>();
        try {
            st = con.prepareStatement("Select * from challenges where fromUser = ? and toUser = ?");
            st.setString(1, fromUser);
            st.setString(2, toUser);
            ResultSet res = st.executeQuery();
            if (!res.next()) {
                st.close();
                return lst;
            }
            List<Challenge> challenges = new ArrayList<>();
            Challenge chall = new Challenge(res.getString("fromUser"), res.getString("toUser"),
                    res.getInt("meeting_time"), res.getInt("computerID"));
            challenges.add(chall);
            while (res.next()) {
                chall = new Challenge(res.getString("fromUser"), res.getString("toUser"),
                        res.getInt("meeting_time"), res.getInt("computerID"));
                challenges.add(chall);
            }
            st.close();
            return challenges;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return lst;
    }

    public List<Challenge> getAllSent(String fromUser) {
        PreparedStatement st = null;
        List<Challenge> lst = new ArrayList<>();
        try {
            st = con.prepareStatement("Select * from challenges where fromUser = ?");
            st.setString(1, fromUser);
            ResultSet res = st.executeQuery();
            if (!res.next()) {
                st.close();
                return lst;
            }
            List<Challenge> challenges = new ArrayList<>();
            Challenge chall = new Challenge(res.getString("fromUser"), res.getString("toUser"),
                    res.getInt("meeting_time"), res.getInt("computerID"));
            challenges.add(chall);
            while (res.next()) {
                chall = new Challenge(res.getString("fromUser"), res.getString("toUser"),
                        res.getInt("meeting_time"), res.getInt("computerID"));
                challenges.add(chall);
            }
            st.close();
            return challenges;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return lst;
    }

    public List<Challenge> getAllReceived(String toUser) {
        PreparedStatement st = null;
        List<Challenge> lst = new ArrayList<>();
        try {
            st = con.prepareStatement("Select * from challenges where toUser = ?");
            st.setString(1, toUser);
            ResultSet res = st.executeQuery();
            if (!res.next()) {
                st.close();
                return lst;
            }
            List<Challenge> challenges = new ArrayList<>();
            Challenge chall = new Challenge(res.getString("fromUser"), res.getString("toUser"),
                        res.getInt("meeting_time"), res.getInt("computerID"));
            challenges.add(chall);
            while (res.next()) {
                chall = new Challenge(res.getString("fromUser"), res.getString("toUser"),
                        res.getInt("meeting_time"), res.getInt("computerID"));
                challenges.add(chall);
            }
            st.close();
            return challenges;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return lst;
    }
}
