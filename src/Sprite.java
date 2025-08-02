public class Sprite {
    private int sizeOfSprite;
    private int x, y;
    private int[] pixels;
    private SpriteSheet sheet;

    public static Sprite grass = new Sprite(16, 0, 0, null);

    public Sprite(int size, int x, int y, SpriteSheet sheet)
    {
        sizeOfSprite = size;
        this.x = x * size;
        this.y = y * size;
        this.sheet = sheet;
        loadSprite();
    }

    private void loadSprite()
    {
        for(int y = 0; y < sizeOfSprite; y++)
        {
            for(int x = 0; x < sizeOfSprite; x++)
            {
                pixels[x + (y * sizeOfSprite)] = sheet.pixels[(x + this.x) + (y + this.y) * sheet.sizeOfSpriteSheet];
            }
        }
    }

}
