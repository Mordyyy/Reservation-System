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
import javax.ws.rs.POST;
import java.io.IOException;

@Controller
public class RegisterController {
    @GetMapping("/register")
    public String display(){
        return "register";
    }

    @PostMapping("/register")
    public ModelAndView register(@RequestParam String Username,
                                 @RequestParam String Password1,
                                 @RequestParam String Password2,
                                 @RequestParam String eMail,
                                 @RequestParam String Code,
                                 @RequestParam String Avatar,
                                 @RequestParam String Button,
                                 HttpServletRequest req,
                                 HttpServletResponse resp) throws IOException, MessagingException {
        Email email = (Email)req.getServletContext().getAttribute("email");
        if(Button.equals("Button1")) {
            UsersDAO users = (UsersDAO) req.getServletContext().getAttribute("db");
            User user = users.getUser(Username);
            ModelAndView modelAndView = new ModelAndView("register");
            if (user != null) {
                modelAndView.addObject("error", "Account with username: " + Username + " Already exists!");
                return modelAndView;
            }
            if (!Password1.equals(Password2)) {
                modelAndView.addObject("error", "Passwords do not match!");
                return modelAndView;
            }

            int sentCode = email.getUsersCode(eMail);
            if(sentCode != Integer.parseInt(Code)){
                modelAndView.addObject("error", "Email Confirmation Failed!!");
                return modelAndView;
            }

            User realUser = new User(Username, Password1, eMail, Avatar);
            users.addUser(realUser);
            resp.sendRedirect("");
            return modelAndView;
        }else{
            email.sendRandomCode(eMail);
            return new ModelAndView("register");
        }
    }
}
