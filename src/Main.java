/*import java.awt.Canvas;
import java.awt.BorderLayout;
import java.awt.color.*;
import java.awt.Graphics;
import java.awt.Window;*/
import java.awt.*;
import java.awt.event.*;

public class Main {
    // Main body of the program
    public static void main(String[] args) throws Exception {
        Frame frame = new Frame("Game Window");

        frame.setSize(500, 500);

        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                System.exit(0);
            }
        });

        frame.setVisible(true);
    }
}
