package Assignment;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The {@code QuizGame} class represents the main quiz functionality.
 * Players answer questions, and scores are tracked over multiple rounds.
 * The game consists of 5 rounds, and the player's progress is stored.
 * 
 * <p>This class interacts with {@code Manager} to retrieve questions 
 * and {@code PlayerSql} to save round scores.</p>
 * 
 * @author Kirti Kirtan Joshi
 * @version 1.0
 * @since 2025
 */
public class QuizGame extends JFrame {

    /** The main content panel for the quiz. */
    private JPanel contentPane;

    /** Label to display the current question. */
    private JLabel questionLabel;

    /** Radio buttons for multiple-choice answers. */
    private JRadioButton optionA, optionB, optionC, optionD;

    /** Button group to ensure only one option is selected at a time. */
    private ButtonGroup optionsGroup;

    /** Button to proceed to the next question. */
    private JButton nextButton;

    /** Current question index within the ResultSet. */
    private int currentQuestionIndex = 0;

    /** Score tracker for the current round. */
    private int marks = 0;

    /** The current round number (1-5). */
    private int round = 1;

    /** Maximum number of rounds in the quiz. */
    private static final int MAX_ROUNDS = 5;

    /** Stores the current set of questions retrieved from the database. */
    private ResultSet questionSet;

    /**
     * Constructs the {@code QuizGame} window.
     * Initializes GUI components and starts the quiz.
     */
    public QuizGame() {
        setTitle("Quiz");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 600, 400);

        // Initialize content panel
        contentPane = new JPanel();
        contentPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout());

        // Question Label
        questionLabel = new JLabel("Loading question...", SwingConstants.CENTER);
        questionLabel.setFont(new Font("Arial", Font.BOLD, 18));

        // Initialize radio buttons for answer choices
        optionA = new JRadioButton();
        optionB = new JRadioButton();
        optionC = new JRadioButton();
        optionD = new JRadioButton();

        // Group radio buttons to allow only one selection
        optionsGroup = new ButtonGroup();
        optionsGroup.add(optionA);
        optionsGroup.add(optionB);
        optionsGroup.add(optionC);
        optionsGroup.add(optionD);

        // Panel for answer options
        JPanel optionsPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        optionsPanel.add(optionA);
        optionsPanel.add(optionB);
        optionsPanel.add(optionC);
        optionsPanel.add(optionD);

        // Next button to proceed through quiz
        nextButton = new JButton("Next");
        nextButton.addActionListener(new ActionListener() {
            /**
             * Handles the action when the "Next" button is clicked.
             * Checks the answer and loads the next question.
             * 
             * @param e The event triggered when the button is clicked.
             */
            public void actionPerformed(ActionEvent e) {
                checkAnswer(); // Check selected answer
                loadNextQuestion(); // Load the next question
            }
        });

        // Add components to the content pane
        contentPane.add(questionLabel, BorderLayout.NORTH);
        contentPane.add(optionsPanel, BorderLayout.CENTER);
        contentPane.add(nextButton, BorderLayout.SOUTH);
        
        
        

        // Load the first question
        loadNextQuestion();
    }

    /**
     * Loads the next question from the database.
     * If there are no more questions, it proceeds to the next round.
     */
    private void loadNextQuestion() {
        try {
            PlayerSession session = PlayerSession.getInstance();
            String playerName = session.getPlayerName();
            String playerLevel = session.getPlayerlevel();
            round = session.getCurrentRound(); // Retrieve stored round number

            System.out.println("Player ID: " + playerName);
            System.out.println("Player Level: " + playerLevel);
            System.out.println("Current Round: " + round);

            // Retrieve questions only once per round
            if (questionSet == null) {
                questionSet = Manager.getQuestionByDifficulty(playerLevel);
            }

            if (questionSet.next()) {
                // Display next question and choices
                questionLabel.setText(questionSet.getString("question"));
                optionA.setText(questionSet.getString("optionA"));
                optionB.setText(questionSet.getString("optionB"));
                optionC.setText(questionSet.getString("optionC"));
                optionD.setText(questionSet.getString("optionD"));
                optionsGroup.clearSelection(); // Reset selection
                currentQuestionIndex++;
            } else {
                // Store the player's score for the round
                PlayerSql.saveRoundScore(playerName, round, marks);

                if (round < MAX_ROUNDS) {
                    // Ask if the player wants to continue to the next round
                    int choice = JOptionPane.showOptionDialog(
                        this,
                        "Round " + round + " Over! Your marks: " + marks + "\nDo you want to play the next round?",
                        "Round Over",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.INFORMATION_MESSAGE,
                        null,
                        new String[]{"Next Round", "Exit"},
                        "Next Round"
                    );

                    if (choice == JOptionPane.YES_OPTION) {
                        session.setCurrentRound(round + 1); // Increment round
                        restartQuiz();
                    } else {
                        session.setCurrentRound(round + 1); // Ensure correct round tracking
                        new Player().setVisible(true);
                        dispose();
                    }
                } else {
                    // All rounds completed
                    JOptionPane.showMessageDialog(this, "Game Over! All 5 rounds completed.");
                    session.setCurrentRound(1); // Reset session
                    System.exit(0);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks if the selected answer is correct and updates the score.
     */
    private void checkAnswer() {
        try {
            if (questionSet != null) {
                String correctAnswer = questionSet.getString("correctAnswer");
                String selectedAnswer = "";

                if (optionA.isSelected()) selectedAnswer = "Option A";
                if (optionB.isSelected()) selectedAnswer = "Option B";
                if (optionC.isSelected()) selectedAnswer = "Option C";
                if (optionD.isSelected()) selectedAnswer = "Option D";

                if (selectedAnswer.equals(correctAnswer)) {
                    marks++; // Increase score for correct answer
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Restarts the quiz for the next round.
     * Resets the question set, question index, and score.
     */
    private void restartQuiz() {
        questionSet = null;
        currentQuestionIndex = 0;
        marks = 0;
        loadNextQuestion();
    }
}
