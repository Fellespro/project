package GUI;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import kalender.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EventListener;

public class Login{
	JFrame frame;
	private JButton loginButton;
	private JButton registerButton;
	private JPasswordField passwordText;
	private JTextField userText;

	public Login(Kalender k) {
		frame = new JFrame("Logg inn");
		frame.setSize(300, 150);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new JPanel();
		frame.add(panel);
		placeComponents(panel);

		frame.setVisible(true);
		
		loginButton.addActionListener(k);
		registerButton.addActionListener(k);
	}

	private void placeComponents(JPanel panel) {

		panel.setLayout(null);

		JLabel userLabel = new JLabel("Brukernavn");
		userLabel.setBounds(10, 10, 80, 25);
		panel.add(userLabel);

		userText = new JTextField(20);
		userText.setBounds(100, 10, 160, 25);
		panel.add(userText);

		JLabel passwordLabel = new JLabel("Passord");
		passwordLabel.setBounds(10, 40, 80, 25);
		panel.add(passwordLabel);

		passwordText = new JPasswordField(20);
		passwordText.setBounds(100, 40, 160, 25);
		panel.add(passwordText);

		loginButton = new JButton("Logg inn");
		loginButton.setBounds(10, 80, 80, 25);
		panel.add(loginButton);
		
		registerButton = new JButton("Cancel");
		registerButton.setBounds(180, 80, 80, 25);
		panel.add(registerButton);
	}

	public String getBrukernavn() {
		return userText.getText();
	}

	@SuppressWarnings("deprecation")
	public String getPassord() {
		return passwordText.getText();
	}

	public void lukk() {
		frame.setVisible(false);
		frame.removeAll();
	}

}
