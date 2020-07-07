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
public class ResetPasswordController {
    @GetMapping("/reset")
    public String display(){
        return "reset";
    }

    @PostMapping("/reset")
    public ModelAndView remainder(@RequestParam String oldPassword,
                                  @RequestParam String password1,
                                  @RequestParam String password2,
                                  HttpServletRequest req,
                                  HttpServletResponse resp) throws IOException, MessagingException {
        return null;
    }
}
