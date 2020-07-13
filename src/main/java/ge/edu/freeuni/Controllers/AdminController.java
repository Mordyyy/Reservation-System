package ge.edu.freeuni.Controllers;

import ge.edu.freeuni.DAO.BlacklistDAO;
import ge.edu.freeuni.DAO.ChallengesDAO;
import ge.edu.freeuni.DAO.TimeTableDAO;
import ge.edu.freeuni.DAO.UsersDAO;
import ge.edu.freeuni.Models.Cell;
import ge.edu.freeuni.Models.Challenge;
import ge.edu.freeuni.Models.Email;
import ge.edu.freeuni.Models.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

@Controller
public class AdminController {

    @GetMapping("/admin")
    public String render(HttpSession ses) {
        User user = (User)ses.getAttribute("user");
        if (user == null || !user.getUsername().equals("admin"))
            return "fail";
        return "admin";
    }

    @PostMapping("/admin")
    public ModelAndView adminActions(@RequestParam String emailstosend,
                                 @RequestParam String subject,
                                 @RequestParam String text,
                                 @RequestParam String Button,
                                 @RequestParam String toBlock,
                                 @RequestParam String time,
                                 @RequestParam String computer,
                                 HttpServletRequest req) throws SQLException {
        ModelAndView mv = new ModelAndView("admin");
        Email email = (Email) req.getServletContext().getAttribute("email");
        UsersDAO db = (UsersDAO) req.getServletContext().getAttribute("db");
        BlacklistDAO blackDB = (BlacklistDAO)req.getServletContext().getAttribute("blacklist");
        if (Button.equals("sendall")) {
            List<User> users = db.getAll();
            for (User user: users) {
                try {
                    if (user.getUsername().equals("admin"))
                        continue;
                    email.sendCode(user.getMail(), subject, text);
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }
        }
        else if (Button.equals("send")) {
            StringTokenizer tokenizer = new StringTokenizer(emailstosend, " ,");
            while (tokenizer.hasMoreElements()) {
                String username = tokenizer.nextToken();
                User user = db.getUserByUsername(username);
                Set<String> st = new HashSet<>();
                if (user != null && !user.getUsername().equals("admin")) {
                    st.add(user.getMail());
                }
                for (String curMail: st) {
                    try {
                        email.sendCode(curMail, subject, text);
                    } catch (MessagingException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else if (Button.equals("block")) {
            if (!blackDB.getUser(toBlock) && !toBlock.equals("admin")) {
                blackDB.addUser(toBlock);
            }
        } else if (Button.equals("unblock")) {
            if (blackDB.getUser(toBlock)) {
                blackDB.removeUser(toBlock);
            }
        } else if (Button.equals("reserve")) {
            TimeTableDAO tableDAO = (TimeTableDAO) req.getServletContext().getAttribute("table");
            int curTime = Integer.parseInt(time.substring(0, 2));
            int compIndx = Integer.parseInt(computer.substring(computer.length() - 1));
            int i = curTime - 9;
            int j = compIndx + 1;
            Cell curCell = tableDAO.get(curTime, compIndx);
            if (curCell.getColor().equals("red")) {
                curCell.setColor("red");
                curCell.setText("Taken");
                ChallengesDAO challengesDAO = (ChallengesDAO) req.getServletContext().getAttribute("challenges");
                Challenge challenge = new Challenge(0, "", "", curTime, compIndx);
                challengesDAO.removeAllForComputerTime(challenge);
            }
            else {
                mv.addObject("error", "Already taken!");
            }
        }
        return mv;
    }

}
