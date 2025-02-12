package Assignment;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 * The {@code PlayerLogin} class provides a login interface for players 
 * to enter their name and select a difficulty level before starting the quiz.
 * 
 * <p>This class extends {@code JFrame} and interacts with {@code PlayerSql} 
 * to store player details in the database.</p>
 * 
 * @author Kirti Kirtan Joshi
 * @version 1.0
 * @since 2025
 */
public class PlayerLogin extends JFrame {

    private static final long serialVersionUID = 1L;

    /** The main content panel for the Player Login window. */
    private JPanel contentPane;

    /** Text field for entering the player's name. */
    private JTextField nameField;

    /** Dropdown for selecting the difficulty level. */
    private JComboBox<String> difficultyBox;

    /**
     * Launches the PlayerLogin application.
     * 
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    PlayerLogin frame = new PlayerLogin();
                    frame.setVisible(true);
                    PlayerSql.playerConnection(); // Establish database connection
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Constructs the {@code PlayerLogin} window.
     * Initializes GUI components, sets up event listeners, and allows
     * players to input their name and select a difficulty level.
     */
    public PlayerLogin() {
        setTitle("Player Login"); // Set window title
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 400, 250);

        // Initialize content panel
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // Dropdown options for difficulty levels
        String[] difficultyLevels = {"Beginner", "Intermediate", "Advance"};
        difficultyBox = new JComboBox<>(difficultyLevels);
        difficultyBox.setBounds(134, 95, 150, 25);
        contentPane.add(difficultyBox);

        // Player Name Label
        JLabel lblName = new JLabel("Player Name:");
        lblName.setBounds(24, 60, 100, 25);
        contentPane.add(lblName);

        // Player Name Input Field
        nameField = new JTextField();
        nameField.setBounds(134, 60, 150, 25);
        contentPane.add(nameField);
        nameField.setColumns(10);

        // Player Level Label
        JLabel lblLevel = new JLabel("Player Level:");
        lblLevel.setBounds(24, 95, 100, 25);
        contentPane.add(lblLevel);

        // Login Button
        JButton btnLogin = new JButton("Login");
        btnLogin.setBounds(67, 141, 100, 30);
        contentPane.add(btnLogin);

        // Message Label for status updates
        JLabel messageLabel = new JLabel("");
        messageLabel.setBounds(50, 160, 300, 25);
        contentPane.add(messageLabel);

        // Switch to Main Login Button
        JButton switchButton = new JButton("Switch to Login");
        switchButton.addActionListener(new ActionListener() {
            /**
             * Handles the action when the "Switch to Login" button is clicked.
             * Opens the {@code Login} window and closes the current login screen.
             * 
             * @param e The event triggered when the button is clicked.
             */
            public void actionPerformed(ActionEvent e) {
                new Login().setVisible(true);
                dispose();
            }
        });
        switchButton.setBounds(244, 10, 112, 40);
        contentPane.add(switchButton);

        // Title Label
        JLabel lblNewLabel = new JLabel("Player Login");
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblNewLabel.setBounds(67, 15, 112, 26);
        contentPane.add(lblNewLabel);

        
        btnLogin.addActionListener(new ActionListener() {
        	/**
//           * Handles the login action.
//           * Validates input fields, stores player details in the database,
//           * and starts the game if the login is successful.
//           * 
//           * @param e The event triggered when the login button is clicked.
//           */
            public void actionPerformed(ActionEvent e) {
                String playerName = nameField.getText();
                String playerLevel = (String) difficultyBox.getSelectedItem();

                if (playerName.isEmpty() || playerLevel.isEmpty()) {
                    messageLabel.setText("Please enter all fields.");
                } else {
                    int roundsPlayed = PlayerSql.getRoundsPlayed(playerName);

                    if (roundsPlayed >= 5) {
                        JOptionPane.showMessageDialog(null, "You have already completed 5 rounds. You cannot play again.", "Game Over", JOptionPane.WARNING_MESSAGE);
                        dispose(); // Close login page
                        return;
                    }

                    // If player has NOT played 5 rounds, proceed normally
                    int playerId = PlayerSql.insertDetails(playerName, playerLevel);
                    System.out.println("Player ID: " + playerId);
                    messageLabel.setText("Welcome, " + playerName + " (Level: " + playerLevel + ")");

                    // Set player session details
                    PlayerSession.getInstance().setPlayerId(playerName,playerId, playerLevel);

                    // Open Player dashboard
                    new Player().setVisible(true);
                    dispose();
                }
            }
        });

    }
}
