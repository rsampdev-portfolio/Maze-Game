package com.rsampdev.game.graphics;

import com.rsampdev.game.Display;
import com.rsampdev.game.Game;
import com.rsampdev.game.entity.mob.Player;
import com.rsampdev.game.level.Block;
import com.rsampdev.game.level.Level;

public class Render3D extends Render {

	public double[] zBuffer;
	public double[] zBufferWall;
	private double renderDistance = 10000;
	private double forward; // z
	private double right; // x
	private double up; // y
	private double cosine;
	private double sine;
	private double walking;
	private int spriteSheetWidth = 8;
	private Sprite sprite = null;
	private Block block = null;
	double h = 0.5;
	int c = -1;

	public Render3D(int width, int height) {
		super(width, height);
		zBuffer = new double[width * height];
		zBufferWall = new double[width];
	}

	public void floor(Game game) {
		for (int x = 0; x < width; x++) {
			zBufferWall[x] = 0;
		}

		double floorPosition = 8;
		double ceilingPosition = 8;
		forward = game.player.z;
		right = game.player.x;
		up = game.player.y;
		walking = 0;
		double rotation = game.player.rotation;
		cosine = Math.cos(rotation);
		sine = Math.sin(rotation);

		for (int y = 0; y < height; y++) {
			double ceiling = (y - height / 2.0) / height;
			double z = (floorPosition + up) / ceiling;
			c = 0;

			if (Player.walk) {
				walking = Math.sin(game.time / 6.0) * 0.5;
				z = (floorPosition + up + walking) / ceiling;
			}

			if (Player.crouchWalk && Player.walk) {
				walking = Math.sin(game.time / 6.0) * 0.5;
				walking = Math.sin(game.time / 6.0) * 0.25;
			}

			if (Player.runWalk && Player.walk) {
				walking = Math.sin(game.time / 6.0) * 0.5;
				walking = Math.sin(game.time / 6.0) * 0.8;
			}

			if (ceiling < 0) {
				z = (ceilingPosition - up) / -ceiling;
				c = 1;
				if (Player.walk) {
					z = (ceilingPosition - up - walking) / -ceiling;
				}
			}

			for (int x = 0; x < width; x++) {
				double depth = (x - width / 2.0) / height;
				depth *= z;
				double xx = depth * cosine + z * sine + right;
				double yy = z * cosine - depth * sine + forward;

				int xPix = (int) (xx);
				int yPix = (int) (yy);

				zBuffer[x + y * width] = z;

				int color = 0;

				if (c == 0) {
					color = Texture.floor.pixels[(xPix & 7) + (yPix & 7) * spriteSheetWidth];
				} else {
					color = Texture.ceiling.pixels[(xPix & 7) + (yPix & 7) * spriteSheetWidth];
				}

				if (color != 0xffff00ff) {
					pixels[x + y * width] = color;
				}

				if (z > 500) {
					pixels[x + y * width] = 0;
				}
			}
		}

		Level level = game.level;
		int size = 300;
		for (int xBlock = -size; xBlock <= size; xBlock++) {
			for (int zBlock = -size; zBlock <= size; zBlock++) {
				Block block = level.create(xBlock, zBlock);
				Block east = level.create(xBlock + 1, zBlock);
				Block south = level.create(xBlock, zBlock + 1);

				if (block.solid) {
					if (!east.solid) {
						renderWall(xBlock + 1, xBlock + 1, zBlock, zBlock + 1, 0);
					}
					if (!south.solid) {
						renderWall(xBlock + 1, xBlock, zBlock + 1, zBlock + 1, 0);
					}
				} else {
					if (east.solid) {
						renderWall(xBlock + 1, xBlock + 1, zBlock + 1, zBlock, 0);
					}
					if (south.solid) {
						renderWall(xBlock, xBlock + 1, zBlock + 1, zBlock + 1, 0);
					}
				}
			}
		}
		for (int xBlock = -size; xBlock <= size; xBlock++) {
			for (int zBlock = -size; zBlock <= size; zBlock++) {
				Block block = level.create(xBlock, zBlock);
				Block east = level.create(xBlock + 1, zBlock);
				Block south = level.create(xBlock, zBlock + 1);

				if (block.solid) {
					if (!east.solid) {
						renderWall(xBlock + 1, xBlock + 1, zBlock, zBlock + 1, 0.5);
					}
					if (!south.solid) {
						renderWall(xBlock + 1, xBlock, zBlock + 1, zBlock + 1, 0.5);
					}
				} else {
					if (east.solid) {
						renderWall(xBlock + 1, xBlock + 1, zBlock + 1, zBlock, 0.5);
					}
					if (south.solid) {
						renderWall(xBlock, xBlock + 1, zBlock + 1, zBlock + 1, 0.5);
					}
				}
			}
		}
		for (int xBlock = -size; xBlock <= size; xBlock++) {
			for (int zBlock = -size; zBlock <= size; zBlock++) {
				block = level.create(xBlock, zBlock);
				for (int s = 0; s < block.sprites.size(); s++) {
					sprite = block.sprites.get(s);
					renderSprite(xBlock + sprite.x, sprite.y, zBlock + sprite.z, h, Texture.goldCoin);
				}
			}
		}
	}

