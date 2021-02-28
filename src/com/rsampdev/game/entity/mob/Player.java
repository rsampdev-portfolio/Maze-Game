package com.rsampdev.game.entity.mob;

import com.rsampdev.game.input.InputHandler;

public class Player extends Mob {

	public double y;
	public double rotation;
	public double xa;
	public double za;
	public double rotationa;

	public static boolean turnLeft = false;
	public static boolean turnRight = false;
	public static boolean walk = false;
	public static boolean crouchWalk = false;
	public static boolean runWalk = false;
	private InputHandler input;

	public Player(InputHandler input) {
		this.input = input;
	}

	public void tick() {
		// double rotationSpeed = 0.032;
		double rotationSpeed = 0.10;
		double walkSpeed = 0.6;
		double jumpHeight = 0.5;
		double crouchHeight = 0.3;
		// double xMove = 0;
		// double zMove = 0;
		double xa = 0;
		double za = 0;

		if (input.crouch && input.run) {
			walkSpeed = 0.5;
			input.run = false;
			input.crouch = false;
			runWalk = false;
			crouchWalk = false;
		}

		if (input.crouch) {
			y -= crouchHeight;
			walkSpeed = 0.4;
			input.run = false;
			crouchWalk = true;
		}

		if (input.run) {
			walkSpeed = 1.2;
			input.run = true;
			crouchWalk = false;
		}

		if (input.forward) {
			za += walkSpeed;
			walk = true;
		}

		if (input.back) {
			walkSpeed = 0.4;
			za -= walkSpeed;
			walk = true;
		}

		if (input.left) {
			xa -= walkSpeed;
			walk = true;
		}

		if (input.right) {
			xa += walkSpeed;
			walk = true;
		}

		if (input.rLeft) {
			rotation -= rotationSpeed;
		}

		if (input.rRight) {
			rotation += rotationSpeed;
		}

		if (input.jump) {
			y += jumpHeight;
			input.run = false;

			if (y == jumpHeight) {
				while (y != 0) {
					y *= 0.9;		
				}
			}
		}

		if (runWalk && input.forward) {
			walk = true;
			runWalk = true;
			za--;
		}

		if (!input.forward && !input.back && !input.left && !input.right) {
			walk = false;
		}

		if (!input.crouch) {
			crouchWalk = false;
		}

		if (!input.run) {
			runWalk = false;
		}

		if (xa != 0 || za != 0) {
			move(xa, za, rotation);
		}

		y *= 0.9;
	}

}
