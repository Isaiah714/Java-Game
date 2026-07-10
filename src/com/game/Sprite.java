package com.game;

enum SpriteID {
	SMALLFLOWERS,
	ROCKS,
	FLOWERS,
	GRASS
}

public class Sprite {
	private int xpixelPos, ypixelPos;
	private SpriteSheet sheet;
	public final int SIZE;
	public int[] pixels;
	// Creating all sprite here
	public static Sprite smallFlowers = new Sprite(16, 0, 0, SpriteID.SMALLFLOWERS, SpriteSheet.gameSheet);
	public static Sprite rocks = new Sprite(16, 1, 0, SpriteID.ROCKS, SpriteSheet.gameSheet);
	public static Sprite flowers = new Sprite(16, 2, 0, SpriteID.FLOWERS, SpriteSheet.gameSheet);
	public static Sprite grass = new Sprite(16, 3, 0, SpriteID.GRASS, SpriteSheet.gameSheet);

	public Sprite(int size, int x, int y, SpriteID ID, SpriteSheet sheet) {
		SIZE = size;
		this.xpixelPos = x * size;
		this.ypixelPos = y * size;
		this.sheet = sheet;
		pixels = new int[SIZE * SIZE];
		load();
	}

	private void load() {
		for (int xOffset = 0; xOffset < SIZE; xOffset++) {
			for (int yOffset = 0; yOffset < SIZE; yOffset++) {
				// This will fill the sprite in a specific cell (16x16 cell) perfect for the
				// sprite being loaded in (also 16x16).
				pixels[(yOffset * SIZE) + xOffset] = sheet.pixels[(this.xpixelPos + xOffset)
						+ (this.ypixelPos + yOffset) * sheet.SIZE];
			}
		}
	}

}
