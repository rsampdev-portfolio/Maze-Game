package com.rsampdev.game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import com.rsampdev.game.entity.mob.Player;
import com.rsampdev.game.graphics.Screen;
import com.rsampdev.game.gui.Launcher;
import com.rsampdev.game.input.InputHandler;

public class Display extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;

	public static int width = 800;
	public static int height = 600;
	public static final String TITLE = "MyGame - Verison 3.6.0 - More fixing the Launcher & Scores";

	public static String DEFAULT_USERNAME = "Guest";
	public static String USERNAME = DEFAULT_USERNAME;

	public static long SCORE = 0;

	private Thread thread;
	private Screen screen;
	private Game game;
	private BufferedImage img;
	private boolean running = false;
	private int[] pixels;
	private InputHandler input;
	private int newX = 0;
	private int oldX = 0;
	private int fps;
	private int maxFPS;
	public static int selection = 0;
	public static int MouseSpeed;

	static Launcher launcher;

	public Display() {
		Dimension size = new Dimension(getGameWidth(), getGameHeight());
		setPreferredSize(size);
		setMinimumSize(size);
		setMaximumSize(size);

		screen = new Screen(getGameWidth(), getGameHeight());
		input = new InputHandler();
		game = new Game(input);
		img = new BufferedImage(getGameWidth(), getGameHeight(), BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt) img.getRaster().getDataBuffer()).getData();

		addKeyListener(input);
		addFocusListener(input);
		addMouseListener(input);
		addMouseMotionListener(input);
	}

	public static Launcher getLauncherInstance() {
		if (launcher == null) {
			launcher = new Launcher(0);
		}
		return launcher;
	}

	public static int getGameWidth() {
		return width;
	}

	public static int getGameHeight() {
		return height;
	}

	public synchronized void start() {
		if (running) {
			return;
		}
		running = true;
		thread = new Thread(this, "game");
		thread.start();
	}

	public synchronized void stop() {
		if (!running) {
			return;
		}
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	public void run() {
		long previousTime = System.nanoTime();
		double ns = 1000000000.0 / 60.0;
		double delta = 0.0;
		int frames = 0;
		int maxFrames = 0;
		int updates = 0;
		long timer = System.currentTimeMillis();
		requestFocus();
		while (running) {
			long currentTime = System.nanoTime();
			delta += (currentTime - previousTime) / ns;
			previousTime = currentTime;

			if (delta >= 1) {
				tick();
				updates++;
				delta--;
			}

			render();
			frames++;

			while (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				fps = frames;
				if (frames > maxFrames) {
					maxFrames = frames;
				}
				maxFPS = maxFrames;
				System.out.println(updates + "ups | " + fps + "fps | " + maxFrames + "MaxFPS");
				frames = 0;
				updates = 0;
			}
		}
		// render();
	}

	private void tick() {
		input.tick();
		game.tick();

		newX = InputHandler.MouseX;
		if (newX > oldX) {
			Player.turnRight = true;
		}
		if (newX < oldX) {
			Player.turnLeft = true;
		}
		if (newX == oldX) {
			Player.turnRight = false;
			Player.turnLeft = false;
		}
		MouseSpeed = Math.abs((newX * 4) - (oldX * 4));
		oldX = newX;
	}

	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}

		screen.render(game);

		for (int i = 0; i < getGameWidth() * getGameHeight(); i++) {
			pixels[i] = screen.pixels[i];
		}

		Graphics g = bs.getDrawGraphics();
		g.drawImage(img, 0, 0, getGameWidth() + 10, getGameHeight() + 10, null);
		g.setFont(new Font("Verdana", 0, 25));
		g.setColor(Color.YELLOW);
		g.drawString(fps + "-FPS | " + maxFPS + "-MaxFPS", 10, 30);
		g.drawString(USERNAME, 10, 60);
		g.drawString("" + SCORE, width - (width - 10), height - (height - 90));
		g.dispose();
		bs.show();
	}

	public static void main(String[] args) {
		Display.getLauncherInstance();
	}

}
