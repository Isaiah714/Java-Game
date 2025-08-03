public class Sprite {
    public int sizeOfSprite;
    public int[] pixels;
    private int x, y;
    private SpriteSheet sheet;

    public static Sprite grass = new Sprite(SpriteSheet.spriteSize, 0, 0, SpriteSheet.grassTiles);
    public static Sprite voidSprite = new Sprite(32, 0xffffff);

    public Sprite(int size, int x, int y, SpriteSheet sheet)
    {
        sizeOfSprite = size;
        this.x = x * size;
        this.y = y * size;
        this.sheet = sheet;
        this.pixels = new int[size * size];
        loadSprite();
    }

    public Sprite(int size, int color)
    {
        sizeOfSprite = size;
        pixels = new int[sizeOfSprite * sizeOfSprite];
        setColor(color);
    }

    private void setColor(int color)
    {
        for(int i = 0; i < sizeOfSprite * sizeOfSprite; i++)
        {
            pixels[i] = color;
        }
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
