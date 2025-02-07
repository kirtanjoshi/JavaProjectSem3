
package Assignment;

import java.sql.*;

import javax.swing.table.DefaultTableModel;

public class PlayerSql {
    private static final String URL = "jdbc:mysql://localhost:3306/competitordb";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    // Establish Database Connection
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    // Initialize Database and Create Table if Not Exists
    public static void playerConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("JDBC Driver Loaded Successfully");
            createTable();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Database Initialization Error: " + e.getMessage());
        }
    }

    // Create Table for Player Details
    private static void createTable() throws SQLException {
        String createTableSql = "CREATE TABLE IF NOT EXISTS playerDetails (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "name VARCHAR(255) UNIQUE, " +
                "level VARCHAR(50), " +
                "score1 INT DEFAULT 0, " +
                "score2 INT DEFAULT 0, " +
                "score3 INT DEFAULT 0, " +
                "score4 INT DEFAULT 0, " +
                "score5 INT DEFAULT 0)";
        
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(createTableSql);
            System.out.println("Table Created Successfully or Already Exists");
        }
    }

    // Insert New Player into Database
//    public static int insertDetails(String name, String level) {
//        String sql = "INSERT INTO playerDetails (name, level) VALUES (?, ?)";
//        try (Connection conn = getConnection();
//             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
//            pstmt.setString(1, name);
//            pstmt.setString(2, level);
//            pstmt.executeUpdate();
//            
//            ResultSet rs = pstmt.getGeneratedKeys();
//            if (rs.next()) {
//                return rs.getInt(1); // Return generated player ID
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return -1; // Return -1 if insertion fails
//    }
    
    
    public static int insertDetails(String name, String level) {
        String sql = "INSERT INTO playerDetails (name, level) VALUES (?, ?) " +
                     "ON DUPLICATE KEY UPDATE level = VALUES(level)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, name);
            pstmt.setString(2, level);
            pstmt.executeUpdate();

            // Retrieve the ID of the inserted/updated player
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1); // Return player ID
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Return -1 if insertion/updation fails
    }


    // Save Player's Score for Each Round
    public static void saveRoundScore(int playerId, int round, int score) {
        String column = "score" + round;
        String sql = "UPDATE playerDetails SET " + column + " = ? WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, score);
            pstmt.setInt(2, playerId);
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Score updated for round " + round + " for player ID: " + playerId);
            } else {
                System.out.println("Player ID not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Retrieve Player Score Data
    public static void getPlayerScores(int playerId) {
        String sql = "SELECT * FROM playerDetails WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, playerId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                System.out.println("Player ID: " + playerId);
                System.out.println("Name: " + rs.getString("name"));
                System.out.println("Level: " + rs.getString("level"));
                System.out.println("Score Round 1: " + rs.getInt("score1"));
                System.out.println("Score Round 2: " + rs.getInt("score2"));
                System.out.println("Score Round 3: " + rs.getInt("score3"));
                System.out.println("Score Round 4: " + rs.getInt("score4"));
                System.out.println("Score Round 5: " + rs.getInt("score5"));
            } else {
                System.out.println("Player not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    

    public static void loadPlayerDetails(DefaultTableModel model) {
        model.setRowCount(0);
        String sql = "SELECT * FROM playerDetails";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                model.addRow(new Object[]{rs.getInt("id"), rs.getString("name"), rs.getString("level"),
                        rs.getString("score1"), rs.getString("score2"), rs.getString("score3"),
                        rs.getString("score4"), rs.getString("score5")});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void getHighestScoringPlayer() {
        String sql = "SELECT id, name, level, " +
                     "(score1 + score2 + score3 + score4 + score5) AS total_score " +
                     "FROM playerDetails ORDER BY total_score DESC LIMIT 1";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            if (rs.next()) {
                System.out.println("Highest Scoring Player:");
                System.out.println("ID: " + rs.getInt("id"));
                System.out.println("Name: " + rs.getString("name"));
                System.out.println("Level: " + rs.getString("level"));
                System.out.println("Total Score: " + rs.getInt("total_score"));
            } else {
                System.out.println("No player records found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static void generateStatistics() {
        System.out.println("\n--- Statistics ---");

        String sql = "SELECT COUNT(*) AS totalPlayers, " +
                     "AVG(score1) AS avgScore1, AVG(score2) AS avgScore2, " +
                     "AVG(score3) AS avgScore3, AVG(score4) AS avgScore4, " +
                     "AVG(score5) AS avgScore5 FROM playerDetails";

        try (Connection conn = PlayerSql.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                System.out.println("Total Competitors: " + rs.getInt("totalPlayers"));
                System.out.printf("Average Score (Round 1): %.2f\n", rs.getDouble("avgScore1"));
                System.out.printf("Average Score (Round 2): %.2f\n", rs.getDouble("avgScore2"));
                System.out.printf("Average Score (Round 3): %.2f\n", rs.getDouble("avgScore3"));
                System.out.printf("Average Score (Round 4): %.2f\n", rs.getDouble("avgScore4"));
                System.out.printf("Average Score (Round 5): %.2f\n", rs.getDouble("avgScore5"));
            } else {
                System.out.println("No player data available.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void generateReport() {
        System.out.println("\n--- Competitor Details ---");

        // Use `PlayerSql.getPlayerScores` method to retrieve and print details in a table format
        String sql = "SELECT * FROM playerDetails ";
        try (var conn = PlayerSql.getConnection();
             var pstmt = conn.prepareStatement(sql)) {

            ResultSet rs = pstmt.executeQuery();
            System.out.println("+----+----------------+--------------+-------+-------+-------+-------+-------+");
            System.out.println("| ID | Name           | Level        | Score1| Score2| Score3| Score4| Score5|");
            System.out.println("+----+----------------+--------------+-------+-------+-------+-------+-------+");
            while (rs.next()) {
          
                System.out.printf("| %-2d | %-14s | %-5s | %-5d | %-5d | %-5d | %-5d | %-5d |\n",
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("level"),
                        rs.getInt("score1"),
                        rs.getInt("score2"),
                        rs.getInt("score3"),
                        rs.getInt("score4"),
                        rs.getInt("score5"));
                System.out.println("+----+----------------+-------+-------+-------+-------+-------+-------+");
            } 
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    
    
    public static void searchCompetitorByID(int playerId) {
        System.out.println("\n--- Competitor Details ---");

        // Use `PlayerSql.getPlayerScores` method to retrieve and print details in a table format
        String sql = "SELECT * FROM playerDetails WHERE id = ?";
        try (var conn = PlayerSql.getConnection();
             var pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, playerId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                System.out.println("+----+----------------+-------+-------+-------+-------+-------+-------+");
                System.out.println("| ID | Name           | Level | Score1| Score2| Score3| Score4| Score5|");
                System.out.println("+----+----------------+-------+-------+-------+-------+-------+-------+");
                System.out.printf("| %-2d | %-14s | %-5s | %-5d | %-5d | %-5d | %-5d | %-5d |\n",
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("level"),
                        rs.getInt("score1"),
                        rs.getInt("score2"),
                        rs.getInt("score3"),
                        rs.getInt("score4"),
                        rs.getInt("score5"));
                System.out.println("+----+----------------+-------+-------+-------+-------+-------+-------+");
            } else {
                System.out.println("No competitor found with ID: " + playerId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

	
}
