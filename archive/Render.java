import javax.swing.JFrame;
//import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class Render extends Canvas implements Runnable {
    // Crearting window object
    JFrame window;

    // Screen Attributes
    private final int screenWidth = 1500;
    private final int screenHeight = 1000;

    private BufferedImage imageWindow = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_RGB);

    // This needs to be declared so this varible can take data from other arrays to
    // display onto the window.
    private int[] pixelBuffer = ((DataBufferInt) imageWindow.getRaster().getDataBuffer()).getData();
    // private int ticks;

    private Control key = new Control();

    // Allows the program to keep running, very useful
    private Thread gameThread;

    // Creating a Screen object
    private Screen screen = new Screen(screenWidth, screenHeight);

    // Creating a Level object
    private Level level = new Level(64, 64);

    int xTick, yTick;

    public Render() {
        window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Untitled Game");

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);

        // add() gives the context of the current instantiated object from Main.java.
        window.add(this);
        window.pack();

        // addKeyListener gives context of the keyboard
        this.addKeyListener(key);

        /*
         * This pre-defined function from the java
         * SE library sets the window in the center
         * of the monitor.
         */
        window.setLocationRelativeTo(null);

        // Also from SE library, use this show the window.
        window.setVisible(true);

        /*
         * setFocusable points to the current object that being
         * interacted with keyboard input
         */
        this.setFocusable(true);
    }

    private void playerTick() {
        if (key.up == true) {
            key.yPos -= key.speedPos;
            yTick--;
        }
        if (key.down == true) {
            key.yPos += key.speedPos;
            yTick++;
        }
        if (key.right == true) {
            key.xPos += key.speedPos;
            xTick++;
        }
        if (key.left == true) {
            key.xPos -= key.speedPos;
            xTick--;
        }
        if (key.close == true) {
            window.dispose();
            System.exit(1);
        }
    }

    // This is required to run multiple functions at the same time
    public void startGame() {
        // Passing render object into the constructor
        gameThread = new Thread(this);
        gameThread.start();
    }

    // This function is what updates the position of the tiles
    /*
     * private void ticks() {
     * xTick++;
     * yTick++;
     * }
     */

    private void render() {
        BufferStrategy bs = getBufferStrategy();

        if (bs == null) {
            createBufferStrategy(3);
            return;
        }

        screen.clear();
        level.render(xTick, yTick, screen);

        /*
         * This would pass all the data from the screen.pixels
         * into pixelBuffer. pixelBuffer would take that data
         * and can use that data to actually render onto the
         * window.
         */
        for (int i = 0; i < pixelBuffer.length; i++) {
            pixelBuffer[i] = screen.pixels[i];
        }

        // Setting up the window to show render graphics on the screen
        Graphics graphicObject = bs.getDrawGraphics();
        graphicObject.fillRect(0, 0, getWidth(), getHeight());

        // drawImage would actually allow us to render the pixels onto the screen
        graphicObject.drawImage(imageWindow, 0, 0, window);

        graphicObject.dispose();
        bs.show();

    }

    // run is interally called from gameThread.start().
    public void run() {
        final double fps = 60.0;
        final long nanoSeconds = 1000000000;
        long timer = System.currentTimeMillis();
        double tickInterval = nanoSeconds / fps;
        double currentTime;
        double lastTime = System.nanoTime();
        double delta;
        int frames = 0;
        int fpsTicks = 0;
        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta = (currentTime - lastTime) / tickInterval;
            if (delta >= 1) {
                playerTick();
                // ticks();
                delta--;
                fpsTicks++;
            }
            render();
            frames++;

            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                System.out.println(fpsTicks + " ups, " + frames + " fps");
                frames = 0;
                fpsTicks = 0;
            }
        }
    }
}