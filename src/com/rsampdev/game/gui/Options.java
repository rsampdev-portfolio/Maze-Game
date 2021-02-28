package com.rsampdev.game.gui;

import java.awt.Choice;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.rsampdev.game.Configuration;

public class Options extends JFrame implements Runnable {

	private static final long serialVersionUID = 1L;

	private int width = 550;
	private int height = 450;

	private JButton ok;
	private Rectangle rOk;
	private JTextField textWidth;
	private JTextField textHeight;
	private JLabel labelWidth;
	private JLabel labelHeight;
	private Choice resolution = new Choice();
	private Rectangle rResolution;
	Configuration config = new Configuration();

	int w = 0;
	int h = 0;

	JPanel window = new JPanel();
	
	private int id = 0;

	public Options(int id) {
		setTitle("Options - Game Launcher");
		setSize(new Dimension(width, height));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		add(window);
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
		window.setLayout(null);
		this.id = id;

		drawButtons();
		repaint();
	}
	
	public void run() {
		new Options(0);
	}

	private void drawButtons() {
		ok = new JButton("Ok");
		rOk = new Rectangle((width - 100), (height - 70), Launcher.button_width, Launcher.button_height - 10);
		ok.setBounds(rOk);
		window.add(ok);

		rResolution = new Rectangle(50, 80, 120, 25);
		resolution.setBounds(rResolution);
		resolution.add("640, 480");
		resolution.add("800, 600");
		resolution.add("1024, 768");
		resolution.add("1280, 810");
		resolution.select(1);
		window.add(resolution);

		labelWidth = new JLabel("Width: ");
		labelWidth.setBounds(30, 150, 120, 20);
		window.add(labelWidth);

		labelHeight = new JLabel("Height: ");
		labelHeight.setBounds(30, 180, 120, 20);
		window.add(labelHeight);

		textWidth = new JTextField();
		textWidth.setBounds(80, 150, 60, 20);
		window.add(textWidth);

		textHeight = new JTextField();
		textHeight.setBounds(80, 180, 60, 20);
		window.add(textHeight);

		ok.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				config.saveConfiguration("width", parseWidth());
				config.saveConfiguration("height", parseHeight());
				if (id == 1) {
					new LoggedIn();
				}
			}
		});
	}

	private void drop() {
		int selection = resolution.getSelectedIndex();
		if (selection == 0) {
			w = 640;
			h = 480;
		}
		if (selection == 1 || selection == -1) {
			w = 800;
			h = 600;
		}
		if (selection == 2) {
			w = 1024;
			h = 768;
		}
		if (selection == 3) {
			w = 1280;
			h = 810;
		}

	}

	private int parseWidth() {
		try {
			int w = Integer.parseInt(textWidth.getText());
			return w;
		} catch (NumberFormatException e) {
			drop();
			return w;
		}
	}

	private int parseHeight() {
		try {
			int h = Integer.parseInt(textHeight.getText());
			return h;
		} catch (NumberFormatException e) {
			drop();
			return h;
		}
	}

}
