package ge.edu.freeuni.Listeners;

import ge.edu.freeuni.Models.Cell;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.awt.*;

public class SessionListener implements HttpSessionListener {
    @Override
    public void sessionCreated(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        Cell[][] table = new Cell[13][11];
        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table[i].length; j++) {
                table[i][j] = new Cell("Free", "gray");
            }
        }
        session.setAttribute("table", table);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {}
}
