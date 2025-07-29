import javax.swing.*;    // JPanel 
import java.awt.*;       // Graphics, JFrame
import java.awt.event.*; // KeyEvent

import javax.swing.JFrame;

public class Render extends JPanel implements KeyListener
{
    // Position for the Rectangle object
    int xPos = 100;
    int yPos = 100;

    public Render()
    {
        // Creating window using the JFrame class
        JFrame window = new JFrame("Untitled Game");
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
    }

    protected void paintComponent(Graphics graphic)
    {
        super.paintComponent(graphic);

        graphic.setColor(Color.GREEN);
        graphic.fillRect(xPos, yPos, 100, 100);

        //graphic.setColor(Color.CYAN);
        //graphic.fillRect(300, 300, 50, 50);

    }

    public void keyPressed(KeyEvent event)
    {
        int userKey = event.getKeyCode();

        if( userKey == KeyEvent.VK_LEFT)
        {
            xPos -= 10;
        }

        if(userKey == KeyEvent.VK_RIGHT)
        {
            xPos += 10;
        }

        if(userKey == KeyEvent.VK_DOWN)
        {
            yPos += 10;
        }

        if( userKey == KeyEvent.VK_UP)
        {
            yPos -= 10;
        }

        // Using this to change buffer on the screen
        // The screen is updating everytime a key is pressed
        repaint();
    }
    /*
     * These are here to avoid compiler error
     * Reason, using impelment keyword which
     * forces me to "define" these functions
     * from the keyListener class
     * public void keyReleased(KeyEvent event) {}
     */
    public void keyReleased(KeyEvent event) {}
    public void keyTyped(KeyEvent event) {}
}
