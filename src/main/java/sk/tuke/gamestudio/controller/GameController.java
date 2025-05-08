package sk.tuke.gamestudio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.entity.User;
import sk.tuke.gamestudio.game.mastermind.core.CodeGenerator;
import sk.tuke.gamestudio.game.mastermind.core.Game;
import sk.tuke.gamestudio.service.CommentService;
import sk.tuke.gamestudio.service.ScoreService;
import sk.tuke.gamestudio.service.RatingService;

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
    private List<String> history;

    @Autowired
    private ScoreService scoreService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private RatingService ratingService;


    public GameController() {
        this.codeGenerator = new CodeGenerator(CODE_LENGTH);
        this.history = new ArrayList<>();
    }

    @GetMapping
    public String showGamePage(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedUser");
        User previousUser = (User) session.getAttribute("previousUser");

        if (user == null) {
            user = new User("Guest", "guest@example.com", "");
            session.setAttribute("loggedUser", user); // Устанавливаем гостя в сессию
        }

        // Если пользователь изменился, сбрасываем историю
        if (previousUser != null && !previousUser.getName().equals(user.getName())) {
            history = new ArrayList<>();  // Создаем новый пустой список
        }
        session.setAttribute("previousUser", user);

        game = (Game) session.getAttribute("game");
        if (game == null) {
            game = new Game(codeGenerator.generateSecretCode(), user, null);
            session.setAttribute("game", game);
        }
        history = new ArrayList<>();
        session.setAttribute("history", history);


//        // Получаем историю из сессии
//        @SuppressWarnings("unchecked")
//        List<String> sessionHistory = (List<String>) session.getAttribute("history");
//        if (sessionHistory != null) {
//            history.clear();
//            history.addAll(sessionHistory);
//        }

        List<Comment> comments = commentService.getComments("Mastermind");
        List<Score> topScores = scoreService.getTopScores("Mastermind");
        if (topScores == null) {
            topScores = new ArrayList<>();
        }

        int averageRating = ratingService.getAverageRating("Mastermind");
        model.addAttribute("averageRating", averageRating);

        model.addAttribute("comments", comments);
        model.addAttribute("topScores", topScores);
        model.addAttribute("history", history);
        model.addAttribute("guessed", false);
        model.addAttribute("attempts", 0);
        model.addAttribute("isGuest", user.getName().equals("Guest"));
        model.addAttribute("user", user);

//        session.setAttribute("history", history); // Сохраняем историю в сессии
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

        List<Comment> comments = commentService.getComments("Mastermind");
        model.addAttribute("comments", comments);

        List<Score> topScores = scoreService.getTopScores("Mastermind");
        if (topScores == null) {
            topScores = new ArrayList<>();
        }
        model.addAttribute("topScores", topScores);

        // Передаём историю и состояние игры в модель
        model.addAttribute("history", history);
        model.addAttribute("guessed", game.isGuessed());
        model.addAttribute("attempts", game.getAttempts());
        model.addAttribute("isGuest", user.getName().equals("Guest")); // Проверка, является ли пользователь гостем

        session.setAttribute("history", history);


        if (game.isGuessed()) {
            model.addAttribute("score", user.getScore());

            // Если пользователь не гость, сохраняем его результат
            if (!user.getName().equals("Guest")) {
                Score score = new Score("Mastermind", user.getName(), user.getScore(), new Date());
                scoreService.addScore(score);
            }

            // Обнуляем игру
            game = null;

            session.removeAttribute("game");
            session.removeAttribute("history");

        }

        return "game";
    }

    @GetMapping("/leaderboard")
    public String getLeaderboard(Model model) {
        List<Score> topScores = scoreService.getTopScores("Mastermind");
        if (topScores == null) {
            topScores = new ArrayList<>();
        }
        model.addAttribute("topScores", topScores);
        return "fragments/leaderboard :: leaderboardContent";
    }

    @PostMapping("/comment")
    public String addComment(@RequestParam("comment") String commentText, HttpSession session) {
        User user = (User) session.getAttribute("loggedUser");

        // Комментарии могут оставлять только зарегистрированные пользователи
        if (user != null && !user.getName().equals("Guest")) {
            Comment comment = new Comment(commentText, user.getName(), new Date(), "Mastermind");
            commentService.addComment(comment);
        }

        return "redirect:/mastermind";
    }

    @PostMapping("/rate")
    public String rateGame(@RequestParam int rating, HttpSession session) {
        User user = (User) session.getAttribute("loggedUser");
        if (user != null && !user.getName().equals("Guest")) {
            Rating gameRating = new Rating(user.getName(), rating, new Date(), "Mastermind");
            ratingService.setRating(gameRating);
        }
        return "redirect:/mastermind";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/auth";
    }

    private void initGame(User user) {
        this.game = new Game(codeGenerator.generateSecretCode(), user, null);
    }
}