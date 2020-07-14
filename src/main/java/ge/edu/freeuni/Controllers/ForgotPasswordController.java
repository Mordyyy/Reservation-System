package ge.edu.freeuni.Controllers;

import ge.edu.freeuni.DAO.UsersDAO;
import ge.edu.freeuni.Hash.GenerateHash;
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
import java.sql.SQLException;
import java.util.Random;

@Controller
public class ForgotPasswordController {
    @GetMapping("/forgot")
    public String display(){
        return "forgot";
    }

    @PostMapping("/forgot")
    public ModelAndView remainder(@RequestParam String email,
                                 HttpServletRequest req,
                                 HttpServletResponse resp) throws IOException, MessagingException, SQLException {
        GenerateHash hasher = new GenerateHash();
        ModelAndView modelAndView = new ModelAndView("login");
        UsersDAO usersDAO = (UsersDAO) req.getServletContext().getAttribute("db");
        Email mail = (Email) req.getServletContext().getAttribute("email");
        Random rand = new Random();
        int randInteger = rand.nextInt(1000000) + 100000;
        if(usersDAO.getUserByeMail(email) != null){
            UsersDAO db = (UsersDAO)req.getServletContext().getAttribute("db");
            String passwordMessagePref = "Your new password is: " + randInteger + "\n";
            String passwordMessageSuf = "Please log in if you want to change password";
            String passwordMessage = passwordMessagePref + passwordMessageSuf;
            mail.sendCode(email,"New password", passwordMessage);
            db.changePassword(db.getUserByeMail(email).getUsername(),
                    hasher.generateHash(Integer.toString(randInteger)));
            return modelAndView;
        }else{
            modelAndView.setViewName("forgot");
            modelAndView.addObject("error", "This mail is not registered");
            return modelAndView;
        }
    }
}
