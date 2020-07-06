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
                                HttpServletRequest req,
                                HttpServletResponse resp) throws IOException {
        UsersDAO users = (UsersDAO)req.getServletContext().getAttribute("db");
        User user = users.getUser(Username);
        ModelAndView modelAndView = new ModelAndView("");
        if(user == null){
            modelAndView.addObject("Error", "Account Does not exist!");
            return modelAndView;
        }
        if(user.getPassword().equals(Password)){
            modelAndView.addObject("error", "Username or Password incorrect!");
            return modelAndView;
        }
        resp.sendRedirect("/home");
        return modelAndView;
    }
}
