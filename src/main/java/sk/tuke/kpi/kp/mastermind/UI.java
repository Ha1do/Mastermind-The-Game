package main.java.sk.tuke.kpi.kp.mastermind;

public record UI() {
    public void printRules()
    {
        System.out.println("Welcome to \u001B[31mMASTERMIND\u001B[0m! Try to guess the secret code.");
        System.out.println("The secret code consists of 4 digits from \u001B[34m0\u001B[0m to \u001B[34m9\u001B[0m.");
        System.out.println("Digits can be repeated in the secret code. Like \u001B[34m1122\u001B[0m.");
        System.out.println("After each guess, you will receive feedback:");
        System.out.println("\u001B[32mG\u001B[0m - a digit is \u001B[4mcorrect\u001B[0m and in the \u001B[4mcorrect position\u001B[0m");
        System.out.println("\u001B[33mY\u001B[0m - a digit is \u001B[4mcorrect\u001B[0m but in the \u001B[4mwrong position\u001B[0m");
        System.out.println("\u001B[31mR\u001B[0m - a digit is not in the secret code");
    }

    public void win(int attempts) {
        System.out.println("Congratulations! You guessed the secret code in " + attempts + " attempts.");
    }
}
