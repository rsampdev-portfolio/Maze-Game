package com.rsampdev.game.gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.rsampdev.game.Display;
import com.rsampdev.game.database.DatabaseConnection;

public class SignUp extends JFrame implements Runnable {

	private static final long serialVersionUID = 1L;

	private JTextField username;
	private JTextField password;

	private JLabel usernameLabel;
	private JLabel passwordLabel;
	private JButton back;

	private JButton submit;

	private int width = 250;
	private int height = 300;

	private JPanel window = new JPanel();
	
	public SignUp() {
		setTitle("Sign Up - Game Launcher");
		setSize(new Dimension(width, height));
		add(window);
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
		window.setLayout(null);
		
		drawForm();
		repaint();		
	}
	
	public void run() {
		new SignUp();
	}

	private void drawForm() {
		usernameLabel = new JLabel("Username");
		usernameLabel.setBounds(15, 50, 135, 20);
		window.add(usernameLabel);

		passwordLabel = new JLabel("Password");
		passwordLabel.setBounds(15, 80, 135, 20);
		window.add(passwordLabel);

		username = new JTextField();
		username.setBounds(85, 50, 135, 20);
		window.add(username);

		password = new JTextField();
		password.setBounds(85, 80, 135, 20);
		window.add(password);
		
		back = new JButton("Back");
		back.setBounds((width / 2) - (Launcher.button_width / 2), (width / 2) - (Launcher.button_width / 2) + 60, 80, 20);
		window.add(back);

		submit = new JButton("Submit");
		submit.setBounds((width / 2) - (Launcher.button_width / 2), 200, 80, 20);
		window.add(submit);
		
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				String u = username.getText();
				String p = password.getText();
				write(u, p);
				Display.USERNAME = username.getText();
				dispose();
				new LoggedIn();
			}
		});
	}

	private void write(String username, String passcode) {
		DatabaseConnection.write(username, passcode);
	}

}