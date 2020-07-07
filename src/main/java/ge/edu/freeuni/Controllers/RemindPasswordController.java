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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class RemindPasswordController {
    @GetMapping("/reminder")
    public String display(){
        return "reminder";
    }

    @PostMapping("/reminder")
    public ModelAndView register(@RequestParam String email,
                                 HttpServletRequest req,
                                 HttpServletResponse resp) throws IOException, MessagingException {
        return null;
    }
}
