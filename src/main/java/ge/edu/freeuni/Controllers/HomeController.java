package ge.edu.freeuni.Controllers;

import ge.edu.freeuni.DAO.*;
import ge.edu.freeuni.Helpers.TableGrey;
import ge.edu.freeuni.Models.*;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Controller
public class HomeController {

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

    @GetMapping("/home")
    public ModelAndView display(HttpSession ses, HttpServletRequest req) throws SQLException {
        User user = (User) ses.getAttribute("user");
        colorCheck(req);
        tableGrey.tableGrey(req);
        ReservedDAO reservedDAO = (ReservedDAO) req.getServletContext().getAttribute("reserved");
        OrdersDAO ordersDAO = (OrdersDAO) req.getServletContext().getAttribute("orders");
        if (user == null || user.getUsername().equals("admin")) {
            return new ModelAndView("fail");
        }
        user.setBonus(ordersDAO.getUserBonus(user));
        user.setOrders(ordersDAO.getUserOrders(user));
        ModelAndView mv = new ModelAndView("home");
        ArrayList<Reservation> arr = (ArrayList<Reservation>) reservedDAO.getAllByUserSorted(user.getUsername());
        if (arr.size() > 0) {
            mv.addObject("label", "Next Reservation on " + arr.get(0).getTime() + ":00 on " + arr.get(0).getCompID() + "th Computer!");
        } else {
            mv.addObject("label", "No Reservations");
        }
        setModelAttributes(req, ses, mv);
        return mv;
    }

