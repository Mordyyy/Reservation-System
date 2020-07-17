package ge.edu.freeuni.Controllers;

import ge.edu.freeuni.DAO.UsersDAO;
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
import java.io.IOException;
import java.sql.SQLException;

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
                                 HttpServletResponse resp) throws IOException, MessagingException, ServletException, SQLException {
        UsersDAO users = (UsersDAO) req.getServletContext().getAttribute("db");
        User user = users.getUserByUsername(Username);
        ModelAndView modelAndView = new ModelAndView("register");
        if (Username.length() < 4) {
            return errorMV(modelAndView, "Username Should Contain At Least 4 Symbols");
        }
        if (Password1.length() < 6) {
            return errorMV(modelAndView, "Password Should Contain At Least 6 Symbols");
        }
        if (eMail.length() == 0) {
            return errorMV(modelAndView, "Enter Email!");
        }
        if (user != null) {
            return errorMV(modelAndView, "Account with username: " + Username + " Already exists!");
        }
        if (!Password1.equals(Password2)) {
            return errorMV(modelAndView, "Passwords do not match!");
        }
        if (users.getUserByeMail(eMail) != null) {
            return errorMV(modelAndView, "Account with email: " + eMail + " Already exists!");
        }
        RequestDispatcher dispatcher = req.getRequestDispatcher("/sendcode");
        dispatcher.forward(req, resp);

        return modelAndView;
    }

    private ModelAndView errorMV(ModelAndView mv, String errorMessage) {
        mv.addObject("error", errorMessage);
        return mv;
    }

}
