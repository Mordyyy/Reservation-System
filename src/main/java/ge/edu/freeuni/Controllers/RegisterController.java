package ge.edu.freeuni.Controllers;

import ge.edu.freeuni.DAO.UsersDAO;
import ge.edu.freeuni.Models.Email;
import ge.edu.freeuni.Models.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.MessagingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.POST;
import java.io.IOException;

@Controller
public class RegisterController {
    @GetMapping("/register")
    public String display() {
        return "register";
    }

    @PostMapping("/register")
    public ModelAndView register(@RequestParam String Username,
                                 @RequestParam String Password1,
                                 @RequestParam String Password2,
                                 @RequestParam String eMail,
                                 @RequestParam String Button,
                                 HttpServletRequest req,
                                 HttpServletResponse resp) throws IOException, MessagingException, ServletException {
        boolean registerClickedSuccessfully = false;
        UsersDAO users = (UsersDAO) req.getServletContext().getAttribute("db");
        User user = users.getUserByUsername(Username);
        ModelAndView modelAndView = new ModelAndView("register");
        if (Username.length() < 4) {
            modelAndView.addObject("error", "Username Should Contain At Least 4 Symbols");
            return modelAndView;
        }
        if (Password1.length() < 6) {
            modelAndView.addObject("error", "Password Should Contain At Least 6 Symbols");
            return modelAndView;
        }
        if (eMail.length() == 0) {
            modelAndView.addObject("error", "Enter Email!");
            return modelAndView;
        }
        if (user != null) {
            modelAndView.addObject("error", "Account with username: " + Username + " Already exists!");
            return modelAndView;
        }
        if (!Password1.equals(Password2)) {
            modelAndView.addObject("error", "Passwords do not match!");
            return modelAndView;
        }
        if (users.getUserByeMail(eMail) != null) {
            modelAndView.addObject("error", "Account with email: " + eMail + " Already exists!");
            return modelAndView;
        }
        RequestDispatcher dispatcher = req.getRequestDispatcher("/sendcode");
        dispatcher.forward(req, resp);

        return modelAndView;
    }
}
