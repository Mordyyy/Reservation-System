package ge.edu.freeuni.Controllers;

import ge.edu.freeuni.DAO.ChallengesDAO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ReceivedChallengesController {

    @GetMapping("/recChallenges")
    public String display(){
        return "received";
    }

    @PostMapping("/recChallenges")
    public String respondChallenge(@RequestParam String hiddenID,
                                   @RequestParam String Button,
                                   HttpServletRequest req){

        ChallengesDAO dao = (ChallengesDAO) req.getServletContext().getAttribute("challenges");
        if(Button.equals("Reject")){
            dao.deleteChallenge(Integer.parseInt(hiddenID));
        }
        return "received";
    }
}
