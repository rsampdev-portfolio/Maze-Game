package com.rsampdev.game.gui;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;

import com.rsampdev.game.Configuration;
import com.rsampdev.game.Display;
import com.rsampdev.game.RunGame;

public class LoggedIn extends JFrame implements Runnable {

	private static final long serialVersionUID = 1L;

	protected JPanel window = new JPanel();
	private JButton play;
	private JButton options;
	private JButton signOut;
	private JButton help;

	private Rectangle rPlay;
	private Rectangle rOptions;
	private Rectangle rSignOut;
	private Rectangle rHelp;

	Configuration config = new Configuration();

	private int width = 240;
	private int height = 380;
	public static final int buttun_width = 80;
	public static final int buttun_height = 40;

	public LoggedIn() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}

		setTitle("Game Launcher");
		setSize(new Dimension(width, height));
		// setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().add(window);
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
		window.setLayout(null);

		addWindowListener(new WindowListener() {

			@Override
			public void windowOpened(WindowEvent e) {
			}

			@Override
			public void windowIconified(WindowEvent e) {
			}

			@Override
			public void windowDeiconified(WindowEvent e) {
			}

			@Override
			public void windowDeactivated(WindowEvent e) {
			}

			@Override
			public void windowClosing(WindowEvent e) {
				setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				dispose();
				if (Display.USERNAME == Display.DEFAULT_USERNAME) {
					Display.SCORE = 0;
				}
				Display.getLauncherInstance();
			}

			@Override
			public void windowClosed(WindowEvent e) {
			}

			@Override
			public void windowActivated(WindowEvent e) {
			}
		});
		drawButtons();
		repaint();

		Display.getLauncherInstance().getFrame().setVisible(false);
	}

	public void run() {
		new LoggedIn();
	}

	private void drawButtons() {
		play = new JButton("Play");
		rPlay = new Rectangle((width / 2) - (buttun_width / 2), 90, buttun_width, buttun_height);
		play.setBounds(rPlay);
		window.add(play);

		options = new JButton("Options");
		rOptions = new Rectangle((width / 2) - (buttun_width / 2), 140, buttun_width, buttun_height);
		options.setBounds(rOptions);
		window.add(options);

		signOut = new JButton("Sign Out");
		rSignOut = new Rectangle((width / 2) - (buttun_width / 2), 190, buttun_width, buttun_height);
		signOut.setBounds(rSignOut);
		window.add(signOut);

		help = new JButton("Help");
		rHelp = new Rectangle((width / 2) - (buttun_width / 2), 240, buttun_width, buttun_height);
		help.setBounds(rHelp);
		window.add(help);

		play.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				dispose();
				config.loadConfiguration("res/settings/config.xml");
				new RunGame();
			}
		});
		options.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				new Options(1);
			}
		});
		signOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				Display.USERNAME = Display.DEFAULT_USERNAME;
				Display.getLauncherInstance().getFrame().setVisible(true);
				Display.getLauncherInstance();

			}
		});
		help.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showConfirmDialog(null, "Coming Soon");
			}
		});
	}
}
