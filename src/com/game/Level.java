package com.game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Level {
	private String filePath;
	private final int SPRITESIZE = 16;

	public int[] tileIDs;
	public int[] tiles;
	public static Level flowerField = new Level(10, "/levels/flower_field.txt");

	public Level(int size, String path) {
		this.filePath = path;
		this.tileIDs = new int[size * size];
		this.tiles = new int[(size * SPRITESIZE) * (size * SPRITESIZE)];
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
						this.tileIDs[(column * width) + row] = tileID; // Saving tile IDs here
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
		int ID = 0;
		int xOffset = 0;
		int yOffset = 0;
		for (int i = 0; i < this.tileIDs.length; i++) {
			ID = tileIDs[i];
			switch (ID) {
				case 0:
					for (int xx = 0; xx < SPRITESIZE; xx++) {
						for (int yy = 0; yy < SPRITESIZE; yy++) {
							tiles[(yy + yOffset) + (xx + xOffset) * SPRITESIZE] = Sprite.smallFlowers.pixels[(yy * SPRITESIZE) + xx];
						}
					}
					xOffset++;
					yOffset++;
				case 1:
					for (int xx = 0; xx < SPRITESIZE; xx++) {
						for (int yy = 0; yy < SPRITESIZE; yy++) {
							tiles[(yy + yOffset) + (xx + xOffset) * SPRITESIZE] = Sprite.rocks.pixels[(yy * SPRITESIZE) + xx];
						}
					}
					xOffset++;
					yOffset++;
				case 2:
					for (int xx = 0; xx < SPRITESIZE; xx++) {
						for (int yy = 0; yy < SPRITESIZE; yy++) {
							tiles[(yy + yOffset) + (xx + xOffset) * SPRITESIZE] = Sprite.flowers.pixels[(yy * SPRITESIZE) + xx];
						}
					}
					xOffset++;
					yOffset++;
				case 3:
					for (int xx = 0; xx < SPRITESIZE; xx++) {
						for (int yy = 0; yy < SPRITESIZE; yy++) {
							tiles[(yy + yOffset) + (xx + xOffset) * SPRITESIZE] = Sprite.grass.pixels[(yy * SPRITESIZE) + xx];
						}
					}
					xOffset++;
					yOffset++;
			}
		}
	}
}
