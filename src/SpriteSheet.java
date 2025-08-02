import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteSheet {
    private String path;
    public int sizeOfSpriteSheet;
    public int[] pixels;

    public SpriteSheet(String path, int size)
    {
        this.path = path;
        sizeOfSpriteSheet = size;

        pixels = new int[size * size];
        loadSpriteSheet();
    }

    public void loadSpriteSheet()
    {
        try {
            BufferedImage image = ImageIO.read(SpriteSheet.class.getResource(path));
            int imageWidth = image.getWidth();
            int imageHeight = image.getHeight();
            image.getRGB(imageWidth, imageHeight);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
