package com.rsampdev.game;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import com.rsampdev.game.gui.LoggedIn;

public class RunGame {

	public RunGame() {
		BufferedImage cursor = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
		Cursor blank = Toolkit.getDefaultToolkit().createCustomCursor(cursor, new Point(), "blank");
		final Display game = new Display();
		final JFrame frame = new JFrame();
		frame.add(game);
		frame.setSize(Display.getGameWidth(), Display.getGameHeight());
		// frame.getContentPane().setCursor(blank);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setTitle(Display.TITLE);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);

		frame.addWindowListener(new WindowListener() {

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
				game.stop();
				frame.dispose();
				if (Display.USERNAME == Display.DEFAULT_USERNAME) {
					Display.SCORE = 0;
					Display.getLauncherInstance().getFrame().setVisible(true);
				} else {
					new LoggedIn();
				}
			}

			@Override
			public void windowClosed(WindowEvent e) {
			}

			@Override
			public void windowActivated(WindowEvent e) {
			}
		});

		game.start();
		Display.getLauncherInstance().getFrame().setVisible(false);
	}
}
