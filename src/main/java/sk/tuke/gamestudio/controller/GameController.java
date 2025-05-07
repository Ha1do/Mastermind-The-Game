package sk.tuke.gamestudio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.entity.User;
import sk.tuke.gamestudio.game.mastermind.core.CodeGenerator;
import sk.tuke.gamestudio.game.mastermind.core.Game;
import sk.tuke.gamestudio.service.ScoreService;

import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/mastermind")
public class GameController {
    private static final int CODE_LENGTH = 4;
    private final CodeGenerator codeGenerator;
    private Game game;
    private final List<String> history;

    @Autowired
    private ScoreService scoreService;

    public GameController() {
        this.codeGenerator = new CodeGenerator(CODE_LENGTH);
        this.history = new ArrayList<>();
    }

    @GetMapping
    public String showGamePage(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedUser");

        // Если пользователь не авторизован, создаём гостевого пользователя
        if (user == null) {
            user = new User("Guest", "guest@example.com", "");
            session.setAttribute("loggedUser", user); // Устанавливаем гостя в сессию
        }

        // Если игра ещё не инициализирована, создаём игру для пользователя
        if (game == null) {
            initGame(user);
        }

        model.addAttribute("history", history);
        model.addAttribute("guessed", game.isGuessed());
        model.addAttribute("attempts", game.getAttempts());
        model.addAttribute("isGuest", user.getName().equals("Guest")); // Проверка, является ли пользователь гостем
        return "game";
    }

    @PostMapping("/play")
    public String playGame(@RequestParam("guess0") int guess0,
                           @RequestParam("guess1") int guess1,
                           @RequestParam("guess2") int guess2,
                           @RequestParam("guess3") int guess3,
                           HttpSession session,
                           Model model) {
        User user = (User) session.getAttribute("loggedUser");

        // Если пользователь не авторизован (гостевой режим), создаём гостя
        if (user == null) {
            user = new User("Guest", "guest@example.com", "");
            session.setAttribute("loggedUser", user);
        }

        int[] guess = {guess0, guess1, guess2, guess3};
        char[] feedback = game.checkGuessAndReturnFeedback(guess);

        // Преобразуем каждый символ обратной связи (G, Y, N) в HTML элемент с цветным кружком
        StringBuilder feedbackHtml = new StringBuilder();
        for (char fb : feedback) {
            switch (fb) {
                case 'G' -> feedbackHtml.append("<span class='feedback-circle green'></span>");
                case 'Y' -> feedbackHtml.append("<span class='feedback-circle yellow'></span>");
                case 'N' -> feedbackHtml.append("<span class='feedback-circle red'></span>");
            }
        }

        // Добавляем новый элемент истории
        String entry = String.format("%d %d %d %d → %s", guess0, guess1, guess2, guess3, feedbackHtml);
        history.add(entry);

        // Передаём историю и состояние игры в модель
        model.addAttribute("history", history);
        model.addAttribute("guessed", game.isGuessed());
        model.addAttribute("attempts", game.getAttempts());
        model.addAttribute("isGuest", user.getName().equals("Guest")); // Проверка, является ли пользователь гостем

        if (game.isGuessed()) {
            model.addAttribute("score", user.getScore());

            // Если пользователь не гость, сохраняем его результат
            if (!user.getName().equals("Guest")) {
                Score score = new Score("Mastermind", user.getName(), user.getScore(), new Date());
                scoreService.addScore(score);
            }

            // Обнуляем игру
            game = null;
        }

        return "game";
    }

    private void initGame(User user) {
        this.game = new Game(codeGenerator.generateSecretCode(), user, null);
    }
}