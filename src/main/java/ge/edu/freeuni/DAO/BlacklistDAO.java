package ge.edu.freeuni.DAO;

import ge.edu.freeuni.Models.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BlacklistDAO {
    private Connection con;

    public BlacklistDAO(String url, String user_name, String password) throws SQLException {
        this.con = DriverManager.getConnection(url, user_name, password);
    }

    public boolean addUser(String username) throws SQLException {
        PreparedStatement st = null;
        st = con.prepareStatement("Insert into blacklist (username) " + "values (?)");
        st.setString(1, username);
        int res = st.executeUpdate();
        st.close();
        return (res == 1);
    }

    public boolean getUser(String username) throws SQLException {
        PreparedStatement st = null;
        st = con.prepareStatement("Select * from blacklist where username = ?");
        st.setString(1, username);
        ResultSet res = st.executeQuery();
        if (!res.next()) {
            st.close();
            return false;
        }
        st.close();
        return true;
    }

    public boolean removeUser(String username) throws SQLException {
        PreparedStatement st = null;
        st = con.prepareStatement("Delete from blacklist where username = ?");
        st.setString(1, username);
        int res = st.executeUpdate();
        st.close();
        return (res == 1);
    }

    public List<String> getAll() throws SQLException {
        PreparedStatement st = null;
        List<String> lst = new ArrayList<>();
        st = con.prepareStatement("Select * from blacklist");
        ResultSet res = st.executeQuery();
        if (!res.next()) {
            st.close();
            return lst;
        }
        List<String> blacklist = new ArrayList<>();
        String username = res.getString("username");
        blacklist.add(username);
        while (res.next()) {
            username = res.getString("username");
            blacklist.add(username);
        }
        st.close();
        return blacklist;
    }

}
