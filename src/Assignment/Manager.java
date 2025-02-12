package Assignment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;

/**
 * The {@code Manager} class handles database operations for managing quiz questions.
 * It provides methods for creating a database, managing tables, adding, updating, 
 * deleting, and retrieving questions from the database.
 * 
 * <p>This class uses MySQL as the database and requires the MySQL JDBC driver.</p>
 * 
 * @author Kirti Kirtan Joshi
 * @version 1.0
 * @since 2025
 */
public class Manager {

    /** MySQL database URL */
    private static final String URL = "jdbc:mysql://localhost:3306/";

    /** Database name */
    private static final String DATABASE_NAME = "competitordb";

    /** Database username */
    private static final String USERNAME = "root";

    /** Database password (default empty) */
    private static final String PASSWORD = "";

    /**
     * Establishes a connection to the MySQL database.
     * 
     * @return A {@code Connection} object to interact with the database.
     * @throws SQLException If a database access error occurs.
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL + DATABASE_NAME, USERNAME, PASSWORD);
    }

    /**
     * Initializes the database connection by loading the MySQL JDBC driver,
     * creating the database if it does not exist, and creating the necessary table.
     */
    public static void connection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("JDBC Driver Loaded Successfully");
            createDatabase();
            createTable();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Creates the database if it does not already exist.
     * 
     * @throws SQLException If an SQL error occurs while creating the database.
     */
    private static void createDatabase() throws SQLException {
        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             Statement stmt = conn.createStatement()) {
            
            String createDatabaseSql = "CREATE DATABASE IF NOT EXISTS " + DATABASE_NAME;
            stmt.executeUpdate(createDatabaseSql);
            System.out.println("Database created successfully or already exists");
        }
    }

    /**
     * Creates the `questions` table if it does not already exist.
     * 
     * @throws SQLException If an SQL error occurs while creating the table.
     */
    private static void createTable() throws SQLException {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {

            String createTableSql = "CREATE TABLE IF NOT EXISTS questions (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "difficulty TEXT, " +
                    "question TEXT, " +
                    "optionA TEXT, " +
                    "optionB TEXT, " +
                    "optionC TEXT, " +
                    "optionD TEXT, " +
                    "correctAnswer TEXT)";
            stmt.executeUpdate(createTableSql);
            System.out.println("Table created successfully or already exists");
        }
    }

    /**
     * Adds a new question to the database.
     * 
     * @param difficulty The difficulty level of the question.
     * @param question The question text.
     * @param optionA Option A.
     * @param optionB Option B.
     * @param optionC Option C.
     * @param optionD Option D.
     * @param correctAnswer The correct answer.
     * @return The ID of the newly inserted question.
     */
    public static int addQuestion(String difficulty, String question, String optionA, String optionB, String optionC, String optionD, String correctAnswer) {
        String sql = "INSERT INTO questions (difficulty, question, optionA, optionB, optionC, optionD, correctAnswer) VALUES (?, ?, ?, ?, ?, ?, ?)";
        int generatedId = -1;

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, difficulty);
            pstmt.setString(2, question);
            pstmt.setString(3, optionA);
            pstmt.setString(4, optionB);
            pstmt.setString(5, optionC);
            pstmt.setString(6, optionD);
            pstmt.setString(7, correctAnswer);
            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                generatedId = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return generatedId;
    }

    /**
     * Updates an existing question in the database.
     * 
     * @param id The ID of the question to update.
     * @param difficulty The new difficulty level.
     * @param question The new question text.
     * @param optionA The new Option A.
     * @param optionB The new Option B.
     * @param optionC The new Option C.
     * @param optionD The new Option D.
     * @param correctAnswer The new correct answer.
     */
    public static void updateQuestion(int id, String difficulty, String question, String optionA, String optionB, String optionC, String optionD, String correctAnswer) {
        String sql = "UPDATE questions SET difficulty = ?, question = ?, optionA = ?, optionB = ?, optionC = ?, optionD = ?, correctAnswer = ? WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, difficulty);
            pstmt.setString(2, question);
            pstmt.setString(3, optionA);
            pstmt.setString(4, optionB);
            pstmt.setString(5, optionC);
            pstmt.setString(6, optionD);
            pstmt.setString(7, correctAnswer);
            pstmt.setInt(8, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

  
    
    
    public static void deleteQuestion(int id) {
        String deleteSql = "DELETE FROM questions WHERE id = ?";
        String resetAutoIncrementSql = "ALTER TABLE questions AUTO_INCREMENT = (SELECT COALESCE(MAX(id), 0) FROM questions) + 1";

        try (Connection conn = getConnection();
             PreparedStatement deleteStmt = conn.prepareStatement(deleteSql)) {

            deleteStmt.setInt(1, id);
            int rowsAffected = deleteStmt.executeUpdate();

            if (rowsAffected > 0) {
                // Reset AUTO_INCREMENT only if deletion was successful
                try (Statement stmt = conn.createStatement()) {
                    stmt.executeUpdate(resetAutoIncrementSql);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * Loads all questions from the database into a table model.
     * 
     * @param model The table model where the data will be loaded.
     */
    public static void loadQuestions(DefaultTableModel model) {
        model.setRowCount(0);
        String sql = "SELECT * FROM questions";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                model.addRow(new Object[]{rs.getInt("id"), rs.getString("difficulty"), rs.getString("question"),
                        rs.getString("optionA"), rs.getString("optionB"), rs.getString("optionC"),
                        rs.getString("optionD"), rs.getString("correctAnswer")});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves a set of random questions based on difficulty level.
     * 
     * @param difficulty The difficulty level (e.g., "Beginner", "Intermediate", "Advanced").
     * @return A {@code ResultSet} containing the selected questions.
     * @throws SQLException If an SQL error occurs.
     */
    public static ResultSet getQuestionByDifficulty(String difficulty) throws SQLException {
        String sql = "SELECT * FROM questions WHERE difficulty = ? ORDER BY RAND()";
        Connection conn = getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, difficulty);
        return pstmt.executeQuery();
    }
}
