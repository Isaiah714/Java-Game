import java.awt.event.KeyEvent;    // KeyEvent
import java.awt.event.KeyListener;

public class Control implements KeyListener
{
    // These are the key states
    boolean left, right, down, up, close;

    /*
     * This function would determine which key is presssed
     * which would satisfy one of the conditions for the 
     * actionPerformed() function
     */ 
    public void keyPressed(KeyEvent event)
    {
        switch(event.getKeyCode())
        {
            case KeyEvent.VK_W: up = true;
            case KeyEvent.VK_S: down = true;
            case KeyEvent.VK_D: right = true;
            case KeyEvent.VK_A: left = true;
        }
    }

    public void keyReleased(KeyEvent event)
    {
        switch(event.getKeyCode())
        {
            case KeyEvent.VK_W: up = false;
            case KeyEvent.VK_S: down = false;
            case KeyEvent.VK_D: right = false;
            case KeyEvent.VK_A: left = false;
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
