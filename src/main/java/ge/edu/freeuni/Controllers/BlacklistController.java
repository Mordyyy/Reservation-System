package ge.edu.freeuni.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BlacklistController {

    @GetMapping("/blacklist")
    public String render() {
        return "blacklist";
    }
}
