package com.game;

public class GameScreen {
	private final int MAPSIZE = 60;
	// private final int MAPSIZE_BOUND = MAPSIZE - 1;
	private final int SPRITE_BOUND = 15;
	private final int tileScale = 3;
	private int tileIndex;

	public int width, height;
	public int xWorldPos, yWorldPos;
	public int[] pixels;
	public int[] tiles = new int[MAPSIZE * MAPSIZE];

	GameScreen(int width, int height) {
		this.width = width;
		this.height = height;
		pixels = new int[width * height];

	}

	public void renderScreen(Player player) {
		int xTile;
		int yTile;
		int tileSprite = 0;

		for (int yScreen = 0; yScreen < this.height; yScreen++) {
			yWorldPos = yScreen + player.yPos; // Getting camera coordinates (calculating offset)
			yTile = yWorldPos >> tileScale;
			for (int xScreen = 0; xScreen < this.width; xScreen++) {
				xWorldPos = xScreen + player.xPos; // Getting camera coordinates (calculating offset)
				xTile = xWorldPos >> tileScale;
				tileIndex = ((yTile & SPRITE_BOUND) + (xTile & SPRITE_BOUND) * Sprite.flowers.SIZE);
				// tileSprite = tiles[tileIndex & MAPSIZE_BOUND >> 1];
				// tileSprite = Sprite.grass.pixels[tileIndex];
				tileSprite = Level.flowerField.tiles[tileIndex];
				pixels[yScreen * this.width + xScreen] = tileSprite;
			}
		}
	}
}