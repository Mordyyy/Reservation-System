package ge.edu.freeuni.Listeners;

import ge.edu.freeuni.DAO.UsersDAO;
import ge.edu.freeuni.Models.Email;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ContextListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent event) {
        ServletContext sc = event.getServletContext();
        String url = sc.getInitParameter("url");
        String user_name = sc.getInitParameter("user_name");
        String password = sc.getInitParameter("password");
        String database = sc.getInitParameter("database");
        UsersDAO db = new UsersDAO(url + database, user_name, password);
        Email mail = new Email();
        sc.setAttribute("email", mail);
        sc.setAttribute("db", db);

    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {}

}