	public void renderSprite(double x, double y, double z, double heightOffset, Render spriteType) {
		double forwardCorrect = 0.0625;
		double rightCorrect = 0.0625;
		double upCorrect = -0.125;
		double walkCorrect = 0.0625;
		int spriteSize = height / 2;

		double animation = Math.sin(walking * 1.1 / 10.5);
		double floating = 0;

		if (animation != 0.0) {
			floating += animation;
		}

		double xc = ((x / 2) - (right * rightCorrect)) * 2 + heightOffset;
		double yc = ((y / 2) - (up * upCorrect)) + (walking * walkCorrect) * 2 + heightOffset + floating;
		double zc = ((z / 2) - (forward * forwardCorrect)) * 2 + heightOffset;

		double rotX = xc * cosine - zc * sine;
		double rotY = yc;
		double rotZ = zc * cosine + xc * sine;

		double xCenter = width / 2.0;
		double yCenter = height / 2.0;

		double xPixel = rotX / rotZ * height + xCenter;
		double yPixel = rotY / rotZ * height + yCenter;

		double xPixelL = xPixel - spriteSize / rotZ;
		double xPixelR = xPixel + spriteSize / rotZ;

		double yPixelL = yPixel - spriteSize / rotZ;
		double yPixelR = yPixel + spriteSize / rotZ;

		int xpl = (int) xPixelL;
		int xpr = (int) xPixelR;
		int ypl = (int) yPixelL;
		int ypr = (int) yPixelR;

		if (xpl < 0) {
			xpl = 0;
		}
		if (xpr > width) {
			xpr = 0;
		}
		if (ypl < 0) {
			ypl = 0;
		}
		if (ypr > height) {
			ypr = 0;
		}

		rotZ *= 8;

		for (int yp = ypl; yp < ypr; yp++) {
			double pixelRotationY = (yp - yPixelR) / (yPixelL - yPixelR);
			int yTexture = (int) (pixelRotationY * 8);

			for (int xp = xpl; xp < xpr; xp++) {
				double pixelRotationX = (xp - xPixelR) / (xPixelL - xPixelR);
				int xTexture = (int) (pixelRotationX * 8);

				if (zBuffer[xp + yp * width] > rotZ) {
					int color = spriteType.pixels[(xTexture & 7) + (yTexture & 7) * spriteSheetWidth];
					if (color != 0xffff00ff) {
						if (pixels[(width / 2) + yp * width] == 0xffEAEE57) {
							block.sprites.remove(sprite);
							Display.SCORE += 1;
						}
						pixels[xp + yp * width] = color;
						zBuffer[xp + yp * width] = rotZ;
					}
				}
			}
		}
	}

