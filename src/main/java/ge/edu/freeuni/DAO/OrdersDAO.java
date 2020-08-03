package ge.edu.freeuni.DAO;

import java.sql.*;

public class OrdersDAO {
    private Connection con;

    public OrdersDAO(String url, String user_name, String password) throws SQLException {
        this.con = DriverManager.getConnection(url, user_name, password);
    }

    public boolean addUser(String username) throws SQLException {
        String query = "insert into orders (username, orders_num, bonus_num) " +
                       "values (?, ?, ?)";
        PreparedStatement st = con.prepareStatement(query);
        st.setString(1,username);
        st.setInt(2, 0);
        st.setInt(3, 0);
        int res = st.executeUpdate();
        return res == 1;

    }

    public int getUserOrders(String username) throws SQLException {
        ResultSet rs = getResultSet(username);
        if(!rs.next()){
            rs.close();
            return -1;
        }
        return rs.getInt(1);
    }

    public int getUserBonus(String username) throws SQLException {
        ResultSet rs = getResultSet(username);
        if(!rs.next()){
            return -1;
        }
        return rs.getInt(1);
    }

    public void updateUserBonus(String username, int bonus) throws SQLException {
        String query = "update orders set bonus_num = ? where username = ?";
        PreparedStatement st = con.prepareStatement(query);
        st.setInt(1, bonus);
        st.setString(2, username);
        st.executeUpdate();
        st.close();
    }

    public void updateUserOrders(String username, int orders) throws SQLException {
        String query = "update orders set orders_num = ? where username = ?";
        PreparedStatement st = con.prepareStatement(query);
        st.setInt(1, orders);
        st.setString(2, username);
        st.executeUpdate();
        st.close();
    }

    private ResultSet getResultSet(String username) throws SQLException {
        String query = "select orders_num from orders where username = ?";
        PreparedStatement st = con.prepareStatement(query);
        st.setString(1,username);
        ResultSet rs = st.executeQuery();
        return rs;
    }

}
