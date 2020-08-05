package ge.edu.freeuni.Helpers;

import ge.edu.freeuni.DAO.ChallengesDAO;
import ge.edu.freeuni.DAO.LastResetDAO;
import ge.edu.freeuni.DAO.ReservedDAO;
import ge.edu.freeuni.DAO.TimeTableDAO;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TableGrey {

    public void tableGrey(HttpServletRequest req) throws SQLException {
        LastResetDAO lastResetDAO = (LastResetDAO) req.getServletContext().getAttribute("lastReset");
        ReservedDAO reservedDAO = (ReservedDAO) req.getServletContext().getAttribute("reserved");
        ChallengesDAO challengesDAO = (ChallengesDAO) req.getServletContext().getAttribute("challenges");
        TimeTableDAO timeTableDAO = (TimeTableDAO) req.getServletContext().getAttribute("table");

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = formatter.format(cal.getTime());
        String resetDate = lastResetDAO.get();

        if (date.compareTo(resetDate) >= 0) {
            challengesDAO.removeAll();
            reservedDAO.removeAll();
            timeTableDAO.resetWithGrey();

        }
    }

}
