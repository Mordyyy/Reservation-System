package ge.edu.freeuni.Controllers;

import ge.edu.freeuni.DAO.BlacklistDAO;
import ge.edu.freeuni.DAO.UsersDAO;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

@Controller
public class AdminController {

    @GetMapping("/admin")
    public String render(HttpSession ses) {
        if (!ses.getAttribute("user").equals("admin")) {
            return null;
        }
        return "admin";
    }

    @PostMapping("/admin")
    public ModelAndView sendMail(@RequestParam String emailstosend,
                                 @RequestParam String subject,
                                 @RequestParam String text,
                                 @RequestParam String Button,
                                 @RequestParam String toBlock,
                                 HttpServletRequest req) {
        ModelAndView mv = new ModelAndView("admin");
        Email email = (Email) req.getServletContext().getAttribute("email");
        UsersDAO db = (UsersDAO) req.getServletContext().getAttribute("db");
        BlacklistDAO blackDB = (BlacklistDAO)req.getServletContext().getAttribute("blacklist");
        if (Button.equals("sendall")) {
            List<User> users = db.getAll();
            for (User user: users) {
                try {
                    email.sendCode(user.getMail(), subject, text);
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }
        }
        else if (Button.equals("send")) {
            StringTokenizer tokenizer = new StringTokenizer(emailstosend, " ,");
            while (tokenizer.hasMoreElements()) {
                String mail = tokenizer.nextToken();
                User user = db.getUserByeMail(mail);
                Set<String> st = new HashSet<>();
                if (user != null) {
                    st.add(mail);
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
        }
        return mv;
    }

}
