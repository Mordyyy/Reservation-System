package ge.edu.freeuni.Controllers;

import ge.edu.freeuni.DAO.*;
import ge.edu.freeuni.Models.Cell;
import ge.edu.freeuni.Models.Challenge;
import ge.edu.freeuni.Models.Reservation;
import ge.edu.freeuni.Models.User;
import org.springframework.http.HttpRequest;
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
public class HomeController {
    @GetMapping("/home")
    public ModelAndView display(HttpSession ses, HttpServletRequest req) throws SQLException {
        ModelAndView modelAndView = new ModelAndView("home");
        User user = (User)ses.getAttribute("user");
        colorCheck(req);
        if (user == null || user.getUsername().equals("admin")) {
            modelAndView.setViewName("fail");
            return modelAndView;
        }
        ReservedDAO reservedDAO = (ReservedDAO) req.getServletContext().getAttribute("reserved");
        ArrayList<Reservation> arr = (ArrayList<Reservation>) reservedDAO.getAllByUserSorted(user.getUsername());
        if(arr.size() > 0) {
            modelAndView.addObject("label", "Next Reservation on " + arr.get(0).getTime() + ":00");
        }else{
            modelAndView.addObject("label", "No Reservations");
        }
        return modelAndView;
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
        UsersDAO usersDAO = (UsersDAO)  req.getServletContext().getAttribute("db");
        ReservedDAO reservedDAO = (ReservedDAO) req.getServletContext().getAttribute("reserved");
        User curUser = (User)ses.getAttribute("user");
        colorCheck(req);
        User usser = (User) ses.getAttribute("user");
        ArrayList<Reservation> arr = (ArrayList<Reservation>) reservedDAO.getAllByUserSorted(usser.getUsername());
        if(arr.size() > 0) {
            mv.addObject("label", "Next Reservation on " + arr.get(0).getTime() + ":00");
        }else{
            mv.addObject("label", "No Reservations");
        }
        if (Button.equals("reserve")) {
            TimeTableDAO tableDAO = (TimeTableDAO) req.getServletContext().getAttribute("table");
            int curTime = Integer.parseInt(time.substring(0, 2));
            int compIndx = Integer.parseInt(computer.substring(computer.length() - 1));
            Cell curCell = tableDAO.get(curTime, compIndx);
            if (blacklistDAO.getUser(curUser.getUsername())) {
                mv.addObject("error", "You are in a blacklist, you can't reserve!");
                return mv;
            }
            if (!curCell.getColor().equals("red") && !curCell.getColor().equals("grey")) {
                Challenge challenge = new Challenge(0, "", "", curTime, compIndx);
                if (WannaChallenge != null) {
                    if(user.equals("")){
                        mv.addObject("error", "Please, enter opponent's name!");
                        return mv;
                    } else if (user.equals(curUser.getUsername())) {
                        mv.addObject("error", "You can't challenge yourself!");
                        return mv;
                    } else if (user.equals("admin")) {
                        mv.addObject("error", "You can't challenge admin!");
                        return mv;
                    } else if (blacklistDAO.getUser(user)) {
                        mv.addObject("error", "Your opponent is in a blacklist, you can't reserve!");
                        return mv;
                    } else if (usersDAO.contains(user)) {
                        Challenge challenge1 = new Challenge(curUser.getUsername(), user, curTime, compIndx);
                        challengesDAO.addChallenge(challenge1);
                    }
                    else {
                        mv.addObject("error", "User you want to challange doesn't exist!");
                        return mv;
                    }
                }
                if (PlayAlone != null) {
                    List<Reservation> userReservations = reservedDAO.getAllByUser(curUser.getUsername());
                    for (Reservation reservation: userReservations) {
                        if (reservation.getTime() == curTime) {
                            mv.addObject("error", "You cant play by this time!");
                            return mv;
                        }
                    }
                    curCell.setColor("red");
                    curCell.setText("Taken");
                    tableDAO.update(curCell);
                    Reservation reservation = new Reservation(curUser.getUsername(), curTime, compIndx);
                    reservedDAO.addReservation(reservation);
                    if (WannaChallenge == null)
                        challengesDAO.removeAllForComputerTime(challenge);
                }
                else {
                    if (WannaChallenge == null) {
                        mv.addObject("error", "None of the checkboxes checked!");
                        return mv;
                    }
                    curCell.setColor("orange");
                    curCell.setText("Waiting");
                    tableDAO.update(curCell);
                }
            }
            else {
                if(curCell.getColor().equals("red"))
                    mv.addObject("error", "Already taken!");
                else
                    mv.addObject("error", "Can not reserve");
            }
        }
        else if (Button.equals("Change avatar")) {
            curUser.setAvatar(avatar);
            usersDAO.changeAvatar(curUser);
        }

        arr = (ArrayList<Reservation>) reservedDAO.getAllByUserSorted(usser.getUsername());
        if(arr.size() > 0) {
            mv.addObject("label", "Next Reservation on " + arr.get(0).getTime() + ":00");
        }else{
            mv.addObject("label", "No Reservations");
        }
        return mv;
    }

    private void colorCheck(HttpServletRequest req) throws SQLException {
        DateFormat df = new SimpleDateFormat("HH");
        Date dateobj = new Date();
        int tm = Integer.parseInt(df.format(dateobj));
        TimeTableDAO tableDAO = (TimeTableDAO) req.getServletContext().getAttribute("table");
        if (tm <= 21 && tm >= 10) {
            withGrey(tableDAO,tm);
        }else{
            withGreen(tableDAO);
        }
    }

    private void withGrey(TimeTableDAO tableDAO, int tm) throws SQLException {
        for (int i = 10; i <= tm; i++) {   // i -> time, j-> computer id
            for(int j = 0; j < 10; j++){
                Cell curCell = tableDAO.get(i, j);
                curCell.setColor("grey");
                curCell.setText("Time out");
                tableDAO.update(curCell);
            }
        }
    }

    private void withGreen(TimeTableDAO tableDAO) throws SQLException {
        for(int i = 10; i <= 21; i++){  // i -> time, j-> computer id
            for(int j = 0; j < 10; j++){
                Cell curCell = tableDAO.get(i, j);
                if(curCell.getColor().equals("grey")){
                    curCell.setColor("green");
                    curCell.setText("Free");
                }
            }
        }
    }
}
