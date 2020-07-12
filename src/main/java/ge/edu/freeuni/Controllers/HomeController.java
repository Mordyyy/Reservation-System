package ge.edu.freeuni.Controllers;

import ge.edu.freeuni.DAO.ChallengesDAO;
import ge.edu.freeuni.DAO.UsersDAO;
import ge.edu.freeuni.Models.Cell;
import ge.edu.freeuni.Models.Challenge;
import ge.edu.freeuni.Models.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
                                HttpServletRequest req,
                                HttpSession ses) {
        ModelAndView mv = new ModelAndView("home");
        ChallengesDAO challengesDAO = (ChallengesDAO) req.getServletContext().getAttribute("challenges");
        if (Button.equals("reserve")) {
            Cell[][] table = (Cell[][]) req.getSession().getAttribute("table");
            int curTime = Integer.parseInt(time.substring(0, 2));
            int compIndx = Integer.parseInt(computer.substring(computer.length() - 1));
            int i = curTime - 9;
            int j = compIndx + 1;
            if (!table[i][j].getColor().equals("red")) {
                table[i][j].setText("Taken");
                table[i][j].setColor("red");
                Challenge challenge = new Challenge(0, "", "", curTime, compIndx);
                challengesDAO.removeAllForComputerTime(challenge);
            }
            else {
                mv.addObject("error", "Already taken!");
            }
        }
        else if (Button.equals("Change avatar")) {

        }
        return mv;
    }
}
