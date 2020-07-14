package ge.edu.freeuni.Controllers;

import ge.edu.freeuni.DAO.ChallengesDAO;
import ge.edu.freeuni.DAO.UsersDAO;
import ge.edu.freeuni.Hash.GenerateHash;
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
public class LoginController {

    @GetMapping("")
    public String login(HttpSession ses, HttpServletRequest req) {
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
        ses.setAttribute("user", null);
        GenerateHash hasher = new GenerateHash();
        if(Button.equals("Login")) {
            UsersDAO users = (UsersDAO) req.getServletContext().getAttribute("db");
            User userUsername = users.getUserByUsername(Username);
            User userEmail = users.getUserByeMail(Username);
            ModelAndView modelAndView;
            String url = "/admin";
            if (Username.equals("admin"))
                modelAndView = new ModelAndView("admin");
            else{
                url = "/home";
                modelAndView = new ModelAndView("home");
            }
            User user = null;
            if(userUsername != null){
                user = userUsername;
            }else if(userEmail != null){
                user = userEmail;
            }
            if (user == null) {
                modelAndView.addObject("Error", "Account Does not exist!");
                modelAndView.setViewName("login");
                return modelAndView;
            }
            if (!user.getPassword().equals(hasher.generateHash(Password))) {
                modelAndView.addObject("Error", "Username or Password incorrect!");
                modelAndView.setViewName("login");
                return modelAndView;
            }
            //modelAndView.setViewName("home");
            ses.setAttribute("user", user);
            resp.sendRedirect(url);
            return modelAndView;
        }else {
            resp.sendRedirect("/register");
            return new ModelAndView("register");
        }
    }
}
