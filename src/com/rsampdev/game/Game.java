package com.rsampdev.game;

import com.rsampdev.game.entity.mob.Player;
import com.rsampdev.game.input.InputHandler;
import com.rsampdev.game.level.Level;

public class Game {

	public int time;
	public Player player;
	public Level level;

	public Game(InputHandler input) {
		player = new Player(input);
		level = new Level(16, 16);
		level.addEntity(player);
	}

	public void tick() {
		time++;
		level.tick();
	}

}
