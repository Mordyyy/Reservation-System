package ge.edu.freeuni.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SendCodeController {

    @GetMapping("/submit")
    public String display(){
        return "submit";
    }
}
