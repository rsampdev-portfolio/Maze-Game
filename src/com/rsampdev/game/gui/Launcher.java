package com.rsampdev.game.gui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferStrategy;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;

import com.rsampdev.game.Configuration;
import com.rsampdev.game.Display;
import com.rsampdev.game.RunGame;
import com.rsampdev.game.input.InputHandler;

public class Launcher extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;

	protected JPanel window = new JPanel();
	private JButton play;
	private JButton options;
	private JButton signIn;
	private JButton signUp;
	private JButton help;
	private JButton quit;
	private Rectangle rPlay;
	private Rectangle rOptions;
	private Rectangle rSignIn;
	private Rectangle rSignUp;
	private Rectangle rHelp;
	private Rectangle rQuit;

	Configuration config = new Configuration();

	private int width = 800;
	private int height = 400;
	public static final int button_width = 80;
	public static final int button_height = 40;
	public boolean running = false;
	Thread thread;
	private JFrame frame = new JFrame();

	public Launcher(int id) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		frame.setUndecorated(true);
		frame.setTitle("Game Launcher");
		frame.setSize(new Dimension(width, height));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// frame.getContentPane().add(window);
		frame.add(this);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);
		window.setLayout(null);
		if (id == 0) {
			drawButtons();
		}
		frame.addWindowListener(new WindowListener() {
			
			@Override
			public void windowOpened(WindowEvent e) {
			}
			
			@Override
			public void windowIconified(WindowEvent e) {
				Display.getLauncherInstance().stopMenu();
			}
			
			@Override
			public void windowDeiconified(WindowEvent e) {
				Display.getLauncherInstance().startMenu();
			}
			
			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		InputHandler input = new InputHandler();
		addKeyListener(input);
		addFocusListener(input);
		addMouseListener(input);
		addMouseMotionListener(input);
		startMenu();
		frame.repaint();
	}

	public void updateFrame() {
		if (InputHandler.dragged == true) {
			InputHandler.MouseButton = 0;
			Point p = frame.getLocation();
			frame.setLocation(p.x + InputHandler.MouseDX - InputHandler.MousePX, p.y + InputHandler.MouseDY - InputHandler.MousePY);
			InputHandler.dragged = false;
		}
	}

	public void startMenu() {
		running = true;
		thread = new Thread(this, "menu");
		thread.start();
	}

	public void stopMenu() {
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		frame.requestFocus();
		while (running) {
			try {
				renderMenu();
			} catch (IllegalStateException e) {
				System.out.println("handled");
			}
			updateFrame();
		}
	}

	private void renderMenu() throws IllegalStateException {
		InputHandler.MouseButton = 0;
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}

		Graphics g = bs.getDrawGraphics();
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 800, 400);
		try {
			g.drawImage(ImageIO.read(Launcher.class.getResource("/menu/menu res/menu_background.png")), 0, 0, 800, 400, null);

			if (InputHandler.MouseX > 650 && InputHandler.MouseX < (650 + 100) && InputHandler.MouseY > 20 && InputHandler.MouseY < 20 + 60) {
				g.drawImage(ImageIO.read(Launcher.class.getResource("/menu/menu res/buttons/play/play_on.png")), 650, 20, 100, 60, null);
				g.drawImage(ImageIO.read(Launcher.class.getResource("/menu/menu res/arrows/leftArrow.png")), 760, 40, 22, 20, null);
				g.drawImage(ImageIO.read(Launcher.class.getResource("/menu/menu res/arrows/rightArrow.png")), 618, 40, 22, 20, null);
				if (InputHandler.MouseButton == 1) {
					InputHandler.MouseButton = 0;
					config.loadConfiguration("res/settings/config.xml");
//					frame.dispose();
					new RunGame();
				}
			} else {
				g.drawImage(ImageIO.read(Launcher.class.getResource("/menu/menu res/buttons/play/play_off.png")), 650, 20, 100, 60, null);
			}
			if (InputHandler.MouseX > 650 && InputHandler.MouseX < (650 + 100) && InputHandler.MouseY > 80 && InputHandler.MouseY < 80 + 60) {
				g.drawImage(ImageIO.read(Launcher.class.getResource("/menu/menu res/buttons/signIn/signIn_on.png")), 650, 80, 100, 60, null);
				g.drawImage(ImageIO.read(Launcher.class.getResource("/menu/menu res/arrows/leftArrow.png")), 760, 100, 22, 20, null);
				g.drawImage(ImageIO.read(Launcher.class.getResource("/menu/menu res/arrows/rightArrow.png")), 618, 100, 22, 20, null);
				if (InputHandler.MouseButton == 1) {
					InputHandler.MouseButton = 0;
					new SignIn();
				}
			} else {
				g.drawImage(ImageIO.read(Launcher.class.getResource("/menu/menu res/buttons/signIn/signIn_off.png")), 650, 80, 100, 60, null);
			}
			if (InputHandler.MouseX > 650 && InputHandler.MouseX < (650 + 100) && InputHandler.MouseY > 140 && InputHandler.MouseY < 140 + 60) {
				g.drawImage(ImageIO.read(Launcher.class.getResource("/menu/menu res/buttons/signUp/signUp_on.png")), 650, 140, 100, 60, null);
				g.drawImage(ImageIO.read(Launcher.class.getResource("/menu/menu res/arrows/leftArrow.png")), 760, 160, 22, 20, null);
				g.drawImage(ImageIO.read(Launcher.class.getResource("/menu/menu res/arrows/rightArrow.png")), 618, 160, 22, 20, null);
				if (InputHandler.MouseButton == 1) {
					InputHandler.MouseButton = 0;
					new SignUp();
				}
			} else {
				g.drawImage(ImageIO.read(Launcher.class.getResource("/menu/menu res/buttons/signUp/signUp_off.png")), 650, 140, 100, 60, null);
			}
			if (InputHandler.MouseX > 650 && InputHandler.MouseX < (650 + 100) && InputHandler.MouseY > 200 && InputHandler.MouseY < 200 + 60) {
				g.drawImage(ImageIO.read(Launcher.class.getResource("/menu/menu res/buttons/options/options_on.png")), 650, 200, 100, 60, null);
				g.drawImage(ImageIO.read(Launcher.class.getResource("/menu/menu res/arrows/leftArrow.png")), 760, 220, 22, 20, null);
				g.drawImage(ImageIO.read(Launcher.class.getResource("/menu/menu res/arrows/rightArrow.png")), 618, 220, 22, 20, null);
				if (InputHandler.MouseButton == 1) {
					InputHandler.MouseButton = 0;
					new Options(0);
				}
			} else {
				g.drawImage(ImageIO.read(Launcher.class.getResource("/menu/menu res/buttons/options/options_off.png")), 650, 200, 100, 60, null);
			}
			if (InputHandler.MouseX > 650 && InputHandler.MouseX < (650 + 100) && InputHandler.MouseY > 260 && InputHandler.MouseY < 260 + 60) {
				g.drawImage(ImageIO.read(Launcher.class.getResource("/menu/menu res/buttons/help/help_on.png")), 650, 260, 100, 60, null);
				g.drawImage(ImageIO.read(Launcher.class.getResource("/menu/menu res/arrows/leftArrow.png")), 760, 280, 22, 20, null);
				g.drawImage(ImageIO.read(Launcher.class.getResource("/menu/menu res/arrows/rightArrow.png")), 618, 280, 22, 20, null);
				if (InputHandler.MouseButton == 1) {
					InputHandler.MouseButton = 0;
					System.out.println("Help");
				}
			} else {
				g.drawImage(ImageIO.read(Launcher.class.getResource("/menu/menu res/buttons/help/help_off.png")), 650, 260, 100, 60, null);
			}
			if (InputHandler.MouseX > 650 && InputHandler.MouseX < (650 + 100) && InputHandler.MouseY > 320 && InputHandler.MouseY < 320 + 60) {
				g.drawImage(ImageIO.read(Launcher.class.getResource("/menu/menu res/buttons/exit/exit_on.png")), 650, 320, 100, 60, null);
				g.drawImage(ImageIO.read(Launcher.class.getResource("/menu/menu res/arrows/leftArrow.png")), 760, 340, 22, 20, null);
				g.drawImage(ImageIO.read(Launcher.class.getResource("/menu/menu res/arrows/rightArrow.png")), 618, 340, 22, 20, null);
				if (InputHandler.MouseButton == 1) {
					InputHandler.MouseButton = 0;
					System.exit(0);
				}
			} else {
				g.drawImage(ImageIO.read(Launcher.class.getResource("/menu/menu res/buttons/exit/exit_off.png")), 650, 320, 100, 60, null);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		g.dispose();
		bs.show();
	}

	private void drawButtons() {
		play = new JButton("Play");
		rPlay = new Rectangle((width / 2) - (button_width / 2), 90, button_width, button_height);
		play.setBounds(rPlay);
		window.add(play);

		options = new JButton("Options");
		rOptions = new Rectangle((width / 2) - (button_width / 2), 140, button_width, button_height);
		options.setBounds(rOptions);
		window.add(options);

		signIn = new JButton("Sign In");
		rSignIn = new Rectangle((width / 2) - (button_width / 2), 190, button_width, button_height);
		signIn.setBounds(rSignIn);
		window.add(signIn);

		signUp = new JButton("Sign Up");
		rSignUp = new Rectangle((width / 2) - (button_width / 2), 240, button_width, button_height);
		signUp.setBounds(rSignUp);
		window.add(signUp);

		help = new JButton("Help");
		rHelp = new Rectangle((width / 2) - (button_width / 2), 290, button_width, button_height);
		help.setBounds(rHelp);
		window.add(help);

		quit = new JButton("Quit");
		rQuit = new Rectangle((width / 2) - (button_width / 2), 340, button_width, button_height);
		quit.setBounds(rQuit);
		window.add(quit);

		play.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				config.loadConfiguration("res/settings/config.xml");
				frame.dispose();
				Display.getLauncherInstance().getFrame().setVisible(false);
				new RunGame();
			}
		});
		options.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				new Options(0);
			}
		});
		signIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				new SignIn();
			}
		});
		signUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				new SignUp();
			}
		});
		help.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showConfirmDialog(null, thread);
			}
		});
		quit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
	}

	public JFrame getFrame() {
		return frame;
	}

}
