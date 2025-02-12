package Assignment;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * The {@code Login} class represents a login form where users can enter their
 * username and password. It also provides an option to switch to the Player login page.
 * 
 * <p>This class extends {@code JFrame} and contains GUI components such as 
 * text fields for user input and buttons for authentication and navigation.</p>
 * 
 * @author Kirti Kirtan Joshi
 * @version 1.0
 * @since 2025
 */
public class Login extends JFrame {

    private static final long serialVersionUID = 1L;

    /** The main content panel for the login frame. */
    private JPanel contentPane;

    /** Text field for entering the username. */
    private JTextField textField;

    /** Text field for entering the password. */
    private JTextField textField_1;

    /**
     * Launches the application.
     * This method starts the GUI in the Event Dispatch Thread.
     * 
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Login frame = new Login();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Constructs the {@code Login} frame.
     * Initializes the GUI components and sets up event listeners.
     */
    public Login() {
        // Set frame properties
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 400, 250);

        // Initialize content panel
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // Title label
        JLabel lblNewLabel = new JLabel("Login Page");
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblNewLabel.setBounds(90, 10, 111, 49);
        contentPane.add(lblNewLabel);

        // Username label
        JLabel usertxt = new JLabel("User");
        usertxt.setFont(new Font("Tahoma", Font.PLAIN, 12));
        usertxt.setBounds(38, 78, 43, 20);
        contentPane.add(usertxt);

        // Password label
        JLabel passwordtxt = new JLabel("Password");
        passwordtxt.setFont(new Font("Tahoma", Font.PLAIN, 12));
        passwordtxt.setBounds(38, 108, 79, 20);
        contentPane.add(passwordtxt);

        // Username text field
        textField = new JTextField();
        textField.setBounds(127, 80, 96, 19);
        contentPane.add(textField);
        textField.setColumns(10);

        // Password text field
        textField_1 = new JTextField();
        textField_1.setColumns(10);
        textField_1.setBounds(127, 110, 96, 19);
        contentPane.add(textField_1);

        // Login button
        JButton btnNewButton = new JButton("Login");
        btnNewButton.addActionListener(new ActionListener() {
            /**
             * Handles the login action. 
             * When clicked, it verifies the credentials (not implemented) 
             * and opens the {@code Dashboard} window.
             * 
             * @param e The event triggered when the button is clicked.
             */
            public void actionPerformed(ActionEvent e) {
                String user = textField.getText();
                String password = textField_1.getText();
                
                // TODO: Implement proper authentication logic here

                // Open Dashboard window
                new Dashboard().setVisible(true);
                dispose(); // Close login window
            }
        });
        btnNewButton.setBounds(90, 164, 85, 21);
        contentPane.add(btnNewButton);

        // Switch to Player Login button
        JButton switchButton = new JButton("Switch to Player");
        switchButton.addActionListener(new ActionListener() {
            /**
             * Handles the switch action.
             * When clicked, it opens the {@code PlayerLogin} window.
             * 
             * @param e The event triggered when the button is clicked.
             */
            public void actionPerformed(ActionEvent e) {
                new PlayerLogin().setVisible(true);
                dispose(); // Close current login window
            }
        });
        switchButton.setBounds(249, 10, 127, 32);
        contentPane.add(switchButton);
    }
}
