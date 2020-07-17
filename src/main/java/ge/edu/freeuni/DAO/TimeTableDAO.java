package ge.edu.freeuni.DAO;

import ge.edu.freeuni.Models.Cell;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TimeTableDAO {
    private Connection con;

    public TimeTableDAO(String url, String user_name, String password) throws SQLException {
        this.con = DriverManager.getConnection(url, user_name, password);
    }

    public Cell get(int time, int computerID) throws SQLException {
        PreparedStatement st = con.prepareStatement("select * from time_table where meeting_time = ? and computerID = ?");
        st.setInt(1, time);
        st.setInt(2, computerID);
        ResultSet rs = st.executeQuery();
        if (!rs.next()) {
            st.close();
            return null;
        }
        Cell toRet = new Cell(rs.getInt(2), rs.getInt(3), rs.getString(4), rs.getString(5));
        return toRet;
    }

    public boolean add(int id, Cell cell) throws SQLException {
        PreparedStatement st;
        st = con.prepareStatement("insert into time_table values(?, ?, ?, ?, ?)");
        st.setInt(1, id);
        st.setInt(2, cell.getTime());
        st.setInt(3, cell.getComputerID());
        st.setString(4, cell.getText());
        st.setString(5, cell.getColor());

        int res = st.executeUpdate();
        return res == 1;
    }

    public boolean update(Cell cell) throws SQLException {
        PreparedStatement st = con.prepareStatement("update time_table set text = ?, color = ? where meeting_time = ? and computerID = ?");
        st.setString(1, cell.getText());
        st.setString(2, cell.getColor());
        st.setInt(3, cell.getTime());
        st.setInt(4, cell.getComputerID());
        int res = st.executeUpdate();
        return res > 0;
    }

    public List<Cell> getAll() throws SQLException {
        PreparedStatement st = con.prepareStatement("Select * from time_table");
        ResultSet rs = st.executeQuery();
        if (!rs.next()) {
            return new ArrayList<>();
        }
        List<Cell> lst = new ArrayList<>();
        Cell curCell = new Cell(rs.getInt("meeting_time"), rs.getInt("computerID"),
                rs.getString("text"), rs.getString("color"));
        lst.add(curCell);
        while (rs.next()) {
            curCell = new Cell(rs.getInt("meeting_time"), rs.getInt("computerID"),
                    rs.getString("text"), rs.getString("color"));
            lst.add(curCell);
        }
        return lst;
    }

    public void reset() throws SQLException {
        PreparedStatement st = con.prepareStatement("update time_table set text = ?, color = ?");
        st.setString(1, "Free");
        st.setString(2, "green");
        st.executeUpdate();
    }

    public void resetWithGrey() throws SQLException {
        PreparedStatement st = con.prepareStatement("update time_table set text = ?, color = ?");
        st.setString(1, "Timed Out");
        st.setString(2, "grey");
        st.executeUpdate();
    }
}
