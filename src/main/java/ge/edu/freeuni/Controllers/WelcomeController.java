package ge.edu.freeuni.Controllers;

import ge.edu.freeuni.Models.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class WelcomeController {
    @GetMapping("/home")
    public String display(HttpSession ses){
        User user = (User)ses.getAttribute("user");
        if (user == null || user.getUsername().equals("admin")) {
            return "fail";
        }
        return "home";
    }
}
