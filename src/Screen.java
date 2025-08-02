import java.util.Random;

public class Screen {
    private int width, height;
    public int[] pixels;
    public int[] tiles = new int[64 * 64];

    private final int MAPSIZE = 64;
    private final int SPRITESIZE = 6;

    Random random = new Random();

    Screen(int width, int height)
    {
        this.width = width;
        this.height = height;
        pixels = new int[width * height];

        /* 
         * This for loop would give a random color to 
         * each tile from the tiles array and allow 
         * the program to use this array to color 
         * each pixel in their respective tiles
         * from the pixels array. 
         */
        for(int i = 0; i < MAPSIZE * MAPSIZE; i++)
        {
            tiles[i] = random.nextInt(0xffffff);
        }
    }

    public void clear()
    {
        // Loops through each tile to reset back to zero which results to clearing the pixels(map).
        for(int i = 0; i < pixels.length; i++)
        {
            pixels[i] = 0;
        }
    }

    public void renderScreen(int xOffSet, int yOffSet)
        {
            for(int y = 0; y < height; y++)
            {
                /*
                 * yy is in charge of moving the tiles onto the window.
                 * The yOffset allows the program to animate the tiles.
                 */ 
                int yy = y + yOffSet;
                for(int x = 0; x < width; x++)
                {
                    /*
                     * xx is in charge of moving the tiles onto the window
                     * The xOffSet allows the program to animate the tiles.
                     */ 
                    int xx = x + xOffSet;
                    /*
                     * tileIndex allows the program to move each tile from the pixels array.
                     * xx >> 5 is equavalent to xx / 2^5 and & checks if xx is out of bounds
                     * which would then return zero preventing an error. The - 1 is required
                     * since the array starts from zero. The x >> 5 is the actual logic in
                     * rendering tiles onto the window. Same goes the y >> 5, the MAPSIZE
                     * follows the same formula for pixels[x + (y * width)] in drawing 
                     * every pixel in their respective columns.
                     */ 
                    int tileIndex = ((xx >> SPRITESIZE) & MAPSIZE - 1) + ((yy >> SPRITESIZE) & MAPSIZE - 1) * MAPSIZE;
                    pixels[x + (y * width)] = tiles[tileIndex];
                }
            }
        }
}


// Side Note: The original code that checks if the tiles are out of bounds.
//if(yy >= height || yy < 0) {break;}
//if(xx >= width || xx < 0) {break;}
