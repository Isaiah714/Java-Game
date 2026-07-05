import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

public class Render {
    JFrame window;

    private final int screenWidth = 840;
    private final int screenHeight = 640;

    private BufferedImage imageWindow = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_RGB);

    private Thread gameThread;

    public Render() {
        window = new JFrame();
        window.setSize(new Dimension(screenWidth, screenHeight));
        window.setVisible(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Not sure yet");
    }
}
