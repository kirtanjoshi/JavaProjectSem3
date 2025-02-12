package Assignment;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The {@code Player} class represents the main menu for the Quiz Application.
 * It provides options to start a quiz, view player details, and check high scores.
 * 
 * <p>This class extends {@code JFrame} and contains buttons to navigate to different sections of the quiz application.</p>
 * 
 * @author Kirti Kirtan Joshi
 * @version 1.0
 * @since 2025
 */
public class Player extends JFrame {
    
    /** The main content panel for the player menu. */
    private JPanel contentPane;

    /**
     * Constructs the {@code Player} menu.
     * Initializes the GUI components, menu bar, and action listeners.
     */
    public Player() {
        setTitle("Quiz App"); // Set window title
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 600, 400); // Set window size

        // Initialize content panel
        contentPane = new JPanel();
        contentPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setContentPane(contentPane);
        contentPane.setLayout(null); // Set custom layout

        // Menu Bar
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Menu");
        JMenuItem home = new JMenuItem("Home");
        JMenuItem highScores = new JMenuItem("High Scores");
        JMenuItem playerDetails = new JMenuItem("Player Details");

        // Add menu items
        menu.add(home);
        menu.add(highScores);
        menu.add(playerDetails);
        menuBar.add(menu);
        setJMenuBar(menuBar);

        // Welcome Label
        JLabel lblNewLabel = new JLabel("Welcome to Quiz");
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblNewLabel.setBounds(206, 10, 148, 47);
        contentPane.add(lblNewLabel);

        // Start Quiz Button
        JButton startQuizButton = new JButton("Start");
        startQuizButton.setFont(new Font("Tahoma", Font.PLAIN, 12));
        startQuizButton.addActionListener(new ActionListener() {
            /**
             * Handles the action when the "Start" button is clicked.
             * Opens the {@code QuizGame} window and closes the current menu.
             *
             * @param e The event triggered when the button is clicked.
             */
            public void actionPerformed(ActionEvent e) {
                new QuizGame().setVisible(true);
                dispose(); // Close the main menu window
            }
        });
        startQuizButton.setBounds(166, 82, 222, 56);
        contentPane.add(startQuizButton);

        // Player Details Button
        JButton btnNewButton = new JButton("Player Details");
        btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 12));
        btnNewButton.addActionListener(new ActionListener() {
            /**
             * Handles the action when the "Player Details" button is clicked.
             * Opens the {@code Report} window and closes the current menu.
             *
             * @param e The event triggered when the button is clicked.
             */
            public void actionPerformed(ActionEvent e) {
                new Report().setVisible(true);
                dispose();
            }
        });
        btnNewButton.setBounds(166, 161, 222, 63);
        contentPane.add(btnNewButton);

        // High Score Button
        JButton btnNewButton_1 = new JButton("High Score");
        btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
        btnNewButton_1.setBounds(166, 257, 222, 63);
        contentPane.add(btnNewButton_1);
    }
}
