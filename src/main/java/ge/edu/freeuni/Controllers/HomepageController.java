package ge.edu.freeuni.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class HomepageController {

    @GetMapping("")
    public String login(HttpSession ses) {
        ses.setAttribute("user", null);
        return "login";
    }
}
