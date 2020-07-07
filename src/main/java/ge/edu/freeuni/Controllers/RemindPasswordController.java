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
    public ModelAndView remainder(@RequestParam String email,
                                 HttpServletRequest req,
                                 HttpServletResponse resp) throws IOException, MessagingException {
        ModelAndView modelAndView = new ModelAndView("login");
        UsersDAO usersDAO = (UsersDAO) req.getServletContext().getAttribute("db");
        Email mail = (Email) req.getServletContext().getAttribute("email");
        if(usersDAO.getUserByeMail(email) != null){
            User user = usersDAO.getUserByeMail(email);
            String passwordMessagePref = "Your password is: " + user.getPassword() + "\n";
            String passwordMessageSuf = "Please log in if you want to change password";
            String passwordMessage = passwordMessagePref + passwordMessageSuf;
            mail.sendCode(email,"Password reminder",passwordMessage);
            return modelAndView;
        }else{
            modelAndView.setViewName("reminder");
            modelAndView.addObject("error", "This mail is not registered");
            return modelAndView;
        }

    }
}
