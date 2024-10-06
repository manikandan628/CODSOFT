import java.util.Random;
import java.util.Scanner;

public class GuessingGame {

    // Method to generate ASCII art for the game
    public static void displayWelcomeMessage() {
        System.out.println("=====================================");
        System.out.println("|     Welcome to the Number Game!    |");
        System.out.println("=====================================");
        System.out.println("|  Can you guess the magic number?   |");
        System.out.println("=====================================\n");
    }

    // Method to get a random number based on the difficulty level
    public static int generateRandomNumber(String difficulty, Random random) {
        switch (difficulty.toLowerCase()) {
            case "easy":
                return random.nextInt(50) + 1; // Number between 1-50
            case "hard":
                return random.nextInt(200) + 1; // Number between 1-200
            default:
                return random.nextInt(100) + 1; // Number between 1-100 (Medium level)
        }
    }

    // Method to provide "hot" or "cold" hints
    public static void giveHotColdHint(int guess, int randomNumber) {
        int difference = Math.abs(guess - randomNumber);
        if (difference >= 30) {
            System.out.println("You're freezing cold!");
        } else if (difference >= 20) {
            System.out.println("You're cold.");
        } else if (difference >= 10) {
            System.out.println("You're warm.");
        } else if (difference >= 5) {
            System.out.println("You're hot!");
        } else {
            System.out.println("You're burning hot!");
        }
    }

    // Method to handle each round of the game
    public static void startNewRound(Scanner scanner, Random random, String difficulty, int maxAttempts, int score) {
        int randomNumber = generateRandomNumber(difficulty, random);
        int attempts = 0;
        boolean guessedCorrectly = false;

        System.out.println("\nA random number has been generated. Try to guess it!");
        System.out.println("You have " + maxAttempts + " attempts.");
        
        // Loop for guessing
        while (attempts < maxAttempts) {
            System.out.print("Enter your guess: ");
            int userGuess;
            
            try {
                userGuess = scanner.nextInt();
            } catch (Exception e) {
                System.out.println("Please enter a valid number.");
                scanner.next(); // Clear invalid input
                continue;
            }

            attempts++;
            giveHotColdHint(userGuess, randomNumber); // Provide dynamic hints

            if (userGuess == randomNumber) {
                System.out.println("Congratulations! You've guessed the correct number in " + attempts + " attempts.");
                int bonusPoints = (maxAttempts - attempts); // Bonus points for guessing quicker
                score += bonusPoints;
                guessedCorrectly = true;
                break;
            } else if (userGuess > randomNumber) {
                System.out.println("Too high! Try a lower number.");
            } else {
                System.out.println("Too low! Try a higher number.");
            }

            System.out.println("Attempts left: " + (maxAttempts - attempts));
        }

        if (!guessedCorrectly) {
            System.out.println("Sorry, you've used all your attempts! The correct number was: " + randomNumber);
        }

        // Display score
        System.out.println("\nYour current score: " + score);

        // Play again option
        System.out.print("Do you want to play another round? (yes/no): ");
        String playAgain = scanner.next();
        
        if (playAgain.equalsIgnoreCase("yes")) {
            selectDifficulty(scanner, random, score); // Start another round with difficulty selection
        } else {
            System.out.println("\nThank you for playing! Your final score is: " + score);
        }
    }

    // Method to select difficulty level
    public static void selectDifficulty(Scanner scanner, Random random, int score) {
        System.out.println("\nChoose your difficulty level:");
        System.out.println("1. Easy (1-50)");
        System.out.println("2. Medium (1-100)");
        System.out.println("3. Hard (1-200)");
        System.out.print("Enter your choice (1/2/3): ");
        String difficultyChoice = scanner.next();

        String difficulty = "medium"; // Default difficulty
        int maxAttempts = 10;

        switch (difficultyChoice) {
            case "1":
                difficulty = "easy";
                maxAttempts = 8; // Fewer attempts for easier levels
                break;
            case "2":
                difficulty = "medium";
                maxAttempts = 10; // Standard attempts
                break;
            case "3":
                difficulty = "hard";
                maxAttempts = 12; // More attempts for harder levels
                break;
            default:
                System.out.println("Invalid choice, defaulting to Medium.");
        }

        // Start the game with the chosen difficulty
        startNewRound(scanner, random, difficulty, maxAttempts, score);
    }

    public static void main(String[] args) {
        // Initialize the Scanner for user input and Random for number generation
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        displayWelcomeMessage(); // Show welcome message with ASCII art
        int score = 0; // Track player's score

        // Start the game by selecting difficulty
        selectDifficulty(scanner, random, score);

        scanner.close();
    }
}
