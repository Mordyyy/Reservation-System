package ge.edu.freeuni.Controllers;

import ge.edu.freeuni.DAO.UsersDAO;
import ge.edu.freeuni.Models.Email;
import ge.edu.freeuni.Models.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.POST;

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
                                 HttpServletRequest req,
                                 HttpServletResponse resp){
        UsersDAO users = (UsersDAO)req.getServletContext().getAttribute("db");
        User user =  users.getUser(Username);
        ModelAndView modelAndView = new ModelAndView("register");
        if(user != null){
            modelAndView.addObject("error", "Account with username: " + Username + " Already exists!");
            return modelAndView;
        }
        Email email = new Email();

    }
}