    @PostMapping("/home")
    public ModelAndView actions(@RequestParam String Button,
                                @RequestParam String time,
                                @RequestParam String computer,
                                @RequestParam String avatar,
                                @RequestParam(value = "WannaChallenge", required = false) String WannaChallenge,
                                @RequestParam(value = "PlayAlone", required = false) String PlayAlone,
                                @RequestParam String user,
                                HttpServletRequest req,
                                HttpSession ses) throws SQLException {
        ModelAndView mv = new ModelAndView("home");
        ChallengesDAO challengesDAO = (ChallengesDAO) req.getServletContext().getAttribute("challenges");
        BlacklistDAO blacklistDAO = (BlacklistDAO) req.getServletContext().getAttribute("blacklist");
        UsersDAO usersDAO = (UsersDAO) req.getServletContext().getAttribute("db");
        tableGrey.tableGrey(req);
        ReservedDAO reservedDAO = (ReservedDAO) req.getServletContext().getAttribute("reserved");
        User curUser = (User) ses.getAttribute("user");
        OrdersDAO ordersDAO = (OrdersDAO) req.getServletContext().getAttribute("orders");
        curUser.setBonus(ordersDAO.getUserBonus(curUser));
        curUser.setOrders(ordersDAO.getUserOrders(curUser));
        colorCheck(req);

        nextReservationDisp(ses, mv, reservedDAO);

        if (Button.equals("reserve")) {
            TimeTableDAO tableDAO = (TimeTableDAO) req.getServletContext().getAttribute("table");
            int curTime = Integer.parseInt(time.substring(0, 2));
            int compIndx = Integer.parseInt(computer.substring(computer.length() - 1));
            Cell curCell = tableDAO.get(curTime, compIndx);

            if (notReservationTimeCheck()) {
                return errorMV(mv, "Can Not Reserve Until 12 o'clock!", req, ses);
            }

            if (blacklistDAO.getUser(curUser.getUsername())) {
                return errorMV(mv, "You are in a blacklist, you can't reserve!", req, ses);
            }
            if (!curCell.getColor().equals("red") && !curCell.getColor().equals("grey")) {
                Challenge challenge = new Challenge(0, "", "", curTime, compIndx);
                if (WannaChallenge != null) {
                    if (user.equals("")) {
                        return errorMV(mv, "Please, enter opponent's name!", req, ses);
                    } else if (user.equals(curUser.getUsername())) {
                        return errorMV(mv, "You can't challenge yourself!", req, ses);
                    } else if (user.equals("admin")) {
                        return errorMV(mv, "You can't challenge admin!", req, ses);
                    } else if (blacklistDAO.getUser(user)) {
                        return errorMV(mv, "Your opponent is in a blacklist, you can't reserve!", req, ses);
                    } else if (usersDAO.contains(user)) {
                        Challenge challenge1 = new Challenge(curUser.getUsername(), user, curTime, compIndx);
                        challengesDAO.addChallenge(challenge1);
                    } else {
                        return errorMV(mv, "User you want to challeng doesn't exist!", req, ses);
                    }
                }
                if (PlayAlone != null) {
                    if (errorTimeChecker(reservedDAO, curUser, curTime)) {
                        return errorMV(mv, "You can't play by this time!", req, ses);
                    }
                    curCell.setColor("red");
                    curCell.setText("Taken");
                    tableDAO.update(curCell);
                    Reservation reservation = new Reservation(curUser.getUsername(), curTime, compIndx);
                    reservedDAO.addReservation(reservation);
                    int orders = ordersDAO.getUserOrders(curUser); orders++;
                    curUser.setOrders(orders);
                    ordersDAO.updateUserOrders(curUser);
                    if(orders % 5 == 0) {
                        int bonus = ordersDAO.getUserBonus(curUser);  bonus++;
                        curUser.setBonus(bonus);
                        ordersDAO.updateUserBonus(curUser);
                    }
                    if (WannaChallenge == null)
                        challengesDAO.removeAllForComputerTime(challenge);
                } else {
                    if (WannaChallenge == null) {
                        return errorMV(mv, "None of the checkboxes checked!", req, ses);
                    }
                    if (errorTimeChecker(reservedDAO, curUser, curTime)) {
                        return errorMV(mv, "you can't play by this time!", req, ses);
                    }
                    curCell.setColor("orange");
                    curCell.setText("Waiting");
                    tableDAO.update(curCell);
                }
            } else {
                if (curCell.getColor().equals("red"))
                    mv.addObject("error", "Already taken!");
                else
                    mv.addObject("error", "Can not reserve");
            }
        } else if (Button.equals("Change avatar")) {
            curUser.setAvatar(avatar);
            usersDAO.changeAvatar(curUser);
        }
        setModelAttributes(req, ses, mv);
        nextReservationDisp(ses, mv, reservedDAO);
        return mv;
    }

    private boolean notReservationTimeCheck() throws SQLException {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("HH");
        int tm = Integer.parseInt(formatter.format(cal.getTime()));
//        if (tm >= 21 && tm <= 24) {
//            return true;
//        }
        return false;
    }

    private boolean errorTimeChecker(ReservedDAO reservedDAO, User curUser, int curTime) throws SQLException {
        List<Reservation> userReservations = reservedDAO.getAllByUser(curUser.getUsername());
        for (Reservation reservation : userReservations) {
            if (reservation.getTime() == curTime) {
                return true;
            }
        }
        return false;
    }

    private void nextReservationDisp(HttpSession ses, ModelAndView mv, ReservedDAO reservedDAO) throws SQLException {
        User usser = (User) ses.getAttribute("user");
        ArrayList<Reservation> arr = (ArrayList<Reservation>) reservedDAO.getAllByUserSorted(usser.getUsername());
        if (arr.size() > 0) {
            mv.addObject("label", "Next Reservation on " + arr.get(0).getTime() + ":00 on " + arr.get(0).getCompID() + "th Computer!");
        } else {
            mv.addObject("label", "No Reservations");
        }
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

    private ModelAndView errorMV(ModelAndView mv, String errorMessage, HttpServletRequest req, HttpSession ses) throws SQLException {
        mv.addObject("error", errorMessage);
        setModelAttributes(req, ses, mv);
        return mv;
    }
}
