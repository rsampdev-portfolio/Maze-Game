package com.rsampdev.game.level;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.rsampdev.game.entity.Entity;
import com.rsampdev.game.graphics.Sprite;

public class Level {

	public Block[] blocks;

	public final int width;
	public final int height;
	public final Random random = new Random();
	
	private List<Entity> entities = new ArrayList<Entity>(); 

	public Level(int width, int height) {
		this.width = width;
		this.height = height;
		blocks = new Block[width * height];
		generateLevel();
	}
	
	public void tick() {
		for (int i = 0; i < entities.size(); i++) {
			entities.get(i).tick();
		}
	}
	
	public void addEntity(Entity e) {
		entities.add(e);
	}
	
	private void generateLevel() {		
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				Block block = null;
				if (random.nextInt(4) == 0) {
					block = new SolidBlock();
				} else {
					block = new Block();
					if (random.nextInt(9) == 0) {
						block.addSprite(new Sprite(0, 0, 0));
					}
				}
				blocks[x + y * width] = block;
			}
		}
	}

	public Block create(int x, int y) {
		if (x < 0 || y < 0 || x >= width || y >= height) {
			return Block.solidWall;
		}
		return blocks[x + y * width];
	}

}
