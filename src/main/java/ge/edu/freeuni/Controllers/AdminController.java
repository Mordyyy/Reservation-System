package ge.edu.freeuni.Controllers;

import ge.edu.freeuni.DAO.*;
import ge.edu.freeuni.Helpers.Email;
import ge.edu.freeuni.Helpers.TableGrey;
import ge.edu.freeuni.Models.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class AdminController {

    private TableGrey tableGrey = new TableGrey();

    private void setModelAttributes(HttpServletRequest req, HttpSession ses, ModelAndView mv) throws SQLException {
        BlacklistDAO dao = (BlacklistDAO) req.getServletContext().getAttribute("blacklist");
        List<String> blacklist = dao.getAll();
        User curUser = (User) ses.getAttribute("user");
        String imgfile = curUser.getAvatar();
        ImageDAO db = (ImageDAO) req.getServletContext().getAttribute("images");
        List<Image> images = db.getAll();
        mv.addObject("blacklist", blacklist);
        mv.addObject("imgfile", imgfile);
        mv.addObject("images", images);
    }

    @GetMapping("/admin")
    public ModelAndView render(HttpSession ses, HttpServletRequest req) throws SQLException {
        User user = (User) ses.getAttribute("user");
        colorCheck(req);
        tableGrey.tableGrey(req);
        if (user == null || !user.getUsername().equals("admin"))
            return new ModelAndView("fail");
        ModelAndView mv = new ModelAndView("admin");
        setModelAttributes(req, ses, mv);
        return mv;
    }

    @PostMapping("/admin")
    public ModelAndView adminActions(@RequestParam String emailstosend,
                                     @RequestParam String subject,
                                     @RequestParam String text,
                                     @RequestParam String Button,
                                     @RequestParam String toBlock,
                                     @RequestParam String time,
                                     @RequestParam String computer,
                                     @RequestParam String check,
                                     @RequestParam String timetocheck,
                                     @RequestParam String computertocheck,
                                     HttpServletRequest req, HttpSession ses) throws SQLException {
        ModelAndView mv = new ModelAndView("admin");
        Email email = (Email) req.getServletContext().getAttribute("email");
        UsersDAO db = (UsersDAO) req.getServletContext().getAttribute("db");
        BlacklistDAO blackDB = (BlacklistDAO) req.getServletContext().getAttribute("blacklist");
        tableGrey.tableGrey(req);
        ReservedDAO reservedDAO = (ReservedDAO) req.getServletContext().getAttribute("reserved");
        ChallengesDAO challengesDAO = (ChallengesDAO) req.getServletContext().getAttribute("challenges");
        TimeTableDAO timeTableDAO = (TimeTableDAO) req.getServletContext().getAttribute("table");
        LastResetDAO lastResetDAO = (LastResetDAO) req.getServletContext().getAttribute("lastReset");

        if (Button.equals("reset")) {
            resetting(reservedDAO, challengesDAO, timeTableDAO, lastResetDAO);
        } else if (Button.equals("check")) {
            if (check != null) {
                String usernm = check;
                int timeToCheck = Integer.parseInt(timetocheck);
                int computerToCheck = Integer.parseInt(computertocheck.substring(computer.length() - 1));
                if (reservedDAO.containsReservation(new Reservation(usernm, timeToCheck, computerToCheck))) {
                    mv.addObject("contains", true);
                } else {
                    mv.addObject("contains", false);
                }
            }
        } else if (Button.equals("sendall")) {
            List<User> users = db.getAll();
            for (User user : users) {
                try {
                    if (user.getUsername().equals("admin"))
                        continue;
                    email.sendCode(user.getMail(), subject, text);
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }
        } else if (Button.equals("send")) {
            sendMail(emailstosend, subject, text, email, db);
        } else if (Button.equals("block")) {
            if (!blackDB.getUser(toBlock) && !toBlock.equals("admin") && db.contains(toBlock)) {
                blackDB.addUser(toBlock);
            }
        } else if (Button.equals("unblock")) {
            if (blackDB.getUser(toBlock)) {
                blackDB.removeUser(toBlock);
            }
        } else if (Button.equals("reserve")) {
            reserveByAdmin(time, computer, req, mv);
        }
        setModelAttributes(req, ses, mv);
        colorCheck(req);
        return mv;
    }

    private void reserveByAdmin(@RequestParam String time, @RequestParam String computer, HttpServletRequest req, ModelAndView mv) throws SQLException {
        ChallengesDAO challengesDAO;
        TimeTableDAO tableDAO = (TimeTableDAO) req.getServletContext().getAttribute("table");
        int curTime = Integer.parseInt(time.substring(0, 2));
        int compIndx = Integer.parseInt(computer.substring(computer.length() - 1));
        int i = curTime - 9;
        int j = compIndx + 1;
        Cell curCell = tableDAO.get(curTime, compIndx);
        if (curCell.getColor().equals("green") || curCell.getColor().equals("orange")) {
            curCell.setColor("red");
            curCell.setText("Taken");
            tableDAO.update(curCell);
            challengesDAO = (ChallengesDAO) req.getServletContext().getAttribute("challenges");
            Challenge challenge = new Challenge(0, "", "", curTime, compIndx);
            challengesDAO.removeAllForComputerTime(challenge);
        } else {
            mv.addObject("error", "Already taken!");
        }
    }

    private void sendMail(@RequestParam String emailstosend, @RequestParam String subject, @RequestParam String text, Email email, UsersDAO db) throws SQLException {
        StringTokenizer tokenizer = new StringTokenizer(emailstosend, " ,");
        while (tokenizer.hasMoreElements()) {
            String username = tokenizer.nextToken();
            User user = db.getUserByUsername(username);
            Set<String> st = new HashSet<>();
            if (user != null && !user.getUsername().equals("admin")) {
                st.add(user.getMail());
            }
            for (String curMail : st) {
                try {
                    email.sendCode(curMail, subject, text);
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void resetting(ReservedDAO reservedDAO, ChallengesDAO challengesDAO, TimeTableDAO timeTableDAO, LastResetDAO lastResetDAO) throws SQLException {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat timeFormatter = new SimpleDateFormat("HH");
        int tm = Integer.parseInt(timeFormatter.format(cal.getTime()));
        String dateToday = formatter.format(cal.getTime());
        System.out.println(dateToday);
        if (tm < 21) {
            lastResetDAO.update(dateToday.substring(0, 11) + "21:00:00");
        } else {
            cal.add(Calendar.DATE, 1);
            String dateTomorrow = formatter.format(cal.getTime());
            lastResetDAO.update(dateTomorrow.substring(0, 11) + "21:00:00");
        }
        challengesDAO.removeAll();
        reservedDAO.removeAll();
        timeTableDAO.reset();
    }

    private void colorCheck(HttpServletRequest req) throws SQLException {
        DateFormat df = new SimpleDateFormat("HH");
        Date dateobj = new Date();
        int tm = Integer.parseInt(df.format(dateobj));
        TimeTableDAO tableDAO = (TimeTableDAO) req.getServletContext().getAttribute("table");
        ReservedDAO reserved = (ReservedDAO) req.getServletContext().getAttribute("reserved");
        if (tm <= 21 && tm >= 10) {
            withGrey(tableDAO, reserved, tm);
        } else {
            withGreen(tableDAO);
        }
    }

    private void withGrey(TimeTableDAO tableDAO, ReservedDAO reserved, int tm) throws SQLException {
        // System.out.println(tm);
        for (int i = 10; i <= tm; i++) {   // i -> time, j-> computer id
            for (int j = 0; j < 10; j++) {
                Cell curCell = tableDAO.get(i, j);
                curCell.setColor("grey");
                curCell.setText("Time out");
                tableDAO.update(curCell);
                reserved.removeTimedOut(tm);
            }
        }
    }

    private void withGreen(TimeTableDAO tableDAO) throws SQLException {
        for (int i = 10; i <= 21; i++) {  // i -> time, j-> computer id
            for (int j = 0; j < 10; j++) {
                Cell curCell = tableDAO.get(i, j);
                if (curCell.getColor().equals("grey")) {
                    curCell.setColor("green");
                    curCell.setText("Free");
                    tableDAO.update(curCell);
                }
            }
        }
    }

}
