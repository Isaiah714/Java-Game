import java.awt.event.KeyEvent;    // KeyEvent
import java.awt.event.KeyListener;

public class Control implements KeyListener
{
    // These are the key states
    boolean left, right, down, up, close;

    // Updates the position of the object on screen
    public int xPos = 100;
    public int yPos = 100;
    public int speedPos = 10;

    /*
     * This function would determine which key is presssed
     * which would satisfy one of the conditions for the 
     * actionPerformed() function
     */ 
    public void keyPressed(KeyEvent event)
    {
        int keycode = event.getKeyCode();

        if(keycode == KeyEvent.VK_W) {up = true;}
        if(keycode == KeyEvent.VK_S) {down = true;}
        if(keycode == KeyEvent.VK_D) {right = true;}
        if(keycode == KeyEvent.VK_A) {left = true;}
        if(keycode == KeyEvent.VK_ESCAPE) {close = true;}
    }
    public void keyReleased(KeyEvent event)
    {
        int keycode = event.getKeyCode();

        if(keycode == KeyEvent.VK_W) {up = false;}
        if(keycode == KeyEvent.VK_S) {down = false;}
        if(keycode == KeyEvent.VK_D) {right = false;}
        if(keycode == KeyEvent.VK_A) {left = false;}
        if(keycode == KeyEvent.VK_ESCAPE) {close = false;}
    }

    /*
     * These are here to avoid a compiler error
     * Reason, using the impelment keyword which
     * forces me to "define" these functions
     * from the keyListener class
     */
    public void keyTyped(KeyEvent event) {}
}
