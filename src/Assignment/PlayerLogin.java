package Assignment;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class PlayerLogin extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField nameField;
    private JComboBox<String> difficultyBox;
    
    

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    PlayerLogin frame = new PlayerLogin();
                    frame.setVisible(true);
                    PlayerSql.playerConnection();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public PlayerLogin() {
        setTitle("Player Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 400, 250);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        String[] difficultyLevels = {"Beginner", "Intermediate", "Advance"};
        difficultyBox = new JComboBox<>(difficultyLevels);
        difficultyBox.setBounds(134, 95, 150, 25); // Set bounds for the JComboBox
        contentPane.add(difficultyBox);

        JLabel lblName = new JLabel("Player Name:");
        lblName.setBounds(24, 60, 100, 25);
        contentPane.add(lblName);

        nameField = new JTextField();
        nameField.setBounds(134, 60, 150, 25);
        contentPane.add(nameField);
        nameField.setColumns(10);

        JLabel lblLevel = new JLabel("Player Level:");
        lblLevel.setBounds(24, 95, 100, 25);
        contentPane.add(lblLevel);

        JButton btnLogin = new JButton("Login");
        btnLogin.setBounds(67, 141, 100, 30);
        contentPane.add(btnLogin);
        
        JLabel messageLabel = new JLabel("");
        messageLabel.setBounds(50, 160, 300, 25);
        contentPane.add(messageLabel);
        
        JButton switchButton = new JButton("Switch to Login");
        switchButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		new Login().setVisible(true);
        		dispose();
        	}
        });
        switchButton.setBounds(244, 10, 112, 40);
        contentPane.add(switchButton);
        
        JLabel lblNewLabel = new JLabel("Player Login");
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblNewLabel.setBounds(67, 15, 112, 26);
        contentPane.add(lblNewLabel);
        
      

        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	
 
            	
                String playerName = nameField.getText();
                String playerLevel = (String) difficultyBox.getSelectedItem();
                if (playerName.isEmpty() || playerLevel.isEmpty()) {
                    messageLabel.setText("Please enter all fields.");
                } else {
                	 int playerId = PlayerSql.insertDetails(playerName, playerLevel);
                	 System.out.print(playerId);
                    messageLabel.setText("Welcome, " + playerName + " (Level: " + playerLevel + ")");
                    PlayerSession.getInstance().setPlayerId(playerId, playerLevel);
                    
//                   System.out.print("Hello:"+session.getPlayerId());
                    new Player().setVisible(true);
                  
                    
                }
            }
        });
    }
}