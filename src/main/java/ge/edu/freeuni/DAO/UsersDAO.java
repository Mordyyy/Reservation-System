package ge.edu.freeuni.DAO;

import ge.edu.freeuni.Models.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsersDAO {

    private Connection con;

    public UsersDAO(String url, String user_name, String password) throws SQLException {
        this.con = DriverManager.getConnection(url, user_name, password);
    }

    public boolean addUser(User user) throws SQLException {
        PreparedStatement st = null;
        st = con.prepareStatement("Insert into users (username, password, mail, avatar) " +
                "values (?, ?, ?, ?, ?)");
        st.setString(1, user.getUsername());
        st.setString(2, user.getPassword());
        st.setString(3, user.getMail());
        st.setString(4, user.getAvatar());
        st.setDouble(5,user.getReliability());
        int res = st.executeUpdate();
        st.close();
        return (res == 1);
    }

    public User getUserByUsername(String username) throws SQLException {
        PreparedStatement st = null;
        st = con.prepareStatement("Select * from users where username = ?");
        st.setString(1, username);
        ResultSet res = st.executeQuery();
        if (!res.next()) {
            st.close();
            return null;
        }
        User user = new User(res.getString("username"), res.getString("password")
                , res.getString("mail"), res.getString("avatar"));
        st.close();
        return user;
    }

    public User getUserByeMail(String eMail) throws SQLException {
        PreparedStatement st = null;
        st = con.prepareStatement("Select * from users where mail = ?");
        st.setString(1, eMail);
        ResultSet res = st.executeQuery();
        if (!res.next()) {
            st.close();
            return null;
        }
        User user = new User(res.getString("username"), res.getString("password")
                , res.getString("mail"), res.getString("avatar"));
        st.close();
        return user;
    }

    public boolean changePassword(String username, String newPassword) throws SQLException {
        PreparedStatement st = null;
        st = con.prepareStatement("update users set password = ? where username = ?");
        st.setString(1, newPassword);
        st.setString(2, username);
        int res = st.executeUpdate();
        st.close();
        return res == 1;
    }

    public void changeAvatar(User user) throws SQLException {
        PreparedStatement st = null;
        st = con.prepareStatement("update users set avatar = ? where username = ?");
        st.setString(1, user.getAvatar());
        st.setString(2, user.getUsername());
        st.executeUpdate();
        st.close();
    }

    public void changeReliability(User user) throws SQLException {
        PreparedStatement st = null;
        st = con.prepareStatement("update users set reliability = ? where username = ?");
        st.setDouble(1, user.getReliability());
        st.setString(2, user.getUsername());
        st.executeUpdate();
        st.close();
    }

    public boolean removeUser(String username) throws SQLException {
        PreparedStatement st = null;
        st = con.prepareStatement("Delete from users where username = ?");
        st.setString(1, username);
        int res = st.executeUpdate();
        st.close();
        return (res == 1);
    }

    public boolean contains(String userName) throws SQLException {
        PreparedStatement st = con.prepareStatement("select * from users where username = ?");
        st.setString(1, userName);
        ResultSet rs = st.executeQuery();
        return getResultSetSize(rs) > 0;
    }

    private int getResultSetSize(ResultSet rs) throws SQLException {
        int res = 0;
        while (rs.next()) {
            res++;
        }
        return res;
    }

    public List<User> getAll() throws SQLException {
        PreparedStatement st = null;
        List<User> lst = new ArrayList<>();
        st = con.prepareStatement("Select * from users");
        ResultSet res = st.executeQuery();
        if (!res.next()) {
            st.close();
            return lst;
        }
        List<User> users = new ArrayList<>();
        User user = new User(res.getString("username"), res.getString("password")
                , res.getString("mail"), res.getString("avatar"));
        users.add(user);
        while (res.next()) {
            user = new User(res.getString("username"), res.getString("password")
                    , res.getString("mail"), res.getString("avatar"));
            users.add(user);
        }
        st.close();
        return users;
    }

    public boolean removeALl() throws SQLException {
        PreparedStatement st = null;
        st = con.prepareStatement("Delete from users");
        int res = st.executeUpdate();
        st.close();
        return (res > 0);
    }

}
