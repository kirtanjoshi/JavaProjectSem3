



package Assignment;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class QuizFrame extends JFrame {
    private JPanel contentPane;
    private JLabel questionLabel;
    private JRadioButton optionA, optionB, optionC, optionD;
    private ButtonGroup optionsGroup;
    private JButton nextButton;
    private int currentQuestionId = 1;
    private int marks = 0; 
    private int round = 1; // Track current round
    private static final int MAX_ROUNDS = 5; // Player plays 5 rounds
    public QuizFrame() {
   
        setTitle("Quiz");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 600, 400);
        contentPane = new JPanel();
        contentPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout());

        // Question Label
        questionLabel = new JLabel("Loading question...", SwingConstants.CENTER);
        questionLabel.setFont(new Font("Arial", Font.BOLD, 18));

        // Radio Buttons for Options
        optionA = new JRadioButton();
        optionB = new JRadioButton();
        optionC = new JRadioButton();
        optionD = new JRadioButton();

        optionsGroup = new ButtonGroup();
        optionsGroup.add(optionA);
        optionsGroup.add(optionB);
        optionsGroup.add(optionC);
        optionsGroup.add(optionD);

        JPanel optionsPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        optionsPanel.add(optionA);
        optionsPanel.add(optionB);
        optionsPanel.add(optionC);
        optionsPanel.add(optionD);

        // Next Button
        nextButton = new JButton("Next");
        nextButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                checkAnswer(); // Check the selected answer
                loadNextQuestion(); // Load the next question
            }
        });

        // Add components to the content pane
        contentPane.add(questionLabel, BorderLayout.NORTH);
        contentPane.add(optionsPanel, BorderLayout.CENTER);
        contentPane.add(nextButton, BorderLayout.SOUTH);

        loadNextQuestion(); // Load the first question
    }


    
    
    private ResultSet questionSet; // Store question set globally
    private int currentQuestionIndex = 0; // Track question index

    private void loadNextQuestion() {
        try {
            PlayerSession session = PlayerSession.getInstance();
            int playerId = session.getPlayerId();
            String playerLevel = session.getPlayerlevel();
            round = session.getCurrentRound(); // Retrieve stored round number

            System.out.println("Player ID: " + playerId);
            System.out.println("Player Level: " + playerLevel);
            System.out.println("Current Round: " + round);

            if (questionSet == null) {
                questionSet = Manager.getQuestionByDifficulty(playerLevel);
            }

            if (questionSet.next()) {
                questionLabel.setText(questionSet.getString("question"));
                optionA.setText(questionSet.getString("optionA"));
                optionB.setText(questionSet.getString("optionB"));
                optionC.setText(questionSet.getString("optionC"));
                optionD.setText(questionSet.getString("optionD"));
                optionsGroup.clearSelection();
                currentQuestionIndex++;
            } else {
                PlayerSql.saveRoundScore(playerId, round, marks);

                if (round < MAX_ROUNDS) {
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
                        session.setCurrentRound(round + 1); // Store next round
                        restartQuiz();
                    } else {
                        session.setCurrentRound(round + 1); // Ensure next session starts at the correct round
                        new Player().setVisible(true);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Game Over! All 5 rounds completed.");
                    session.setCurrentRound(1); // Reset for the next game session
                    System.exit(0);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
//    private void loadNextQuestion() {
//    	
//    	
//    	
//        try {
//        	 PlayerSession session = PlayerSession.getInstance();
//             int playerId = session.getPlayerId();
//             String playerLevel = session.getPlayerlevel();
//        	
//        	System.out.println(playerId);
//        	System.out.println(playerLevel);
//     // Load questions only once
//            if (questionSet == null) {
//                questionSet = Manager.getQuestionByDifficulty(playerLevel);
//            }
//
//            if (questionSet.next()) {
//                questionLabel.setText(questionSet.getString("question"));
//                optionA.setText(questionSet.getString("optionA"));
//                optionB.setText(questionSet.getString("optionB"));
//                optionC.setText(questionSet.getString("optionC"));
//                optionD.setText(questionSet.getString("optionD"));
//                optionsGroup.clearSelection(); // Clear previous selection
//                currentQuestionIndex++; // Move to the next question
//            } else {
//            	
//            	System.out.println("Player Id:"+playerId);
//            	System.out.println("Player Level:"+session.getPlayerlevel());
//                // No more questions, end round
//                PlayerSql.saveRoundScore(playerId, round, marks);
//
//                if (round < MAX_ROUNDS) {
//                    int choice = JOptionPane.showOptionDialog(
//                        this,
//                        "Round " + round + " Over! Your marks: " + marks + "\nDo you want to play the next round?",
//                        "Round Over",
//                        JOptionPane.YES_NO_OPTION,
//                        JOptionPane.INFORMATION_MESSAGE,
//                        null,
//                        new String[]{"Next Round", "Exit"},
//                        "Next Round"
//                    );
//
//                    if (choice == JOptionPane.YES_OPTION) {
//                        restartQuiz(); // Move to next round
//                    } else {
//                       new Player().setVisible(true);
//                  
//                    }
//                } else {
//                    JOptionPane.showMessageDialog(this, "Game Over! All 5 rounds completed.");
//                    System.exit(0);
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }


    private void checkAnswer() {
        try {
            if (questionSet != null) {
                String correctAnswer = questionSet.getString("correctAnswer");
                String selectedAnswer = "";

                if (optionA.isSelected()) selectedAnswer = optionA.getText();
                if (optionB.isSelected()) selectedAnswer = optionB.getText();
                if (optionC.isSelected()) selectedAnswer = optionC.getText();
                if (optionD.isSelected()) selectedAnswer = optionD.getText();

                if (selectedAnswer.equals(correctAnswer)) {
                    marks++;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
    

    private void restartQuiz() {
        questionSet = null;
        currentQuestionIndex = 0;
        marks = 0;
        loadNextQuestion(); // Keep using the stored round value
    }

}
