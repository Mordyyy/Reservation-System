package ge.edu.freeuni.Listeners;

import ge.edu.freeuni.DAO.BlacklistDAO;
import ge.edu.freeuni.DAO.ChallengesDAO;
import ge.edu.freeuni.DAO.ImageDAO;
import ge.edu.freeuni.DAO.UsersDAO;
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
        UsersDAO db = new UsersDAO(url + database, user_name, password);
        BlacklistDAO blacklist = null;
        try {
            blacklist = new BlacklistDAO(url + database, user_name, password);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        Email mail = new Email();
        ChallengesDAO challenges = new ChallengesDAO(url + database, user_name, password);
        ImageDAO images = new ImageDAO(url + database, user_name, password);
        sc.setAttribute("challenges", challenges);
        sc.setAttribute("email", mail);
        sc.setAttribute("db", db);
        sc.setAttribute("blacklist", blacklist);
        sc.setAttribute("images", images);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {}

}
