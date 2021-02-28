package com.rsampdev.game.entity.mob;

import com.rsampdev.game.entity.Entity;

public class Mob extends Entity {

	public void move(double xa, double za, double rotation) {
		if (xa != 0 && za != 0) {
			move(xa, 0, rotation);
			move(0, za, rotation);
			return;
		}
		
		double nx = xa * Math.cos(rotation) + za * Math.sin(rotation);
		double nz = za * Math.cos(rotation) - xa * Math.sin(rotation);

		x += nx;
		z += nz;
	}

}
