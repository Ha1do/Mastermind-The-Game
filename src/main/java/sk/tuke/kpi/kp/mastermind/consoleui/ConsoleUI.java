package sk.tuke.kpi.kp.mastermind.consoleui;

import org.springframework.stereotype.Component;
import sk.tuke.kpi.kp.mastermind.core.User;
import sk.tuke.kpi.kp.mastermind.gamestudio.entity.Comment;
import sk.tuke.kpi.kp.mastermind.gamestudio.entity.Rating;
import sk.tuke.kpi.kp.mastermind.gamestudio.entity.Score;
import sk.tuke.kpi.kp.mastermind.gamestudio.service.*;

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

@Component
public class ConsoleUI
{
    private final Scanner scanner = new Scanner(System.in);

    public void Welcome()
    {
        System.out.println("Welcome to \u001B[31mMASTERMIND\u001B[0m! Your task is to guess the secret code.");
        System.out.println("The secret code usually contains \u001B[34m4\u001B[0m digits.");
        System.out.println("But you can choose any number between \u001B[33m1\u001B[0m and \u001B[31m50\u001B[0m." +
                "(The recommended value is from \u001B[32m4\u001B[0m to \u001B[32m10\u001B[0m).");
        System.out.println("The more digits you choose, the harder the game will be.");
        System.out.println("But the more digits you choose, the more points you will get.");
        System.out.println("Digits can be repeated in the secret code. Like \u001B[34m1122\u001B[0m.");
        System.out.println("After each guess, you will receive feedback:");
        System.out.println("\u001B[32mG\u001B[0m (from \u001B[32mGreen\u001B[0m) - means that you have " +
                "\u001B[32mguessed\u001B[0m both, \u001B[4m\u001B[32mnumber\u001B[0m and its " +
                "\u001B[04m\u001B[32mposition\u001B[0m.");
        System.out.println("\u001B[33mY\u001B[0m (from \u001B[33mYellow\u001B[0m) - means that you " +
                "\u001B[33mguessed\u001B[0m the \u001B[32m\u001B[4mnumber\u001B[0m but not its " +
                "\u001B[31m\u001B[4mposition\u001B[0m.");
        System.out.println("\u001B[31mR\u001B[0m (from \u001B[31mRed\u001B[0m) - means that the " +
                "\u001B[31mnumber\u001B[0m is \u001B[4mnot in the secret code at all\u001B[0m.");
        System.out.println("Good luck!");
    }

    public void AskForName(User user)
    {
        System.out.print("Please enter your name here: ");
        user.setName(scanner.nextLine());
    }

    public void seeComsRatsScores(ScoreServiceJDBC scoreservice, CommentServiceJDBC com, RatingServiceJDBC reting)
    {
        System.out.println("Before we start, you can check the top scores, ratings and comments of the game.");
        System.out.println("To do that, type 'yes'. If not, type anything else.");
        String input = scanner.nextLine();
        if (input.equals("yes"))
        {
            System.out.println("If you want to see the top 4 players with the highest score, enter 'topscores'.");
            System.out.println("If you want to see the comments, enter 'comments'.");
            System.out.println("If you want to see the AVG game rating, enter 'avg'.");
            System.out.println("If you want to start the game, enter 'start'.");

            String pick = scanner.nextLine();
            while (!(pick.equals("start")))
            {
                switch (pick)
                {
                    case "topscores":
                        System.out.println("Top scores: ");
                        List<Score> scores = scoreservice.getTopScores("Mastermind");
                        if (scores.isEmpty())
                        {
                            System.out.println("No scores available.");
                        } else
                        {
                            scores.forEach(score -> System.out.println("Player: "
                                    + score.getPlayer() + ", Score: " + score.getPoints()));
                        }
                        break;
                    case "comments":
                        System.out.println("Comments: ");
                        List<Comment> comments = com.getComments("Mastermind");
                        if (comments.isEmpty())
                        {
                            System.out.println("No comments available.");
                        } else
                        {
                            comments.forEach(comment -> System.out.println(
                                    "Player: " + comment.getPlayer() +
                                            ", Comment: " + comment.getComment() +
                                            ", Date: " + new SimpleDateFormat("dd-MM-yyyy").format(comment.getCommentedOn())));
                        }
                        break;
                    case "avg":
                        System.out.println("Average rating: ");
                        try
                        {
                            int avgRating = reting.getAverageRating("Mastermind");
                            System.out.println(avgRating);
                        } catch (RatingException e)
                        {
                            System.out.println("No ratings available.");
                        }
                        break;
                    default:
                        System.out.println("Wrong input. Please enter one of the options above: ");
                        break;
                }
                pick = scanner.nextLine();
            }
        }
    }

    public int getNumber()
    {
        System.out.print("Enter the number of digits in the secret code: ");
        String input = scanner.nextLine();
        if (input.matches("[1-9]|[1-4][0-9]|50"))
            return Integer.parseInt(input);
        else
        {
            System.out.print("Wrong input. Please enter a number from 1 to 50: \n");
            return getNumber();
        }
    }

    public int[] attempGuess(int count)
    {
        System.out.print("Enter your guess: ");
        String input = scanner.nextLine();
        if (input.matches("\\d{" + count + "}"))
        {
            int[] guess = new int[count];
            for (int i = 0; i < count; i++)
            {
                guess[i] = Character.getNumericValue(input.charAt(i));
            }
            return guess;
        } else
        {
            System.out.print("Wrong input. Please enter a number with " + count + " digits: \n");
            return attempGuess(count);
        }
    }

    public void feedBack(char[] answer)
    {
        System.out.print("Your guess is   : ");
        for (char match : answer)
        {
            switch (match)
            {
                case 'G' -> System.out.print("\u001B[32mG\u001B[0m");
                case 'Y' -> System.out.print("\u001B[33mY\u001B[0m");
                default -> System.out.print("\u001B[31mR\u001B[0m");

            }
        }
        System.out.println("\n");
    }

    public void win(int attempts, int score)
    {
        System.out.println("Congratulations! You guessed the secret code in " + attempts + " attempts " + "and you got " + score + " points.");
    }

    public void askForCommentRating(String name, Date date,
                                    CommentServiceJDBC commentService, RatingServiceJDBC ratingService)
    {
        System.out.println("If you like to leave a comment or rate the game, type 'yes'. If not, type anything else.");
        String input = scanner.nextLine();
        if (input.equals("yes"))
        {
            System.out.println("Enter your comment: ");
            String com = scanner.nextLine();

            System.out.println("Enter your rating from 1 to 10: ");
            String inputRat = scanner.nextLine();
            while (!inputRat.matches("[1-9]|10"))
            {
                System.out.println("Wrong input. Please enter a number from 1 to 10: ");
                inputRat = scanner.nextLine();
            }
            int rat = Integer.parseInt(inputRat);

            Comment comment = new Comment(com, name, date, "Mastermind");
            Rating rating = new Rating(name, rat, date, "Mastermind");

            commentService.addComment(comment);
            ratingService.setRating(rating);
        }
    }

    public void End()
    {
        System.out.println("Goodbye!");
        scanner.close();
    }
}
