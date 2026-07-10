package com.game;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

public class Game extends Canvas implements Runnable {
	// Create an image object to create data to be passed into the window to render
	// something
	private BufferedImage image = new BufferedImage(windowWidth, windowHeight, BufferedImage.TYPE_INT_RGB);
	// Create pixels to obtain data that will be used to render pixels onto the
	// screen (pixels will act as data)
	private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
	private boolean running = false;
	private GameScreen screen = new GameScreen(windowWidth, windowHeight);
	private static GameInput gameInput = new GameInput();

	public static final int windowHeight = 960;
	public static final int windowWidth = 960;
	public static Player player = new Player(gameInput);

	public void start() {
		running = true;
		new Thread(this).start();
	}

	public void stop() {
		running = false;
	}

	public void run() {
		final double fps = 60.0;
		final double nanoSeconds = 1000000000.0;
		long timer = System.currentTimeMillis();
		double tickInterval = nanoSeconds / fps;
		double currentTime = 0.0;
		double lastTime = System.nanoTime();
		double delta = 0.0;
		int frames = 0;
		int fpsTicks = 0;
		while (running) {
			currentTime = System.nanoTime();
			delta += (currentTime - lastTime) / tickInterval;
			lastTime = currentTime;
			if (delta >= 1) {
				delta--;
				tick();
				player.movePlayer(screen);
				fpsTicks++;
			}
			render();
			frames++;

			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				System.out.println(fpsTicks + " fps, " + frames + " ups");
				frames = 0;
				fpsTicks = 0;
			}
		}
	}

	int tickCount;

	public void tick() {
		tickCount++;
	}

	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}

		// ------------------ Draw Here ------------------//
		screen.renderScreen(player);
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = screen.pixels[i];
		}
		// ----------------------------------------------//

		Graphics graphics = bs.getDrawGraphics();
		graphics.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		graphics.dispose();
		bs.show();
	}

	public static void main(String[] args) {
		Game game = new Game();
		game.setPreferredSize(new Dimension(windowWidth, windowHeight));
		JFrame frame = new JFrame("Untitled");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.add(game);
		frame.pack();
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

		game.addKeyListener(gameInput);
		game.requestFocus();
		game.start();
	}
}
/*
 * for (int ii = 0; ii < Level.flowerField.tileIDs.length; ii++) {
 * System.out.print(Level.flowerField.tileIDs[ii]);
 * }
 */
