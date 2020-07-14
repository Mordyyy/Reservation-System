package ge.edu.freeuni.DAO;

import ge.edu.freeuni.Models.Reservation;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservedDAO {
    private Connection con;

    public ReservedDAO(String url, String user_name, String password) throws SQLException {
        this.con = DriverManager.getConnection(url, user_name, password);
    }

    public void addReservation(Reservation reservation) throws SQLException {
        PreparedStatement st = con.prepareStatement("insert into reservations values (?,?,?)");
        st.setString(1, reservation.getUsername());
        st.setInt(2, reservation.getTime());
        st.setInt(3, reservation.getCompID());
        st.executeUpdate();
        st.close();
    }

    public List<Reservation> getAllByUser(String username) throws SQLException {
        PreparedStatement st = con.prepareStatement("select * from reservations " +
                "where username = ?");
        st.setString(1,username);
        ResultSet res = st.executeQuery();
        if (!res.next()) {
            st.close();
            return null;
        }
        return getFromResultSet(res);
    }

    public List<Reservation> getAllByTimeComp(int time, int compID) throws SQLException {
        PreparedStatement st = con.prepareStatement("select * from reservations " +
                "where time = ? and computerID = ?");
        st.setInt(1,time);
        st.setInt(2,compID);
        ResultSet res = st.executeQuery();
        if (!res.next()) {
            st.close();
            return null;
        }
        return getFromResultSet(res);
    }

    private List<Reservation> getFromResultSet(ResultSet res) throws SQLException {
        List<Reservation> reservations = new ArrayList<>();
        Reservation reservation = new Reservation(res.getString("username"), res.getInt(2), res.getInt(3));
        reservations.add(reservation);
        while(res.next()){
            reservation = new Reservation(res.getString("username"),
            res.getInt(2), res.getInt(3));
            reservations.add(reservation);
        }
        return reservations;
    }

}
