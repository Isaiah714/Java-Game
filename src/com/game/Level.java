package com.game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

enum LevelID {
	FLOWERFIELD,
	FOREST,
	DESERT,
	ICE,
	// Add more levels as needed
}

public class Level {
	private String filePath;
	private final int SPRITESIZE = 16;

	public SpriteID[] tileIDs;
	public int[] tiles;
	public static Level flowerField = new Level(10, "/levels/flower_field.txt");
	//public static Level forest = new Level(10, "/levels/forest.txt");

	public Level(int tileQuantity, String path) {
		this.filePath = path;
		this.tileIDs = new SpriteID[tileQuantity * tileQuantity];
		this.tiles = new int[(tileQuantity * SPRITESIZE) * (tileQuantity * SPRITESIZE)];
		parseLevel();
		loadLevel();
	}

	private void parseLevel() {
		try (InputStream readBytes = Level.class.getResourceAsStream(filePath)) {
			if (readBytes == null) {
				System.out.println("Error: Could not find file at " + filePath);
				return;
			}
			try (BufferedReader reader = new BufferedReader(new InputStreamReader(readBytes))) {
				String line = "";
				int row = 0;
				while ((line = reader.readLine()) != null) {
					int width = line.length();
					for (int column = 0; column < width; column++) {
						char c = line.charAt(column);
						int tileID = Character.getNumericValue(c);
						switch(tileID) {
							case 0:
							this.tileIDs[(column * width) + row] = SpriteID.SMALLFLOWERS;
							break;
							case 1:
							this.tileIDs[(column * width) + row] = SpriteID.ROCKS;
							break;
							case 2:
							this.tileIDs[(column * width) + row] = SpriteID.FLOWERS;
							break;
							case 3:
							this.tileIDs[(column * width) + row] = SpriteID.GRASS;
							break;
						}
					}
					row++;
				}
			}
		} catch (IOException e) {
			System.out.println("An error occured when reading the file");
			e.printStackTrace();
		}
	}

	private void loadLevel() {
		SpriteID ID = null;
		int rowWidth = 160;
		int xPixelPos = 0;
		int yPixelPos = 0;
		int xpixelOffset = 0;
		int ypixelOffset = 0;
		for (int i = 0; i < this.tileIDs.length; i++) {
			ID = tileIDs[i];
			if (xpixelOffset >= 9) {
				xpixelOffset = 0;
				ypixelOffset++;
			}
			if (ypixelOffset >= 9) {
				ypixelOffset = 0;
			}
			switch (ID) {
				case SpriteID.SMALLFLOWERS:
					for (int yy = 0; yy < SPRITESIZE; yy++) {
						for (int xx = 0; xx < SPRITESIZE; xx++) {
							tiles[((yPixelPos * rowWidth) + (yy * rowWidth)) + (xx + xPixelPos)] = Sprite.smallFlowers.pixels[(yy * SPRITESIZE) + xx];
						}
					}
					xpixelOffset++;
					xPixelPos = xpixelOffset * SPRITESIZE;
					yPixelPos = ypixelOffset * SPRITESIZE;
					break;
				case SpriteID.ROCKS:
					for (int xx = 0; xx < SPRITESIZE; xx++) {
						for (int yy = 0; yy < SPRITESIZE; yy++) {
							tiles[((yPixelPos * rowWidth) + (yy * rowWidth)) + (xx + xPixelPos)] = Sprite.rocks.pixels[(yy * SPRITESIZE) + xx];
						}
					}
					xpixelOffset++;
					xPixelPos = xpixelOffset * SPRITESIZE;
					yPixelPos = ypixelOffset * SPRITESIZE;
					break;
				case SpriteID.FLOWERS:
					for (int xx = 0; xx < SPRITESIZE; xx++) {
						for (int yy = 0; yy < SPRITESIZE; yy++) {
							tiles[((yPixelPos * rowWidth) + (yy * rowWidth)) + (xx + xPixelPos)] = Sprite.flowers.pixels[(yy * SPRITESIZE) + xx];
						}
					}
					xpixelOffset++;
					xPixelPos = xpixelOffset * SPRITESIZE;
					yPixelPos = ypixelOffset * SPRITESIZE;
					break;
				case SpriteID.GRASS:
					for (int xx = 0; xx < SPRITESIZE; xx++) {
						for (int yy = 0; yy < SPRITESIZE; yy++) {
							tiles[((yPixelPos * rowWidth) + (yy * rowWidth)) + (xx + xPixelPos)] = Sprite.grass.pixels[(yy * SPRITESIZE) + xx];
						}
					}
					xpixelOffset++;
					xPixelPos = xpixelOffset * SPRITESIZE;
					yPixelPos = ypixelOffset * SPRITESIZE;
					break;
			}
		}
	}
}