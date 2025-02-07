package Assignment;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Player extends JFrame {
    private JPanel contentPane;


//    public static void main(String[] args) {
//        EventQueue.invokeLater(() -> {
//            try {
////                Player frame = new Player();
////                frame.setVisible(true);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        });
//    }

    public Player() {
        setTitle("Quiz App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 600, 400);
        contentPane = new JPanel();
        contentPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setContentPane(contentPane);

        // Menu Bar
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Menu");
        JMenuItem home = new JMenuItem("Home");
        JMenuItem highScores = new JMenuItem("High Scores");
        JMenuItem playerDetails = new JMenuItem("Player Details");
        
        menu.add(home);
        menu.add(highScores);
        menu.add(playerDetails);
        menuBar.add(menu);
        setJMenuBar(menuBar);
        contentPane.setLayout(null);
        
        JLabel lblNewLabel = new JLabel("Welcome to Quiz");
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblNewLabel.setBounds(206, 10, 148, 47);
        contentPane.add(lblNewLabel);
        
        JButton startQuizButton = new JButton("Start ");
        startQuizButton.setFont(new Font("Tahoma", Font.PLAIN, 12));
        startQuizButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new QuizFrame().setVisible(true);
                dispose(); // Close the main menu window
            }
        });
        startQuizButton.setBounds(166, 82, 222, 56);
        contentPane.add(startQuizButton);
        
        JButton btnNewButton = new JButton("Player Details");
        btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 12));
        btnNewButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		new Report().setVisible(true);
        		dispose();
        	}
        });
        btnNewButton.setBounds(166, 161, 222, 63);
        contentPane.add(btnNewButton);
        
        JButton btnNewButton_1 = new JButton("High Score");
        btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
        btnNewButton_1.setBounds(166, 257, 222, 63);
        contentPane.add(btnNewButton_1);
        
        
        
    }
}
