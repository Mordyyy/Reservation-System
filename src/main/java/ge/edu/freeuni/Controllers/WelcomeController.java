package ge.edu.freeuni.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class WelcomeController {
    @GetMapping("/home")
    public String display(){
        return "home";
    }
}
