import javax.swing.*;    // JPanel 
import java.awt.*;       // Graphics, JFrame
import java.awt.event.*; // KeyEvent

import javax.swing.JFrame;

public class Render extends JPanel implements KeyListener, ActionListener
{
    // Attributes for the circle object
    int xPos = 100;
    int yPos = 100;
    int diameter = 100;

    // This will be used in multiple functions
    JFrame window;

    // These are the key states
    boolean left, right, down, up, close;

    // This is timer that would update a frame every 16 milliseconds
    Timer timer;

    public Render()
    {
        // Creating window using the JFrame class
        window = new JFrame("Untitled Game");
        window.setSize(800, 800);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // The "this" keyword allows a JPanel object to this window
        window.add(this);
        window.setVisible(true);

        /* 
         * setFocusable points to the current object that being
         * interacted with keyboard input
         */
        this.setFocusable(true);
        this.addKeyListener(this);

        // Instantiated timer to start updating frames (60 fps)
        timer = new Timer(16, this);
        timer.start();
    }

    protected void paintComponent(Graphics graphic)
    {
        super.paintComponent(graphic);

        graphic.drawOval(xPos, yPos, diameter, diameter);

        graphic.setColor(Color.GREEN);
        graphic.fillOval(xPos, yPos, diameter, diameter);
    }

    // This function would update the object every frame
    public void actionPerformed(ActionEvent action)
    {
        if(left)  { xPos -= 10;       }
        if(right) { xPos += 10;       }
        if(down)  { yPos += 10;       }
        if(up)    { yPos -= 10;       }
        if(close) { window.dispose(); }

        /*
         * Using this to change buffer on the screen
         * The screen is updating everytime a key is pressed
         */ 
        repaint();
    }

    /*
     * This function would determine which key is presssed
     * which would satisfy one of the conditions for the 
     * actionPerformed() function
     */ 
    public void keyPressed(KeyEvent event)
    {
        switch (event.getKeyCode())
        {
            case KeyEvent.VK_LEFT:   left  = true; break;
            case KeyEvent.VK_RIGHT:  right = true; break;
            case KeyEvent.VK_DOWN:   down  = true; break;
            case KeyEvent.VK_UP:     up    = true; break;
            case KeyEvent.VK_ESCAPE: close = true; break;
        }
    }

    public void keyReleased(KeyEvent event)
    {
        switch(event.getKeyCode())
        {
            case KeyEvent.VK_LEFT:  left = false; break;
            case KeyEvent.VK_RIGHT: right = false; break;
            case KeyEvent.VK_DOWN:  down = false; break;
            case KeyEvent.VK_UP:    up = false; break;
        }
    }
    /*
     * These are here to avoid a compiler error
     * Reason, using the impelment keyword which
     * forces me to "define" these functions
     * from the keyListener class
     */
    
    public void keyTyped(KeyEvent event) {}
}
