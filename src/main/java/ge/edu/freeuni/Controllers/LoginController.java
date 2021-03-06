package ge.edu.freeuni.Controllers;

import ge.edu.freeuni.DAO.BlacklistDAO;
import ge.edu.freeuni.DAO.ImageDAO;
import ge.edu.freeuni.DAO.UsersDAO;
import ge.edu.freeuni.Helpers.GenerateHash;
import ge.edu.freeuni.Models.Image;
import ge.edu.freeuni.Models.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@Controller
public class LoginController {

    @GetMapping("")
    public String login(HttpSession ses, HttpServletRequest req) {
        ses.setAttribute("user", null);
        return "login";
    }

    @PostMapping("")
    public ModelAndView Authenticate(@RequestParam String Username,
                               @RequestParam String Password,
                                @RequestParam String Button,
                                HttpServletRequest req,
                                HttpServletResponse resp,
                                HttpSession ses) throws IOException, SQLException {
        ses.setAttribute("user", null);
        GenerateHash hasher = new GenerateHash();
        if(Button.equals("Login")) {
            UsersDAO users = (UsersDAO) req.getServletContext().getAttribute("db");
            User userUsername = users.getUserByUsername(Username);
            User userEmail = users.getUserByeMail(Username);
            ModelAndView modelAndView;
            String url = "/admin";
            if (Username.equals("admin"))
                modelAndView = new ModelAndView("admin");
            else{
                url = "/home";
                modelAndView = new ModelAndView("home");
            }
            User user = null;
            if(userUsername != null){
                user = userUsername;
            }else if(userEmail != null){
                user = userEmail;
            }
            if (user == null) {
                modelAndView.addObject("Error", "Account Does not exist!");
            } else if (!user.getPassword().equals(hasher.generateHash(Password))) {
                modelAndView.addObject("Error", "Username or Password incorrect!");
            }
            if (user == null || !user.getPassword().equals(hasher.generateHash(Password))) {
                modelAndView.setViewName("login");
                setModelAttributes(req, ses, modelAndView);
                return modelAndView;
            }
            ses.setAttribute("user", user);
            resp.sendRedirect(url);
            setModelAttributes(req, ses, modelAndView);
            return modelAndView;
        }else {
            resp.sendRedirect("/register");
            return new ModelAndView("register");
        }
    }

    private void setModelAttributes(HttpServletRequest req, HttpSession ses, ModelAndView mv) throws SQLException {
        BlacklistDAO dao = (BlacklistDAO) req.getServletContext().getAttribute("blacklist");
        List<String> blacklist = dao.getAll();
        User curUser = (User)ses.getAttribute("user");
        String imgfile = null;
        if (curUser != null)
            imgfile = curUser.getAvatar();
        ImageDAO db = (ImageDAO) req.getServletContext().getAttribute("images");
        List<Image> images = db.getAll();
        mv.addObject("blacklist", blacklist);
        mv.addObject("imgfile", imgfile);
        mv.addObject("images", images);
    }
}
