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

public class Login extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;

	/**
	 * Launch the application.
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
	 * Create the frame.
	 */
	public Login() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setBounds(100, 100, 400, 250);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Login Page");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel.setBounds(90, 10, 111, 49);
		contentPane.add(lblNewLabel);
		
		JLabel passwordtxt = new JLabel("password");
		passwordtxt.setFont(new Font("Tahoma", Font.PLAIN, 12));
		passwordtxt.setBounds(38, 108, 79, 20);
		contentPane.add(passwordtxt);
		
		JLabel usertxt = new JLabel("user");
		usertxt.setFont(new Font("Tahoma", Font.PLAIN, 12));
		usertxt.setBounds(38, 78, 43, 20);
		contentPane.add(usertxt);
		
		textField = new JTextField();
		textField.setBounds(127, 80, 96, 19);
		contentPane.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(127, 110, 96, 19);
		contentPane.add(textField_1);
		
		JButton btnNewButton = new JButton("Login");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String user = usertxt.getText();
				String password = passwordtxt.getText();
				new AdminPanel().setVisible(true);
				dispose();
			}
		});
		btnNewButton.setBounds(90, 164, 85, 21);
		contentPane.add(btnNewButton);

		
		JButton switchButton = new JButton("Switch to Player");
		switchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new PlayerLogin().setVisible(true);
				dispose();
			}
		});
		switchButton.setBounds(249, 10, 127, 32);
		contentPane.add(switchButton);
	}
}
