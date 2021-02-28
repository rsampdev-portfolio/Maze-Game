package com.rsampdev.game.input;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class InputHandler implements KeyListener, FocusListener, MouseListener, MouseMotionListener {

	public boolean[] key = new boolean[68836];
	public static int MouseX;
	public static int MouseY;
	public static int MouseDX;
	public static int MouseDY;
	public static int MousePX;
	public static int MousePY;
	public static int MouseButton;
	public static boolean dragged = false;

	public boolean forward;
	public boolean back;
	public boolean left;
	public boolean right;
	public boolean rLeft;
	public boolean rRight;
	public boolean jump;
	public boolean crouch;
	public boolean run;
	public boolean escape;

	public void tick() {
		forward = key[KeyEvent.VK_W] || key[KeyEvent.VK_I];
		back = key[KeyEvent.VK_S] || key[KeyEvent.VK_K];
		left = key[KeyEvent.VK_A] || key[KeyEvent.VK_J];
		right = key[KeyEvent.VK_D] || key[KeyEvent.VK_L];
		rLeft = key[KeyEvent.VK_LEFT] || key[KeyEvent.VK_Q] || key[KeyEvent.VK_U];
		rRight = key[KeyEvent.VK_RIGHT]  || key[KeyEvent.VK_E] || key[KeyEvent.VK_O];
		jump = key[KeyEvent.VK_SPACE];
		crouch = key[KeyEvent.VK_ALT];
		run = key[KeyEvent.VK_SHIFT];
		escape = key[KeyEvent.VK_ESCAPE]; // make this pause the game not stop the entire thread
	}

	public void mouseDragged(MouseEvent e) {
		dragged = true;
		MouseDX = e.getX();
		MouseDY = e.getY();
	}

	public void mouseMoved(MouseEvent e) {
		MouseX = e.getX();
		MouseY = e.getY();
	}

	public void mouseClicked(MouseEvent e) {
		MouseButton = 0;
	}

	public void mousePressed(MouseEvent e) {
		MouseButton = e.getButton();
		MousePX = e.getX();
		MousePY = e.getY();
	}

	public void mouseReleased(MouseEvent e) {
		dragged = false;
		MouseButton = 0;
	}

	public void mouseEntered(MouseEvent e) {

	}

	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void focusGained(FocusEvent e) {

	}

	@Override
	public void focusLost(FocusEvent e) {
		for (int i = 0; i < key.length; i++) {
			key[i] = false;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if (keyCode > 0 && keyCode < key.length) {
			key[keyCode] = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if (keyCode > 0 && keyCode < key.length) {
			key[keyCode] = false;
		}
	}

}
