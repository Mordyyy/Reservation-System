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
    public ModelAndView remainder(//@RequestParam String userName,
                                  @RequestParam String oldPassword,
                                  @RequestParam String password1,
                                  @RequestParam String password2,
                                  HttpServletRequest req,
                                  HttpServletResponse resp) throws IOException, MessagingException {
//        ModelAndView modelAndView = new ModelAndView("/"); // there should be user page
//        UsersDAO usersDAO = (UsersDAO) req.getServletContext().getAttribute("db");
//        Email mail = (Email) req.getServletContext().getAttribute("email");
//        User user = usersDAO.getUserByUsername(userName);
//        if(oldPassword.equals(password1)){
//            modelAndView.setViewName("reset");
//            modelAndView.addObject("error", "Dude, Serious?");
//            return modelAndView;
//        }else if(oldPassword.equals(user.getPassword()) &&  password1.equals(password2) && password1.length() >= 6){
//            usersDAO.changePassword(userName,password1);
//            return modelAndView;
//        }else if (!oldPassword.equals(user.getPassword())){
//            modelAndView.setViewName("reset");
//            modelAndView.addObject("error", "Your current password is not correct");
//            return modelAndView;
//        }else{
//            modelAndView.setViewName("reset");
//            modelAndView.addObject("error", "Password doesn't match");
//            return modelAndView;
//        }
        return null; 
    }
}
