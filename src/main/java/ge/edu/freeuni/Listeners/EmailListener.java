package ge.edu.freeuni.Listeners;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class EmailListener implements ServletContextListener{
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();

    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }

}
