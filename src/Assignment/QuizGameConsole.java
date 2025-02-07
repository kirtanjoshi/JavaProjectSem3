package Assignment;

import java.util.Scanner;

public class QuizGameConsole {
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

    // Option 1: Generate Full Report
    private static void generateFullReport() {
        System.out.println("\n--- Full Report ---");
        PlayerSql.generateReport();
        // Replace with actual implementation to display all competitors' details.
    }

    // Option 2: Display Top Performer
    private static void displayTopPerformer() {
        System.out.println("\n--- Top Performer ---");
        PlayerSql.getHighestScoringPlayer();
        // Replace with actual implementation to fetch the top scorer.
    }

    // Option 3: Generate Statistics
    private static void generateStatistics() {
       PlayerSql.generateStatistics();
        // Implement logic to calculate and display statistics (e.g., average scores, total competitors).
    }

    // Option 4: Search Competitor by ID
    private static void searchCompetitorByID(int id) {
        System.out.println("\n--- Competitor Details ---");
        PlayerSql.searchCompetitorByID(id);
        // Replace with actual implementation to search and display competitor details.
    }
}
