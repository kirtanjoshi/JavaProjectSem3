package Assignment;

import java.util.Scanner;

/**
 * The {@code QuizGameConsole} class provides a console-based interface for managing
 * quiz competitors. Users can generate reports, view top performers, retrieve statistics,
 * and search for competitors by ID.
 * 
 * <p>This class interacts with the {@code PlayerSql} class to fetch and display data.</p>
 * 
 * @author [Your Name]
 * @version 1.0
 * @since 2025
 */
public class QuizGameConsole {

    /**
     * The main method that starts the console-based application.
     * Provides a menu-driven interface for the user.
     * 
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            // Display Menu
            System.out.println("\n--- Competitor Management System ---");
            System.out.println("1. Generate Full Report");
            System.out.println("2. Display Top Performer");
            System.out.println("3. Generate Statistics");
            System.out.println("4. Search Competitor by ID");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            // Get User Choice
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            switch (choice) {
                case 1:
                    generateFullReport();
                    break;
                case 2:
                    displayTopPerformer();
                    break;
                case 3:
                    generateStatistics();
                    break;
                case 4:
                    System.out.print("Enter Competitor ID: ");
                    int id = scanner.nextInt();
                    searchCompetitorByID(id);
                    break;
                case 5:
                    System.out.println("Exiting the system. Goodbye!");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }

    /**
     * Generates and displays a full report of all competitors.
     * Calls {@code PlayerSql.generateReport()} to retrieve data.
     */
    private static void generateFullReport() {
        System.out.println("\n--- Full Report ---");
        PlayerSql.generateReport();
    }

    /**
     * Displays the top-performing competitor based on total score.
     * Calls {@code PlayerSql.getHighestScoringPlayer()} to retrieve data.
     */
    private static void displayTopPerformer() {
        System.out.println("\n--- Top Performer ---");
        PlayerSql.getHighestScoringPlayer();
    }

    /**
     * Generates and displays quiz statistics such as total competitors and average scores.
     * Calls {@code PlayerSql.generateStatistics()} to retrieve data.
     */
    private static void generateStatistics() {
        PlayerSql.generateStatistics();
    }

    /**
     * Searches for a competitor by their unique ID and displays their details.
     * Calls {@code PlayerSql.searchCompetitorByID(int)} to retrieve data.
     * 
     * @param id The ID of the competitor to search for.
     */
    private static void searchCompetitorByID(int id) {
        System.out.println("\n--- Competitor Details ---");
        PlayerSql.searchCompetitorByID(id);
    }
}
