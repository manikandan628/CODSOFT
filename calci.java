import java.util.Scanner;

public class calci {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Input: Number of subjects
        System.out.print("Enter the number of subjects: ");
        int numSubjects = scanner.nextInt();

        // Input validation for number of subjects
        while (numSubjects <= 0) {
            System.out.print("Please enter a valid number of subjects (greater than 0): ");
            numSubjects = scanner.nextInt();
        }

        // Input: Marks obtained in each subject
        double[] marks = new double[numSubjects];
        double totalMarks = 0;
        boolean pass = true; // To track if the student has passed all subjects

        for (int i = 0; i < numSubjects; i++) {
            System.out.print("Enter marks for subject " + (i + 1) + " (out of 100): ");
            double mark = scanner.nextDouble();

            // Input validation for marks
            while (mark < 0 || mark > 100) {
                System.out.print("Invalid input. Enter marks between 0 and 100: ");
                mark = scanner.nextDouble();
            }

            marks[i] = mark;
            totalMarks += mark;

            // Check if the student has failed any subject
            if (mark < 40) {
                pass = false;
            }
        }

        // Calculate Average Percentage
        double averagePercentage = totalMarks / numSubjects;

        // Grade Calculation
        char grade;
        if (averagePercentage >= 90) {
            grade = 'A';
        } else if (averagePercentage >= 80) {
            grade = 'B';
        } else if (averagePercentage >= 70) {
            grade = 'C';
        } else if (averagePercentage >= 60) {
            grade = 'D';
        } else {
            grade = 'F';
        }

        // Display individual subject marks
        System.out.println("\nMarks obtained in each subject:");
        for (int i = 0; i < numSubjects; i++) {
            System.out.println("Subject " + (i + 1) + ": " + marks[i]);
        }

        // Display Results
        System.out.println("\nTotal Marks: " + totalMarks);
        System.out.printf("Average Percentage: %.2f%%\n", averagePercentage);
        System.out.println("Grade: " + grade);

        // Display pass/fail summary
        if (pass) {
            System.out.println("Status: Passed");
        } else {
            System.out.println("Status: Failed (You have scored below 40 in one or more subjects)");
        }

        // Additional feedback based on the grade
        switch (grade) {
            case 'A':
                System.out.println("Excellent performance! Keep it up.");
                break;
            case 'B':
                System.out.println("Good job! You can aim higher next time.");
                break;
            case 'C':
                System.out.println("Fair performance. There's room for improvement.");
                break;
            case 'D':
                System.out.println("You passed, but you should work harder.");
                break;
            case 'F':
                System.out.println("You have failed. Consider revising the subjects and improving.");
                break;
        }

        scanner.close();
    }
}
