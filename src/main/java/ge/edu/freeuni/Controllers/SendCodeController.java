package ge.edu.freeuni.Controllers;
import ge.edu.freeuni.DAO.UsersDAO;
import ge.edu.freeuni.Helpers.Email;
import ge.edu.freeuni.Models.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@Controller
public class SendCodeController {
    private User user;

    @PostMapping("/sendcode")
    public ModelAndView sendCode(@RequestParam String Button,
                                 HttpServletResponse resp,
                                 HttpServletRequest req) throws IOException, SQLException {
        Email email = (Email) req.getServletContext().getAttribute("email");
        ModelAndView modelAndView = new ModelAndView("submit");
        if (Button.equals("Submit")) {
            String Code = (String)req.getParameter("Code");
            int sentCode = email.getUsersCode(user.getMail());
            if (Integer.parseInt(Code) == sentCode) {
                ((UsersDAO)req.getServletContext().getAttribute("db")).addUser(user);
                resp.sendRedirect("");
            } else {
                modelAndView.addObject("error", "Wrong Code!");
            }
        } else {
            try {
                email.sendRandomCode((String)req.getParameter("eMail"));
            } catch (MessagingException e) {
                ModelAndView mv = new ModelAndView("register");
                mv.addObject("error", "Wrong Mail Address!");
                return mv;
            }
            user = new User((String)req.getParameter("Username"),
                    (String)req.getParameter("Password1"),
                    (String)req.getParameter("eMail"));
        }
        return modelAndView;
    }
}
