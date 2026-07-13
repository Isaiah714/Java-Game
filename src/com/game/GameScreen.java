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
		switch (player.currentLevel) {
			case FLOWERFIELD:
				renderMap(Level.flowerField, player);
				break;
			
		
			default:
			break;
		}
	}

	private void renderMap(Level level, Player player) {
		int xTile;
		int yTile;
		int tileSprite = 0;

		for (int row = 0; row < this.height; row++) {
			yWorldPos = row + player.yPos; // Getting camera coordinates (calculating offset)
			yTile = yWorldPos >> tileScale;
			for (int col = 0; col < this.width; col++) {
				xWorldPos = col + player.xPos; // Getting camera coordinates (calculating offset)
				xTile = xWorldPos >> tileScale;
				tileIndex = ((yTile & SPRITE_BOUND) + (xTile & SPRITE_BOUND) * Sprite.flowers.SIZE);
				// tileSprite = tiles[tileIndex & MAPSIZE_BOUND >> 1];
				// tileSprite = Sprite.grass.pixels[tileIndex];
				tileSprite = level.tiles[tileIndex];
				// row * this.width grabs the entire row and adds the column to get the pixel position in the array
				pixels[row * this.width + col] = tileSprite;
			}
		}

		
	}
}