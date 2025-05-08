package sk.tuke.gamestudio.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sk.tuke.gamestudio.entity.User;
import sk.tuke.gamestudio.service.UserService;

@Controller
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String showAuthPage(@RequestParam(value = "currentForm", defaultValue = "signIn") String currentForm,
                               Model model) {
        model.addAttribute("currentForm", currentForm);
        return "login";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam("username") String username,
                               @RequestParam("email") String email,
                               @RequestParam("password") String password,
                               Model model,
                               HttpSession session) {
        if (userService.register(username, email, password))
        {
            session.removeAttribute("game");
            session.removeAttribute("history");

            User user = userService.authenticate(username, password);
            session.setAttribute("loggedUser", user);

            return "redirect:/mastermind";

        } else {
            model.addAttribute("error", "Этот email или имя пользователя уже заняты.");
            model.addAttribute("currentForm", "signUp");
            return "login";
        }
    }


    // Метод логина теперь принимает username вместо email
    @PostMapping("/login")
    public String loginUser(@RequestParam("username") String username,
                            @RequestParam("password") String password,
                            Model model,
                            HttpSession session) {
        User user = userService.authenticate(username, password);
        if (user != null)
        {
            session.removeAttribute("game");
            session.removeAttribute("history");

            session.setAttribute("loggedUser", user); // Сохраняем пользователя в сессии
            return "redirect:/mastermind";
        } else {
            model.addAttribute("error", "Неверное имя пользователя или пароль.");
            model.addAttribute("currentForm", "signIn");
            return "login";
        }
    }

    @PostMapping("/guest")
    public String playAsGuest(Model model) {
        User guestUser = new User("Guest", "guest@example.com", ""); // Гостевой пользователь
        model.addAttribute("user", guestUser);
        return "redirect:/mastermind";
    }
}