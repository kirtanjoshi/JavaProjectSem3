
package Assignment;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminPanel extends JFrame {

    private JTextField questionField;
    private JTextField optionAField;
    private JTextField optionBField;
    private JTextField optionCField;
    private JTextField optionDField;
    private JComboBox<String> difficultyBox;
    private JTable questionTable;
    private DefaultTableModel tableModel;

    public AdminPanel() {
        setTitle("Admin Panel - Quiz Manager");
        setSize(800, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setLayout(new BorderLayout());

        // Panel for adding questions
        JPanel inputPanel = new JPanel();
        inputPanel.setBorder(BorderFactory.createTitledBorder("Add New Question"));

        questionField = new JTextField();
        questionField.setBounds(290, 19, 254, 29);
        optionAField = new JTextField();
        optionAField.setBounds(290, 98, 254, 29);
        optionBField = new JTextField();
        optionBField.setBounds(290, 59,  254, 29);
        optionCField = new JTextField();
        optionCField.setBounds(290, 137, 254, 29);
        optionDField = new JTextField();
        optionDField.setBounds(290, 186, 254, 29);
        
        // Difficulty Selection
        String[] difficultyLevels = {"Easy", "Intermediate", "Hard"};
        difficultyBox = new JComboBox<>(difficultyLevels);
        difficultyBox.setBounds(290, 236, 254, 40);
        inputPanel.setLayout(null);

        // Labels and inputs
        JLabel label = new JLabel("Question:");
        label.setBounds(7, 18, 254, 40);
        inputPanel.add(label);
        inputPanel.add(questionField);
        JLabel label_1 = new JLabel("Option A:");
        label_1.setBounds(7, 53, 254, 40);
        inputPanel.add(label_1);
        inputPanel.add(optionAField);
        JLabel label_2 = new JLabel("Option B:");
        label_2.setBounds(7, 92, 254, 40);
        inputPanel.add(label_2);
        inputPanel.add(optionBField);
        JLabel label_3 = new JLabel("Option C:");
        label_3.setBounds(7, 131, 254, 40);
        inputPanel.add(label_3);
        inputPanel.add(optionCField);
        JLabel label_4 = new JLabel("Option D:");
        label_4.setBounds(7, 181, 254, 40);
        inputPanel.add(label_4);
        inputPanel.add(optionDField);
        JLabel label_5 = new JLabel("Difficulty:");
        label_5.setBounds(7, 236, 254, 40);
        inputPanel.add(label_5);
        inputPanel.add(difficultyBox);

        // Buttons for actions
        JButton addButton = new JButton("Add Question");
        addButton.setBounds(582, 18, 181, 40);
        JButton updateButton = new JButton("Update Question");
        updateButton.setBounds(580, 92, 183, 40);
        JButton deleteButton = new JButton("Delete Question");
        deleteButton.setBounds(582, 151, 181, 40);

        inputPanel.add(addButton);
        inputPanel.add(updateButton);
        inputPanel.add(deleteButton);

        // Table for displaying questions
        String[] columnNames = {"ID", "Difficulty","Question", "Option A", "Option B", "Option C", "Option D"};
        tableModel = new DefaultTableModel(columnNames, 0);
        questionTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(questionTable);

        // Load existing questions
        loadQuestions();

        // Button Listeners
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addQuestion();
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateQuestion();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteQuestion();
            }
        });

        // Add components to frame
        getContentPane().add(inputPanel, BorderLayout.CENTER);
        getContentPane().add(tableScrollPane, BorderLayout.SOUTH);
    }

    private void loadQuestions() {
        tableModel.setRowCount(0); // Clear table before loading
        Manager.loadQuestions(tableModel);
    }

    private void addQuestion() {
        String question = questionField.getText();
        String optionA = optionAField.getText();
        String optionB = optionBField.getText();
        String optionC = optionCField.getText();
        String optionD = optionDField.getText();
        String difficulty = (String) difficultyBox.getSelectedItem();

        if (question.isEmpty() || optionA.isEmpty() || optionB.isEmpty() || optionC.isEmpty() || optionD.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Manager.addQuestion(difficulty,question, optionA, optionB, optionC, optionD);
        loadQuestions(); // Refresh table
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

        if (question.isEmpty() || optionA.isEmpty() || optionB.isEmpty() || optionC.isEmpty() || optionD.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Manager.updateQuestion(id, difficulty, question, optionA, optionB, optionC, optionD);
        loadQuestions(); // Refresh table
        clearFields();
    }

//    private void deleteQuestion() {
//        int selectedRow = questionTable.getSelectedRow();
//        if (selectedRow == -1) {
//            JOptionPane.showMessageDialog(this, "Select a question to delete!", "Error", JOptionPane.ERROR_MESSAGE);
//            return;
//        }
//
//        int id = (int) tableModel.getValueAt(selectedRow, 0);
//
//        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this question?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
//        if (confirm == JOptionPane.YES_OPTION) {
//            Manager.deleteQuestion(id);
//            loadQuestions(); // Refresh table
//        }
//    }
    private void deleteQuestion() {
        int selectedRow = questionTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Select a question to delete!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int id = (int) tableModel.getValueAt(selectedRow, 0);

        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this question?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            Manager.deleteQuestion(id);
            loadQuestions(); // Refresh table

            // Re-fetch questions to update UI properly
            tableModel.setRowCount(0);
            loadQuestions();
        }
    }


    private void clearFields() {
        questionField.setText("");
        optionAField.setText("");
        optionBField.setText("");
        optionCField.setText("");
        optionDField.setText("");
        difficultyBox.setSelectedIndex(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AdminPanel adminPanel = new AdminPanel();
            adminPanel.setVisible(true);
        });
        Manager.connection();
    }
}

