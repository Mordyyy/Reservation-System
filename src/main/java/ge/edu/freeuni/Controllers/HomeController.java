package ge.edu.freeuni.Controllers;

import ge.edu.freeuni.DAO.BlacklistDAO;
import ge.edu.freeuni.DAO.ChallengesDAO;
import ge.edu.freeuni.DAO.UsersDAO;
import ge.edu.freeuni.Models.Cell;
import ge.edu.freeuni.Models.Challenge;
import ge.edu.freeuni.Models.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;

@Controller
public class HomeController {
    @GetMapping("/home")
    public String display(HttpSession ses){
        User user = (User)ses.getAttribute("user");
        if (user == null || user.getUsername().equals("admin")) {
            return "fail";
        }
        return "home";
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
        User curUser = (User)ses.getAttribute("user");
        if (Button.equals("reserve")) {
            Cell[][] table = (Cell[][]) req.getSession().getAttribute("table");
            int curTime = Integer.parseInt(time.substring(0, 2));
            int compIndx = Integer.parseInt(computer.substring(computer.length() - 1));
            int i = curTime - 9;
            int j = compIndx + 1;
            if (blacklistDAO.getUser(curUser.getUsername())) {
                mv.addObject("error", "You are in a blacklist, you can't reserve!");
                return mv;
            }
            if (!table[i][j].getColor().equals("red")) {
                Challenge challenge = new Challenge(0, "", "", curTime, compIndx);
                if (WannaChallenge != null) {
                    if (blacklistDAO.getUser(user)) {
                        mv.addObject("error", "Your opponent is in a blacklist, you can't reserve!");
                        return mv;
                    }
                    challengesDAO.removeAllForComputerTime(challenge);
                    Challenge challenge1 = new Challenge(curUser.getUsername(), user, curTime, compIndx);
                    challengesDAO.addChallenge(challenge1);
                }
                if (PlayAlone != null) {
                    table[i][j].setColor("red");
                    table[i][j].setText("Taken");
                    if (WannaChallenge == null)
                        challengesDAO.removeAllForComputerTime(challenge);
                }
                else {
                    if (WannaChallenge == null) {
                        mv.addObject("error", "None of the checkboxes checked!");
                        return mv;
                    }
                    table[i][j].setColor("yellow");
                    table[i][j].setText("Waiting");
                }
            }
            else {
                mv.addObject("error", "Already taken!");
            }
        }
        else if (Button.equals("Change avatar")) {
            curUser.setAvatar(avatar);
            usersDAO.changeAvatar(curUser);
        }
        return mv;
    }
}
