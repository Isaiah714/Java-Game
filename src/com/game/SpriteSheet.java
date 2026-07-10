package com.game;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteSheet {
	public final int SIZE;
	public int[] pixels;
	public static SpriteSheet gameSheet = new SpriteSheet("/sheets/SpriteSheet(PNG).png", 160);

	private String filePath;

	public SpriteSheet(String path, int size) {
		this.filePath = path;
		SIZE = size;
		pixels = new int[SIZE * SIZE];
		load();
	}

	private void load() {
		try {
			BufferedImage image = ImageIO.read(SpriteSheet.class.getResource(filePath));
			int width = image.getWidth();
			int height = image.getHeight();
			image.getRGB(0, 0, width, height, pixels, 0, width);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
