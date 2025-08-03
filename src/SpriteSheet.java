import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class SpriteSheet {
    private String filePath;
    public int sizeOfSpriteSheet;
    public int[] pixels;

    public static int spriteSize = 32;

    public static SpriteSheet grassTiles = new SpriteSheet("/textures/grass.png", spriteSize);

    public SpriteSheet(String path, int size)
    {
        this.filePath = path;
        sizeOfSpriteSheet = size;

        pixels = new int[size * size];
        loadSpriteSheet();
    }

    public void loadSpriteSheet()
    {
        try {
            BufferedImage image = ImageIO.read(SpriteSheet.class.getResource(filePath));
            int imageWidth = image.getWidth();
            int imageHeight = image.getHeight();
            image.getRGB(0, 0, imageWidth, imageHeight, pixels, 0, imageWidth);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
