package ge.edu.freeuni.Controllers;

import ge.edu.freeuni.Models.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;

@Controller
public class InformationController {
    @GetMapping("/info")
    public ModelAndView render(HttpSession ses, HttpServletRequest req) throws SQLException {
        ModelAndView mv = new ModelAndView("help");
        return mv;
    }
}
