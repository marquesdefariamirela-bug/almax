package sp.senai.org.controlepatrimonio.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    @GetMapping("/")
    public String inicio() {
        return "redirect:/login";
    }
}
