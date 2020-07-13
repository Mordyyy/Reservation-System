package ge.edu.freeuni.Controllers;

import ge.edu.freeuni.DAO.ChallengesDAO;
import ge.edu.freeuni.DAO.TimeTableDAO;
import ge.edu.freeuni.Models.Cell;
import ge.edu.freeuni.Models.Challenge;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.List;

@Controller
public class ReceivedChallengesController {

    @GetMapping("/recChallenges")
    public String display(){
        return "received";
    }

    @PostMapping("/recChallenges")
    public String respondChallenge(@RequestParam String hiddenID,
                                   @RequestParam String Button,
                                   HttpServletRequest req) throws SQLException {

        ChallengesDAO dao = (ChallengesDAO) req.getServletContext().getAttribute("challenges");
        TimeTableDAO table = (TimeTableDAO) req.getServletContext().getAttribute("table");
        if(Button.equals("Reject")){
            Challenge challenge = dao.getChallenge(Integer.parseInt(hiddenID));
            int time = challenge.getTime();
            int computer = challenge.getComputerID();
            List<Challenge> lst = dao.getAllForComputerTime(time, computer);
            if (lst.size() == 1) {
                Cell curCell = table.get(time, computer);
                if (curCell.getColor().equals("yellow")) {
                    curCell.setColor("green");
                    curCell.setText("Free");
                }
                table.update(curCell);
            }
            dao.deleteChallenge(Integer.parseInt(hiddenID));
        }
        return "received";
    }
}
