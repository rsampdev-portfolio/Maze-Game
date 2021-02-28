package com.rsampdev.game.gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.rsampdev.game.Display;
import com.rsampdev.game.database.DatabaseConnection;

public class SignIn extends JFrame implements Runnable {

	private static final long serialVersionUID = 1L;

	private JTextField username;
	private JTextField password;

	private JButton back;

	private JLabel usernameLabel;
	private JLabel passwordLabel;

	private JButton submit;

	private int width = 250;
	private int height = 245;

	private JPanel window = new JPanel();

	public SignIn() {
		setTitle("Sign In - Game Launcher");
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
		new SignIn();
	}

	private void drawForm() {
		usernameLabel = new JLabel("Username");
		usernameLabel.setBounds(15, 65, 135, 20);
		window.add(usernameLabel);

		passwordLabel = new JLabel("Password");
		passwordLabel.setBounds(15, 95, 135, 20);
		window.add(passwordLabel);

		username = new JTextField();
		username.setBounds(85, 65, 135, 20);
		window.add(username);

		password = new JTextField();
		password.setBounds(85, 95, 135, 20);
		window.add(password);

		back = new JButton("Back");
		back.setBounds((width / 2) - (Launcher.button_width / 2), (width / 2) - (Launcher.button_width / 2) + 60, 80, 20);
		window.add(back);

		submit = new JButton("Sign in");
		submit.setBounds((width / 2) - (Launcher.button_width / 2), (width / 2) - (Launcher.button_width / 2) + 100, 80, 20);
		window.add(submit);

		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				submitButtonAction();
			}
		});
	}
	
	private void submitButtonAction() {
		String u = username.getText();
		String p = password.getText();

		List<String> data = DatabaseConnection.getData();

		if (data.contains(u) != false && data.contains(p) != false) {
			if (DatabaseConnection.verify(u, p)) {
				dispose();
				Display.USERNAME = u;
				new LoggedIn();
			}
		} else {

			if (data.contains(u) == false && data.contains(p) == false) {

				JOptionPane.showMessageDialog(null, "Your username and password do not not exist. Please enter a valid username and password");

			} else if (data.contains(u) == false) {

				JOptionPane.showMessageDialog(null, "Your username does not exist. Please enter a valid username");

			} else if (data.contains(p) == false) {

				JOptionPane.showMessageDialog(null, "Your password does not exist. Please enter a valid password");

			}
			username.setText("");
			password.setText("");
	}

}}