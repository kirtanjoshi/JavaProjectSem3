 package Assignment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;

public class Manager {

    private static final String URL = "jdbc:mysql://localhost:3306/";
    private static final String DATABASE_NAME = "competitordb";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL + DATABASE_NAME, USERNAME, PASSWORD);
    }

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

    private static void createDatabase() throws SQLException {
        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             Statement stmt = conn.createStatement()) {

            String createDatabaseSql = "CREATE DATABASE IF NOT EXISTS " + DATABASE_NAME;
            stmt.executeUpdate(createDatabaseSql);
            System.out.println("Database created successfully or already exists");
        }
    }

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
        String sql = "DELETE FROM questions WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

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
    
    public static ResultSet getQuestionByDifficulty(String difficulty) throws SQLException {
        String sql = "SELECT * FROM questions WHERE difficulty = ? ORDER BY RAND()";
        Connection conn = getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, difficulty);
        return pstmt.executeQuery();
    }
}