	public void renderWall(double xLeft, double xRight, double zDistanceLeft, double zDistanceRight, double yHeight) {
		double forwardCorrect = 0.0625;
		double rightCorrect = 0.0625;
		double upCorrect = 0.0625;
		double walkCorrect = -0.0625;

		double xcLeft = ((xLeft / 2) - (right * rightCorrect)) * 2;
		double zcLeft = ((zDistanceLeft / 2) - (forward * forwardCorrect)) * 2;

		double rotLeftSideX = xcLeft * cosine - zcLeft * sine;
		double yCornerTL = ((-yHeight) - (-up * upCorrect + (walking * walkCorrect))) * 2;
		double yCornerBL = ((+0.5 - yHeight) - (-up * upCorrect + (walking * walkCorrect))) * 2;
		double rotLeftSideZ = zcLeft * cosine + xcLeft * sine;

		double xcRight = ((xRight / 2) - (right * rightCorrect)) * 2;
		double zcRight = ((zDistanceRight / 2) - (forward * forwardCorrect)) * 2;

		double rotRightSideX = xcRight * cosine - zcRight * sine;
		double yCornerTR = ((-yHeight) - (-up * upCorrect + (walking * walkCorrect))) * 2;
		double yCornerBR = ((+0.5 - yHeight) - (-up * upCorrect + (walking * walkCorrect))) * 2;
		double rotRightSideZ = zcRight * cosine + xcRight * sine;

		double tex30 = 0;
		double tex40 = 8;
		double clip = 0.5;

		if (rotLeftSideZ < clip && rotRightSideZ < clip) {
			return;
		}

		if (rotLeftSideZ < clip) {
			double clip0 = (clip - rotLeftSideZ) / (rotRightSideZ - rotLeftSideZ);
			rotLeftSideZ = rotLeftSideZ + (rotRightSideZ - rotLeftSideZ) * clip0;
			rotLeftSideX = rotLeftSideX + (rotRightSideX - rotLeftSideX) * clip0;
			tex30 = tex30 + (tex40 - tex30) * clip0;
		}

		if (rotRightSideZ < clip) {
			double clip0 = (clip - rotLeftSideZ) / (rotRightSideZ - rotLeftSideZ);
			rotRightSideZ = rotLeftSideZ + (rotRightSideZ - rotLeftSideZ) * clip0;
			rotRightSideX = rotLeftSideX + (rotRightSideX - rotLeftSideX) * clip0;
			tex40 = tex30 + (tex40 - tex30) * clip0;
		}

		double xPixelLeft = (rotLeftSideX / rotLeftSideZ * height + width / 2);
		double xPixelRight = (rotRightSideX / rotRightSideZ * height + width / 2);

		if (xPixelLeft >= xPixelRight) {
			return;
		}

		int xPixelLeftInt = (int) (xPixelLeft);
		int xPixelRightInt = (int) (xPixelRight);

		if (xPixelLeftInt < 0) {
			xPixelLeftInt = 0;
		}
		if (xPixelRightInt > width) {
			xPixelRightInt = width;
		}

		double yPixelLeftTop = (yCornerTL / rotLeftSideZ * height + height / 2.0);
		double yPixelLeftBottom = (yCornerBL / rotLeftSideZ * height + height / 2.0);
		double yPixelRightTop = (yCornerTR / rotRightSideZ * height + height / 2.0);
		double yPixelRightBottom = (yCornerBR / rotRightSideZ * height + height / 2.0);

		double tex1 = 1 / rotLeftSideZ;
		double tex2 = 1 / rotRightSideZ;
		double tex3 = tex30 / rotLeftSideZ;
		double tex4 = tex40 / rotRightSideZ - tex3;

		for (int x = xPixelLeftInt; x < xPixelRightInt; x++) {
			double pixelRotation = (x - xPixelLeft) / (xPixelRight - xPixelLeft);
			double zWall = (tex1 + (tex2 - tex1) * pixelRotation);

			if (zBufferWall[x] > zWall) {
				continue;
			}
			zBufferWall[x] = zWall;

			int xTexture = (int) ((tex3 + tex4 * pixelRotation) / (zWall));

			double yPixelTop = yPixelLeftTop + (yPixelRightTop - yPixelLeftTop) * pixelRotation;
			double yPixelBottom = yPixelLeftBottom + (yPixelRightBottom - yPixelLeftBottom) * pixelRotation;

			int yPixelTopInt = (int) (yPixelTop);
			int yPixelBottomInt = (int) (yPixelBottom);

			if (yPixelTopInt < 0) {
				yPixelTopInt = 0;
			}
			if (yPixelBottomInt > height) {
				yPixelBottomInt = height;
			}

			for (int y = yPixelTopInt; y < yPixelBottomInt; y++) {
				double pixelRotationY = (y - yPixelTop) / (yPixelBottom - yPixelTop);
				int yTexture = (int) (8 * pixelRotationY);

				int color = Texture.wall.pixels[(xTexture & 7) + (yTexture & 7) * spriteSheetWidth];

				if (color != 0xffff00ff) {
					pixels[x + y * width] = color;
					zBuffer[x + y * width] = 1 / (tex1 + (tex2 - tex1) * pixelRotation) * spriteSheetWidth;
				}
			}
		}
	}

	public void renderDistanceLimiter() {
		for (int i = 0; i < width * height; i++) {
			int color = pixels[i];

			int brightness = (int) (renderDistance / (zBuffer[i]));

			if (brightness < 0) {
				brightness = 0;
			}

			if (brightness > 255) {
				brightness = 255;
			}

			int r = (color >> 16) & 0xff;
			int g = (color >> 8) & 0xff;
			int b = (color) & 0xff;

			r = r * brightness / 255;
			g = g * brightness / 255;
			b = b * brightness / 255;

			pixels[i] = r << 16 | g << 8 | b;
		}
	}
}
