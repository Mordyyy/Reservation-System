package ge.edu.freeuni.Controllers;

import ge.edu.freeuni.DAO.UsersDAO;
import ge.edu.freeuni.Models.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionListener;
import java.io.IOException;

@Controller
public class HomepageController {

    @GetMapping("")
    public String login(HttpSession ses) {
        ses.setAttribute("user", null);
        return "login";
    }

    @PostMapping("")
    public ModelAndView Authenticate(@RequestParam String Username,
                               @RequestParam String Password,
                                @RequestParam String Button,
                                HttpServletRequest req,
                                HttpServletResponse resp,
                                HttpSession ses) throws IOException {
        if(Button.equals("Login")) {
            UsersDAO users = (UsersDAO) req.getServletContext().getAttribute("db");
            User userUsername = users.getUserByUsername(Username);
            User userEmail = users.getUserByeMail(Username);
            ModelAndView modelAndView = new ModelAndView("login");
            User user = null;
            if(userUsername != null){
                user = userUsername;
            }else if(userEmail != null){
                user = userEmail;
            }
            if (user == null) {
                modelAndView.addObject("Error", "Account Does not exist!");
                return modelAndView;
            }
            if (!user.getPassword().equals(Password)) {
                modelAndView.addObject("Error", "Username or Password incorrect!");
                return modelAndView;
            }
            //modelAndView.setViewName("home");
            ses.setAttribute("user", user);
            resp.sendRedirect("/home");
            return modelAndView;
        }else {
            resp.sendRedirect("/register");
            return new ModelAndView("register");
        }
    }
}
