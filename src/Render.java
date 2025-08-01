import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class Render extends Canvas implements Runnable
{
    // Crearting window object
    JFrame window;

    // Screen Attributes
    private final int originalTileSize = 16;
    private final int scale = 3;

    private final int tileSize = originalTileSize * scale;
    private final int maxScreenColumns = 20;
    private final int maxScreenRows = 32;
    private final int screenWidth = tileSize * maxScreenRows;
    private final int screenHeight = tileSize * maxScreenColumns;

    private BufferedImage imageWindow = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_RGB);
    private int[] interpol = ((DataBufferInt)imageWindow.getRaster().getDataBuffer()).getData();
    private int frameCount;

    // 1 billion nanoseconds
    private final long nanoSeconds = 1000000000;

    Control key = new Control();

    // Allows the program to keep running, very useful
    private Thread gameThread;

    // FPS
    private double fps = 60.0;

    public Render()
    {
        window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Untitled Game");

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);

        window.add(this);
        window.pack();

        this.addKeyListener(key);

        window.setLocationRelativeTo(null);

        window.setVisible(true);

        /* 
         * setFocusable points to the current object that being
         * interacted with keyboard input
         */
        this.setFocusable(true);
    }

    private void updatePlayer()
    {
        if(key.up == true)         {key.yPos -= key.speedPos;}
        if(key.down == true)       {key.yPos += key.speedPos;}
        if(key.right == true)      {key.xPos += key.speedPos;}
        if(key.left == true)       {key.xPos -= key.speedPos;}
        if(key.close == true)      {window.dispose(); System.exit(1);}
    }

    public void startGameRendering()
    {
        // Passing render object into the constructor
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void updateFrames()
    {
        frameCount++;
    }

    public void draw()
    {
        BufferStrategy bs = getBufferStrategy();

        if(bs == null)
        {
            createBufferStrategy(3);
            return;
        }

        for( int i = 0; i < interpol.length; i++)
        {
            interpol[i] = i + frameCount;
        }

        /*Graphics g = imageWindow.getGraphics();
        g.setColor(Color.CYAN);
        g.fillRect(100, 100, 50, 50);
        g.dispose();*/

        // Setting up the window to show render graphics on the screen
        Graphics graphicObject = bs.getDrawGraphics();
        graphicObject.drawImage(imageWindow, 0, 0, window);
        graphicObject.dispose();
        bs.show();
    }

    public void run()
    {
        long lastFrame = System.nanoTime();
        double delta = 0.0;
        double frameInterval = nanoSeconds / fps;
        long currentFrame;
        while(gameThread != null)
        {
            currentFrame = System.nanoTime();
            delta = (currentFrame - lastFrame) / frameInterval;
            while(delta >= 1)
            {
                updatePlayer();
                updateFrames();
                delta -= 1;
            }
            draw();
        }
    }
}