
package Assignment;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminPanel extends JFrame {
    private JTextField questionField, optionAField, optionBField, optionCField, optionDField;
    private JComboBox<String> difficultyBox, correctAnswerBox;
    private JTable questionTable;
    private DefaultTableModel tableModel;
    private JButton leaderBoard;

    public AdminPanel() {
        setTitle("Admin Panel - Quiz Manager");
        setSize(850, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.setBorder(BorderFactory.createTitledBorder("Add New Question"));
        inputPanel.setLayout(new GridLayout(7, 2, 5, 5));

        questionField = new JTextField();
        optionAField = new JTextField();
        optionBField = new JTextField();
        optionCField = new JTextField();
        optionDField = new JTextField();

        String[] difficultyLevels = {"Easy", "Intermediate", "Hard"};
        difficultyBox = new JComboBox<>(difficultyLevels);

        String[] options = {"Option A", "Option B", "Option C", "Option D"};
        correctAnswerBox = new JComboBox<>(options);

        inputPanel.add(new JLabel("Question:"));
        inputPanel.add(questionField);
        inputPanel.add(new JLabel("Option A:"));
        inputPanel.add(optionAField);
        inputPanel.add(new JLabel("Option B:"));
        inputPanel.add(optionBField);
        inputPanel.add(new JLabel("Option C:"));
        inputPanel.add(optionCField);
        inputPanel.add(new JLabel("Option D:"));
        inputPanel.add(optionDField);
        inputPanel.add(new JLabel("Difficulty:"));
        inputPanel.add(difficultyBox);
        inputPanel.add(new JLabel("Correct Answer:"));
        inputPanel.add(correctAnswerBox);

        JButton addButton = new JButton("Add Question");
        addButton.setBounds(205, 5, 93, 21);
        JButton updateButton = new JButton("Update Question");
        updateButton.setBounds(303, 5, 107, 21);
        JButton deleteButton = new JButton("Delete Question");
        deleteButton.setBounds(415, 5, 105, 21);

        addButton.addActionListener(e -> addQuestion());
        updateButton.addActionListener(e -> updateQuestion());
        deleteButton.addActionListener(e -> deleteQuestion());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(null);
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);

        String[] columnNames = {"ID", "Difficulty", "Question", "Option A", "Option B", "Option C", "Option D", "Correct Answer"};
        tableModel = new DefaultTableModel(columnNames, 0);
        questionTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(questionTable);

        getContentPane().add(inputPanel, BorderLayout.NORTH);
        getContentPane().add(buttonPanel, BorderLayout.CENTER);
        
        leaderBoard = new JButton("LeaderBoard");
        leaderBoard.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		new Report().setVisible(true);
        	}
        });
        leaderBoard.setBounds(525, 5, 105, 21);
        buttonPanel.add(leaderBoard);
        getContentPane().add(tableScrollPane, BorderLayout.SOUTH);

        loadQuestions();
    }

    private void loadQuestions() {
        tableModel.setRowCount(0);
        Manager.loadQuestions(tableModel);
    }

    private void addQuestion() {
        String question = questionField.getText();
        String optionA = optionAField.getText();
        String optionB = optionBField.getText();
        String optionC = optionCField.getText();
        String optionD = optionDField.getText();
        String difficulty = (String) difficultyBox.getSelectedItem();
        String correctAnswer = (String) correctAnswerBox.getSelectedItem();

        if (question.isEmpty() || optionA.isEmpty() || optionB.isEmpty() || optionC.isEmpty() || optionD.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Manager.addQuestion(difficulty, question, optionA, optionB, optionC, optionD, correctAnswer);
        loadQuestions();
        clearFields();
    }

    private void updateQuestion() {
        int selectedRow = questionTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Select a question to update!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int id = (int) tableModel.getValueAt(selectedRow, 0);
        String question = questionField.getText();
        String optionA = optionAField.getText();
        String optionB = optionBField.getText();
        String optionC = optionCField.getText();
        String optionD = optionDField.getText();
        String difficulty = (String) difficultyBox.getSelectedItem();
        String correctAnswer = (String) correctAnswerBox.getSelectedItem();

        Manager.updateQuestion(id, difficulty, question, optionA, optionB, optionC, optionD, correctAnswer);
        loadQuestions();
        clearFields();
    }

    private void deleteQuestion() {
        int selectedRow = questionTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Select a question to delete!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int id = (int) tableModel.getValueAt(selectedRow, 0);
        Manager.deleteQuestion(id);
        loadQuestions();
    }

    private void clearFields() {
        questionField.setText("");
        optionAField.setText("");
        optionBField.setText("");
        optionCField.setText("");
        optionDField.setText("");
        difficultyBox.setSelectedIndex(0);
        correctAnswerBox.setSelectedIndex(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AdminPanel().setVisible(true));
        Manager.connection();
    }
}

