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
    private final int maxScreenColumns = 16;
    private final int maxScreenRows = 12;
    private final int screenWidth = tileSize * maxScreenRows;
    private final int screenHeight = tileSize * maxScreenColumns;

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

    public void updatePlayer()
    {
        if(key.up == true)         {yPos -= speedPos; System.out.println("up");}
        else if(key.down == true)  {yPos += speedPos; System.out.println("down");}
        else if(key.right == true) {xPos += speedPos; System.out.println("right");}
        else if(key.left == true)  {xPos -= speedPos; System.out.println("left");}
    }

    public void startGameRendering()
    {
        // Passing render object into the constructor
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void run()
    {
        double frameInterval = 1000000000/fps;
        double nextFrame = System.nanoTime() + frameInterval;

        while(gameThread != null)
        {
            updatePlayer();

            repaint();

            // Forced to use the try catch block that was insisted by javac.
            try
            {
                double remainingTimeToNextFrame = nextFrame - System.nanoTime();
                remainingTimeToNextFrame = remainingTimeToNextFrame/1000000;

                if(remainingTimeToNextFrame < 0)
                {
                    remainingTimeToNextFrame = 0;
                }

                Thread.sleep((long) remainingTimeToNextFrame);

                nextFrame += frameInterval;
            } 
            catch (InterruptedException e)
            {
                e.printStackTrace();
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