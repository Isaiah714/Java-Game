//import javax.swing.*;    // JPanel 
import java.awt.*;       // Graphics, JFrame
import javax.swing.JPanel;

// levitation, bad dreams, motion blur

public class Render extends JPanel implements Runnable
{
    // Screen Attributes
    private final int originalTileSize = 16;
    private final int scale = 3;

    private final int tileSize = originalTileSize * scale;
    private final int maxScreenColumns = 20;
    private final int maxScreenRows = 32;
    private final int screenWidth = tileSize * maxScreenRows;
    private final int screenHeight = tileSize * maxScreenColumns;

    // 1 billion nanoseconds
    private final long nanoSeconds = 1000000000;

    Control key = new Control();

    // Allows the program to keep running, very useful
    Thread gameThread;

    // FPS
    private int fps = 60;

    // Updates the position of the object on screen
    public int xPos = 100;
    public int yPos = 100;
    public int speedPos = 5;

    public Render()
    {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);

        this.addKeyListener(key);

        /* 
         * setFocusable points to the current object that being
         * interacted with keyboard input
         */
        this.setFocusable(true);
    }

    private void updatePlayer()
    {
        if(key.up == true)         {yPos -= speedPos;}
        else if(key.down == true)  {yPos += speedPos;}
        else if(key.right == true) {xPos += speedPos;}
        else if(key.left == true)  {xPos -= speedPos;}
    }

    public void startGameRendering()
    {
        // Passing render object into the constructor
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void run()
    {
        // Variables that would allow the program to buffer frames properly
        double frameInterval = nanoSeconds/fps;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        // Variables to calculate fps during runtime
        long timer = 0;
        int drawCount = 0;

        while(gameThread != null)
        {
            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / frameInterval;
            timer += (currentTime - lastTime);

            lastTime = currentTime;

            if(delta >= 1)
            {
                updatePlayer();

                repaint();

                delta--;

                drawCount++;
            }

            if(timer >= nanoSeconds)
            {
                System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }

    // This function gets called internally by repaint()
    protected void paintComponent(Graphics graphic)
    {
        super.paintComponent(graphic);

        Graphics2D graphics2d = (Graphics2D)graphic;

        //graphics2d.drawOval(xPos, yPos, 50, 50);

        graphics2d.setColor(Color.GREEN);
        graphics2d.fillOval(xPos, yPos, 50, 50);

        // Frees object from memory
        graphics2d.dispose();
    }
}