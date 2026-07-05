public class GrassTile extends Tile {
    GrassTile(Sprite sprite)
    {
        super(sprite);
    }

    public void render(int x, int y, Screen screen)
    {
        screen.renderTile(x << 5, y << 5, this);
    }
}
