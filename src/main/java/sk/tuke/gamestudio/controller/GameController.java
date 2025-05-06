package sk.tuke.gamestudio.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sk.tuke.gamestudio.game.mastermind.core.CodeGenerator;
import sk.tuke.gamestudio.game.mastermind.core.Game;
import sk.tuke.gamestudio.game.mastermind.core.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/mastermind")
public class GameController {
    private static final int CODE_LENGTH = 4;
    private final CodeGenerator codeGenerator;
    private Game game;
    private final User user;
    private final List<String> history;

    @GetMapping("/test")
    @ResponseBody
    public String testRoute() {
        return "Контроллер работает!";
    }

    public GameController() {
        this.codeGenerator = new CodeGenerator(CODE_LENGTH);
        this.user = new User();
        this.history = new ArrayList<>();
        initGame();
    }

    private void initGame() {
        user.setName("Player");
        this.game = new Game(codeGenerator.generateSecretCode(), user,
           null);
    }

    @GetMapping
    public String showGamePage(Model model) {
        model.addAttribute("history", history);
        model.addAttribute("guessed", game.isGuessed());
        model.addAttribute("attempts", game.getAttempts());
        return "game";
    }

    @PostMapping("/play")
    public String playGame(@RequestParam("guess0") int guess0,
                           @RequestParam("guess1") int guess1,
                           @RequestParam("guess2") int guess2,
                           @RequestParam("guess3") int guess3,
                           Model model) {
        int[] guess = {guess0, guess1, guess2, guess3};
        char[] feedback = game.checkGuessAndReturnFeedback(guess);
        String entry = String.format("%d %d %d %d → %s", guess0, guess1, guess2, guess3, new String(feedback));
        history.add(entry);

        model.addAttribute("history", history);
        model.addAttribute("guessed", game.isGuessed());
        model.addAttribute("attempts", game.getAttempts());
        if (game.isGuessed()) {
            model.addAttribute("score", user.getScore());
        }

        return "game";
    }
}