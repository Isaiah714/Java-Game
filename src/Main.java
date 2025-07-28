import java.awt.*;    // JFrame
import javax.swing.*; // JPanel

import javax.swing.JFrame;

// https://introcs.cs.princeton.edu/java/11cheatsheet/

public class Main {
    // Main body of the program
    public static void main(String[] args) throws Exception {
        // Instantiated JFrame object to create a window object
        JFrame frame = new JFrame("Game Window");

        // Setting the size of the window
        frame.setSize(800, 800);

        // Allows the user to exit window by clicking the X icon
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // JPanel class allows object to be drawn onto the window
        JPanel panel = new JPanel() {
            // Overriding this method, must use the protected keyword
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                g.setColor(Color.RED);
                g.fillRect(100, 100, 200, 200);

                repaint();

                g.setColor(Color.GREEN);
                g.fillRect(200, 600, 100, 100);
            }
        };

        frame.add(panel);
        frame.setVisible(true);

        /*frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                System.exit(0);
            }
        });*/
    }
}
