package ge.edu.freeuni.DAO;

import java.sql.*;

public class LastResetDAO {
    private Connection con;

    public LastResetDAO(String url, String user_name, String password) throws SQLException {
        this.con = DriverManager.getConnection(url, user_name, password);
    }

    public boolean update(String Date) throws SQLException {
        PreparedStatement st = con.prepareStatement("delete from last_reset");
        st.executeUpdate();
        st = con.prepareStatement("insert into last_reset values(?)");
        st.setString(1,Date);
        return st.executeUpdate() == 1;
    }

    public String get() throws SQLException {
        PreparedStatement st = con.prepareStatement("select * from last_reset");
        ResultSet rs = st.executeQuery();
        if (!rs.next()) {
            return null;
        }
        return rs.getString(1);
    }
}
