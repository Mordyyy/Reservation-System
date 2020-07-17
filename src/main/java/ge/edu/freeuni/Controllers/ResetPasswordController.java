package ge.edu.freeuni.Controllers;

import ge.edu.freeuni.DAO.UsersDAO;
import ge.edu.freeuni.Helpers.GenerateHash;
import ge.edu.freeuni.Helpers.Email;
import ge.edu.freeuni.Models.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@Controller
public class ResetPasswordController {
    @GetMapping("/reset")
    public String display(){
        return "reset";
    }

    @PostMapping("/reset")
    public ModelAndView remainder(//@RequestParam String userName,
                                  @RequestParam String oldpassword,
                                  @RequestParam String password1,
                                  @RequestParam String password2,
                                  HttpServletRequest req,
                                  HttpServletResponse resp,
                                  HttpSession ses) throws IOException, MessagingException, SQLException {
        GenerateHash hasher = new GenerateHash();
        String url = "/home";
        User user = (User)ses.getAttribute("user");
        ModelAndView modelAndView = new ModelAndView("home"); // there should be user page
        if (user.getUsername().equals("admin")) {
            url = "/admin";
            modelAndView.setViewName("admin");
        }
        UsersDAO usersDAO = (UsersDAO) req.getServletContext().getAttribute("db");
        Email mail = (Email) req.getServletContext().getAttribute("email");
        String userName = user.getUsername();
        if(oldpassword.equals(password1)){
            return errorMV(modelAndView, "Dude, Serious?");
        }else if(hasher.generateHash(oldpassword).equals(user.getPassword()) &&  password1.equals(password2) && password1.length() >= 6){
            usersDAO.changePassword(userName,hasher.generateHash(password1));
            resp.sendRedirect(url);
            return modelAndView;
        }else if (!hasher.generateHash(oldpassword).equals(user.getPassword())){
            return errorMV(modelAndView, "Your current password is not correct");
        }else{
            return errorMV(modelAndView, "Password doesn't match");
        }
    }

    private ModelAndView errorMV(ModelAndView mv, String errorMessage) {
        mv.setViewName("reset");
        mv.addObject("error", errorMessage);
        return mv;
    }

}
