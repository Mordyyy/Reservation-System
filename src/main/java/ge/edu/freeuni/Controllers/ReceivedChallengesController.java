package ge.edu.freeuni.Controllers;

import ge.edu.freeuni.DAO.*;
import ge.edu.freeuni.Models.Cell;
import ge.edu.freeuni.Models.Challenge;
import ge.edu.freeuni.Models.Reservation;
import ge.edu.freeuni.Models.User;
import org.springframework.stereotype.Controller;
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
import java.util.Date;
import java.util.List;

@Controller
public class ReceivedChallengesController {

    @GetMapping("/recChallenges")
    public ModelAndView display(HttpServletRequest req, HttpSession ses) throws SQLException {
        ChallengesDAO challenges = (ChallengesDAO) req.getServletContext().getAttribute("challenges");
        DateFormat df = new SimpleDateFormat("HH");
        Date dateobj = new Date();
        int tm = Integer.parseInt(df.format(dateobj));
        challenges.deleteTimedOutChallenges(tm);
        ModelAndView mv = new ModelAndView("received");
        setModelAttributes(req, ses, mv);
        return mv;
    }

    @PostMapping("/recChallenges")
    public ModelAndView respondChallenge(@RequestParam String hiddenID,
                                         @RequestParam String Button,
                                         HttpServletRequest req, HttpSession ses) throws SQLException {
        ModelAndView mv = new ModelAndView("received");
        ChallengesDAO dao = (ChallengesDAO)req.getServletContext().getAttribute("challenges");
        TimeTableDAO table = (TimeTableDAO) req.getServletContext().getAttribute("table");
        BlacklistDAO blacklistDAO = (BlacklistDAO) req.getServletContext().getAttribute("blacklist");
        ReservedDAO reservedDAO = (ReservedDAO) req.getServletContext().getAttribute("reserved");
        Challenge challenge = dao.getChallenge(Integer.parseInt(hiddenID));
        OrdersDAO ordersDAO = (OrdersDAO) req.getServletContext().getAttribute("orders");
        int time = challenge.getTime();
        int computer = challenge.getComputerID();
        List<Challenge> lst = dao.getAllForComputerTime(time, computer);
        Cell curCell = table.get(time, computer);
        if (challenge != null) {
            if(Button.equals("Reject")){
                if (lst.size() == 1) {
                    if (curCell.getColor().equals("orange")) {
                        updateTable(curCell, "green", "Free", table);
                    }
                }
            }
            else if (Button.equals("Accept")) {
                if (blacklistDAO.getUser(challenge.getFromUser())) {
                    mv.addObject("error", "User who sent you a challenge is in a Blacklist!");
                }
                else if (blacklistDAO.getUser(challenge.getToUser())) {
                    mv.addObject("error", "You are in a Blacklist!");
                }
                else {
                    if (curCell.getColor().equals("orange")) {
                        dao.removeChallengesByChallenge(challenge);
                        updateTable(curCell, "red", "Taken", table);
                        for (int i = 0; i < 10; i++) {
                            Cell cell = table.get(time, i);
                            if (cell.getColor().equals("orange") && dao.getAllForComputerTime(time, i).size() == 0) {
                                updateTable(cell, "green", "Free", table);
                            }
                        }
                        reservedDAO.addReservation(new Reservation(challenge.getToUser(), time, computer));
                        reservedDAO.addReservation(new Reservation(challenge.getFromUser(), time, computer));
                        User tmpuser = new User(challenge.getFromUser());
                        int orders = ordersDAO.getUserOrders(tmpuser.getUsername());
                        orders++;
                        tmpuser.setOrders(orders);
                        ordersDAO.updateUserOrders(tmpuser);
                        if(orders % 5 == 0) {
                            int bonus = ordersDAO.getUserBonus(tmpuser);
                            bonus++;
                            tmpuser.setBonus(bonus);
                            ordersDAO.updateUserBonus(tmpuser);
                        }
                        System.out.println(tmpuser.getUsername() + " " + ordersDAO.getUserOrders(tmpuser.getUsername()) + " " + tmpuser.getBonus());
                    }
                    else if (curCell.getColor().equals("red")) {
                        dao.removeChallengesByChallenge(challenge);
                        List<Reservation> reservations = reservedDAO.getAllByTime(challenge.getTime());
                        if (reservations.size() > 1) {
                            mv.addObject("error", "Already taken!");
                        }
                        else {
                            Reservation curReservation = reservations.get(0);
                            if (curReservation.getUsername().equals(challenge.getFromUser())) {
                                List<Reservation> curReservations = reservedDAO.getAllByUser(challenge.getToUser());
                                for (Reservation reservation: curReservations) {
                                    if (reservation.getTime() == challenge.getTime()) {
                                        mv.addObject("error", "You can't accept this challenge!");
                                        setModelAttributes(req, ses, mv);
                                        return mv;
                                    }
                                }
                                reservedDAO.addReservation(new Reservation(challenge.getToUser(), time, computer));
                            }
                            else {
                                mv.addObject("error", "Already taken!");
                            }
                        }
                    }
                    else if (curCell.getColor().equals("grey")) {
                        mv.addObject("error", "Too late!");
                    }
                }
            }
            dao.deleteChallenge(Integer.parseInt(hiddenID));
        }
        setModelAttributes(req, ses, mv);
        return mv;
    }

    private ModelAndView setModelAttributes(HttpServletRequest req, HttpSession ses,
                                            ModelAndView mv) throws SQLException {
        User user = (User)ses.getAttribute("user");
        String username = user.getUsername();
        ChallengesDAO dao = (ChallengesDAO)req.getServletContext().getAttribute("challenges");
        List<Challenge> allReceived = dao.getAllReceived(username);
        mv.addObject("receivedChallenges", allReceived);
        return mv;
    }

    private void updateTable(Cell cell, String color, String text, TimeTableDAO table) throws SQLException {
        cell.setColor(color);
        cell.setText(text);
        table.update(cell);
    }

}
