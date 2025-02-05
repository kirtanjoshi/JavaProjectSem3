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
            // Load MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("JDBC Driver Loaded Successfully");

            // Create database if it does not exist
            createDatabase();

            // Connect to the database and create table
            createTable();

        } catch (ClassNotFoundException e) {
            System.out.println("Error loading JDBC Driver: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
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
            		"difficulty TEXT, "+
                    "question TEXT, " +
                    "optionA TEXT, " +
                    "optionB TEXT, " +
                    "optionC TEXT, " +
                    "optionD TEXT)";
            stmt.executeUpdate(createTableSql);
            System.out.println("Table created successfully or already exists");
        }
    }

    public static int addQuestion( String difficulty,String question, String optionA, String optionB, String optionC, String optionD) {
        String sql = "INSERT INTO questions (difficulty,question, optionA, optionB, optionC, optionD) VALUES (?, ?, ?, ?, ?,?)";
        int generatedId = -1;

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
        	 pstmt.setString(1, difficulty);
            pstmt.setString(2, question);
           
            pstmt.setString(3, optionA);
            pstmt.setString(4, optionB);
            pstmt.setString(5, optionC);
            pstmt.setString(6, optionD);
       
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


    public static void updateQuestion(int id, String difficulty, String question, String optionA, String optionB, String optionC, String optionD ) {
        String sql = "UPDATE questions SET difficulty = ?, question = ?, optionA = ?, optionB = ?, optionC = ?, optionD = ?  WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
        	pstmt.setString(1, difficulty);
            pstmt.setString(2, question);
            
            pstmt.setString(3, optionA);
            pstmt.setString(4, optionB);
            pstmt.setString(5, optionC);
            pstmt.setString(6, optionD);
            pstmt.setInt(7, id);
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

            // Reset AUTO_INCREMENT to maintain sequential IDs
            try (Statement stmt = conn.createStatement()) {
                stmt.executeUpdate("ALTER TABLE questions AUTO_INCREMENT = 1");
            }
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
                model.addRow(new Object[]{rs.getInt("id"), rs.getString("difficulty"), rs.getString("question"), rs.getString("optionA"),
                        rs.getString("optionB"), rs.getString("optionC"), rs.getString("optionD")});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static ResultSet getQuestionById(int questionId) throws SQLException {
    	String sql = "SELECT * FROM questions WHERE id = ?";

        Connection conn = getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, questionId);
        return pstmt.executeQuery(); // Returns the result set
    }


}

