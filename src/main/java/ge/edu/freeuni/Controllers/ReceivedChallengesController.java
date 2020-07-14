package ge.edu.freeuni.Controllers;

import ge.edu.freeuni.DAO.BlacklistDAO;
import ge.edu.freeuni.DAO.ChallengesDAO;
import ge.edu.freeuni.DAO.ReservedDAO;
import ge.edu.freeuni.DAO.TimeTableDAO;
import ge.edu.freeuni.Models.Cell;
import ge.edu.freeuni.Models.Challenge;
import ge.edu.freeuni.Models.Reservation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class ReceivedChallengesController {

    @GetMapping("/recChallenges")
    public String display(HttpServletRequest req) throws SQLException {
        ChallengesDAO challenges = (ChallengesDAO) req.getServletContext().getAttribute("challenges");
        DateFormat df = new SimpleDateFormat("HH");
        Date dateobj = new Date();
        int tm = Integer.parseInt(df.format(dateobj));
        challenges.deleteTimedOutChallenges(tm);
        return "received";
    }

    @PostMapping("/recChallenges")
    public ModelAndView respondChallenge(@RequestParam String hiddenID,
                                         @RequestParam String Button,
                                         HttpServletRequest req) throws SQLException {

        ChallengesDAO dao = (ChallengesDAO) req.getServletContext().getAttribute("challenges");
        TimeTableDAO table = (TimeTableDAO) req.getServletContext().getAttribute("table");
        BlacklistDAO blacklistDAO = (BlacklistDAO) req.getServletContext().getAttribute("blacklist");
        ReservedDAO reservedDAO = (ReservedDAO) req.getServletContext().getAttribute("reserved");
        Challenge challenge = dao.getChallenge(Integer.parseInt(hiddenID));
        ModelAndView mv = new ModelAndView("received");
        int time = challenge.getTime();
        int computer = challenge.getComputerID();
        List<Challenge> lst = dao.getAllForComputerTime(time, computer);
        Cell curCell = table.get(time, computer);
        if (challenge != null) {
            if(Button.equals("Reject")){
                if (lst.size() == 1) {
                    if (curCell.getColor().equals("orange")) {
                        curCell.setColor("green");
                        curCell.setText("Free");
                    }
                    table.update(curCell);
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
                        curCell.setText("Taken");
                        curCell.setColor("red");
                        table.update(curCell);
                        for (int i = 0; i < 10; i++) {
                            Cell cell = table.get(time, i);
                            if (cell.getColor().equals("orange") && dao.getAllForComputerTime(time, i).size() == 0) {
                                cell.setColor("green");
                                cell.setText("Free");
                                table.update(cell);
                            }
                        }
                        reservedDAO.addReservation(new Reservation(challenge.getToUser(), time, computer));
                        reservedDAO.addReservation(new Reservation(challenge.getFromUser(), time, computer));
                    }
                    else if (curCell.getColor().equals("red")) {
                        dao.removeChallengesByChallenge(challenge);
                        List<Reservation> reservations = reservedDAO.getAllByTime(challenge.getTime());
                        for (Reservation reservation: reservations) {
                            System.out.println(reservation.getUsername());
                        }
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
                    else if (curCell.getColor().equals("gray")) {
                        mv.addObject("error", "Too late!");
                    }
                }
            }
            dao.deleteChallenge(Integer.parseInt(hiddenID));
        }
        return mv;
    }
}
