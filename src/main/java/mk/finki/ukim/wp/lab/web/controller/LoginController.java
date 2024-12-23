package mk.finki.ukim.wp.lab.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    // This method maps the /login URL to your custom login page
    @GetMapping("/login")
    public String login() {
        return "login";  // This will return the login.html Thymeleaf template
    }
}
