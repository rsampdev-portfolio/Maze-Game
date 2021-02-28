package com.rsampdev.game.graphics;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class Texture {

	public static Render floor = loadBitmap("/textures/BITLY_8X8_11.4/assets/minecraft/textures/blocks/wool_colored_black.png");
	public static Render ceiling = loadBitmap("/textures/BITLY_8X8_11.4/assets/minecraft/textures/blocks/wool_colored_black.png");
	public static Render wall = loadBitmap("/textures/BITLY_8X8_11.4/assets/minecraft/textures/blocks/wool_colored_blue.png");
	
	public static Render silverCoin = loadBitmap("/textures/items/coins/coin_silver.png");
	public static Render goldCoin = loadBitmap("/textures/items/coins/coin_gold.png");
	public static Render diamondCoin = loadBitmap("/textures/items/coins/coin_diamond.png");

	private static Render loadBitmap(String filename) {
		try {
			BufferedImage image = ImageIO.read(Texture.class.getResource(filename));
			int width = image.getWidth();
			int height = image.getHeight();
			Render result = new Render(width, height);
			image.getRGB(0, 0, width, height, result.pixels, 0, width);
			return result;
		} catch (Exception e) {
			System.out.println("CRASH");
			throw new RuntimeException();
		}
	}
}
