package ge.edu.freeuni.Controllers;

import ge.edu.freeuni.Models.Email;
import ge.edu.freeuni.Models.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class SendCodeController {

    private User user;
//    @GetMapping("/sendcode")
//    public ModelAndView display(@RequestParam String Code){
//        ModelAndView modelAndView = new ModelAndView("submit");
//        return modelAndView;
//    }

    @PostMapping("/sendcode")
    public ModelAndView sendCode(@RequestParam String Username,
                                 @RequestParam String Password1,
                                 @RequestParam String eMail,
                                 @RequestParam String Avatar,
                                 @RequestParam String Button,
                                 @RequestParam String Code,
                                 HttpServletResponse resp,
                                 HttpServletRequest req) throws MessagingException, IOException {
        Email email = (Email)req.getServletContext().getAttribute("email");
        ModelAndView modelAndView = new ModelAndView("submit");
        if(Button.equals("Register")){
            int sentCode = email.getUsersCode(user.getMail());
            if(Integer.parseInt(Code) == sentCode){
                resp.sendRedirect("");
            }else{
                modelAndView.addObject("error", "Wrong Code!");
            }
            return modelAndView;
        }else{
            email.sendRandomCode(eMail);
            user = new User(Username,Password1, eMail, Avatar, 0);
            return modelAndView;
        }
    }
}
