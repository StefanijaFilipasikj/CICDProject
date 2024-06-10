package mk.ukim.finki.wp.eshop.web;

import jakarta.servlet.http.HttpServletRequest;
import mk.ukim.finki.wp.eshop.model.User;
import mk.ukim.finki.wp.eshop.service.AuthService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/login")
public class LoginController{
    private final AuthService authService;

    public LoginController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping
    public String getLoginPage(@RequestParam(required = false) String error, Model model){
        if(error != null && !error.isEmpty()){
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        model.addAttribute("bodyContent", "login");
        return "master-template";
    }

    @PostMapping
    public String login(@RequestParam String username, @RequestParam String password){
        try {
            authService.login(username,password);
        }catch (Exception ex){
            return "redirect:/login?error=" + ex.getMessage();
        }
        return "redirect:/home";
    }
}
