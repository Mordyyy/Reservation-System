package ge.edu.freeuni.Listeners;

import ge.edu.freeuni.DAO.*;
import ge.edu.freeuni.Models.Cell;
import ge.edu.freeuni.Models.Email;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.awt.*;
import java.sql.SQLException;

public class ContextListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent event) {
        ServletContext sc = event.getServletContext();
        String url = sc.getInitParameter("url");
        String user_name = sc.getInitParameter("user_name");
        String password = sc.getInitParameter("password");
        String database = sc.getInitParameter("database");
        UsersDAO db = null;
        try {
            db = new UsersDAO(url + database, user_name, password);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        BlacklistDAO blacklist = null;
        try {
            blacklist = new BlacklistDAO(url + database, user_name, password);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        Email mail = new Email();
        ChallengesDAO challenges = null;
        try {
            challenges = new ChallengesDAO(url + database, user_name, password);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        ImageDAO images = new ImageDAO(url + database, user_name, password);
        ReservedDAO reservedDAO = null;
        try {
            reservedDAO = new ReservedDAO(url + database, user_name, password);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        TimeTableDAO timeTable = null;
        try {
            timeTable = new TimeTableDAO(url + database, user_name, password);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        for(int i = 10; i <= 21; i++){
            for(int j = 0; j <= 9; j++){
                try {
                    timeTable.add(10*i + j, new Cell(i,j,"Free", "green"));
                } catch (SQLException ignored) {}
            }
        }

        sc.setAttribute("reserved", reservedDAO);
        sc.setAttribute("table", timeTable);
        sc.setAttribute("challenges", challenges);
        sc.setAttribute("email", mail);
        sc.setAttribute("db", db);
        sc.setAttribute("blacklist", blacklist);
        sc.setAttribute("images", images);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {}

}
