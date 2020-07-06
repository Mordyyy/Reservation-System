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

@Controller
public class HomepageController {

    @GetMapping("")
    public String login(HttpSession ses) {
        ses.setAttribute("user", null);
        return "login";
    }

    @PostMapping("")
    public String Authenticate(@RequestParam String Username,
                               @RequestParam String Password,
                                HttpServletRequest req,
                                HttpServletResponse resp){
        UsersDAO users = (UsersDAO)req.getServletContext().getAttribute("db");
        User user = users.getUser(Username);
        ModelAndView modelAndView = new ModelAndView("");
        if(user.getPassword().equals(Password)){
            modelAndView.addObject("error", "Username or Password incorrect.");
        }else{
            return "login";
        }
    }
}
