package com.game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Vector;

import org.joml.Vector2f;
import org.joml.Vector2i;

enum TileID {
  SMALLFLOWERS,
  ROCKS,
  FLOWERS,
  GRASS
}

enum LevelArea {
  FLOWERLAND,
  /*FORESTLAND,
  DESERTLAND,
  TROPICALLAND,
  ICELAND,
  BLUEDUNGEON,
  PURPLEDUNGEON,
  REDDUNGEON,
  FINALDUNGEON*/
}

class FilePath {
	public String vertex;
	public String fragment;
	public String textureSheet;
  public String tileMap;

	public FilePath(String vertPath, String fragPath, String texPath, String map) {
		this.vertex = vertPath;
		this.fragment = fragPath;
		this.textureSheet = texPath;
    this.tileMap = map;
	}
}

public class Level {
  private final float TILESIZE = 16.0f;
  private LevelArea area;
  private FilePath path;
  private Vector<TileID> tileIDs;
  private Vector2i sprite;

  public Shader shader;

  public Level(LevelArea area, Camera camera) {
    this.tileIDs = new Vector<TileID>();
    String sheetPath = null;
    String mapPath = null;

    switch(area) {
      case LevelArea.FLOWERLAND:
      sheetPath = "/sheets/FlowerfieldSheet.png";
      mapPath = "/levels/flower_field.txt";
      break;
      /*case LevelArea.FORESTLAND:
      break;
      case LevelArea.DESERTLAND:
      break;
      case LevelArea.TROPICALLAND:
      break;
      case LevelArea.ICELAND:
      break;
      case LevelArea.BLUEDUNGEON:
      break;
      case LevelArea.PURPLEDUNGEON:
      break;
      case LevelArea.REDDUNGEON:
      break;
      case LevelArea.FINALDUNGEON:
      break;*/
    }

    this.area = area;
    this.sprite = new Vector2i(0, 0);
    this.path = new FilePath("/shaders/tile.vs",
                                           "/shaders/tile.fs",
                                           sheetPath,
                                           mapPath);

    this.shader = Shader.createShader(this.sprite,
                                                            this.path.vertex,
                                                            this.path.fragment,
                                                            this.path.textureSheet,
                                                            ShapeType.SQUARE);
    
    parseLevel();
  }

  public void renderMap(Camera camera) {
    Vector2f tilePos = new Vector2f();
    TileID ID = null;
    int mapWidthInTiles = 10;

    for(int ii = 0; ii < this.tileIDs.size(); ii++) {
      ID = tileIDs.get(ii);

      int column = ii % mapWidthInTiles;
      int row = ii / mapWidthInTiles;

      tilePos.x = column * TILESIZE;
      tilePos.y = row * TILESIZE;

      switch(this.area) {
      case LevelArea.FLOWERLAND:
        switch(ID) {
          case TileID.SMALLFLOWERS:
          this.shader.sprite.x = 0;
          this.shader.obtainSprite(this.shader.sprite);
          this.shader.scale(TILESIZE);
          this.shader.changePos(tilePos, camera);
          this.shader.draw();
          break;
          case TileID.ROCKS:
          this.shader.sprite.x = 1;
          this.shader.obtainSprite(this.shader.sprite);
          this.shader.scale(TILESIZE);
          this.shader.changePos(tilePos, camera);
          this.shader.draw();
          break;
          case TileID.FLOWERS:
          this.shader.sprite.x = 2;
          this.shader.obtainSprite(this.shader.sprite);
          this.shader.scale(TILESIZE);
          this.shader.changePos(tilePos, camera);
          this.shader.draw();
          break;
          case TileID.GRASS:
          this.shader.sprite.x = 3;
          this.shader.obtainSprite(this.shader.sprite);
          this.shader.scale(TILESIZE);
          this.shader.changePos(tilePos, camera);
          this.shader.draw();
          break;
        }
        break;
        /*case LevelArea.FORESTLAND:
        switch(ID) {
          case TileID.GRASS:
          this.shader.sprite = new Vector2i(0, 0);
          break;
        }
        break;
        case LevelArea.DESERTLAND:
        switch(ID) {
          case TileID.GRASS:
          break;
        }
        break;
        case LevelArea.TROPICALLAND:
        switch(ID) {
          case TileID.GRASS:
          break;
        }
        break;
        case LevelArea.ICELAND:
        switch(ID) {
          case TileID.GRASS:
          break;
        }
        break;
        case LevelArea.BLUEDUNGEON:
        switch(ID) {
          case TileID.GRASS:
          break;
        }
        break;
        case LevelArea.PURPLEDUNGEON:
        switch(ID) {
          case TileID.GRASS:
          break;
        }
        break;
        case LevelArea.REDDUNGEON:
        switch(ID) {
          case TileID.GRASS:
          break;
        }
        break;
        case LevelArea.FINALDUNGEON:
        switch(ID) {
          case TileID.GRASS:
          break;
        }
        break;*/
      }
    }
  }

  public void clean() {
    this.shader.cleanup();
  }

  private void parseLevel() {
		try (InputStream readBytes = Level.class.getResourceAsStream(this.path.tileMap)) {
			if (readBytes == null) {
				System.out.println("Error: Could not find file at " + this.path.tileMap);
				return;
			}
			try (BufferedReader reader = new BufferedReader(new InputStreamReader(readBytes))) {
				String line = "";
				while ((line = reader.readLine()) != null) {
					int width = line.length();
					for (int ii = 0; ii < width; ii++) {
						char c = line.charAt(ii);
						int tileID = Character.getNumericValue(c);
						genMap(area, tileID);
					}
				}
			}
		} catch (IOException e) {
			System.out.println("An error occured when reading the file");
			e.printStackTrace();
		}
	}

  private void genMap(LevelArea area, int ID) {
    switch(area) {
      case LevelArea.FLOWERLAND:
      switch(ID) {
        case 0:
        tileIDs.add(TileID.SMALLFLOWERS);
        break;
        case 1:
        tileIDs.add(TileID.ROCKS);
        break;
        case 2:
        tileIDs.add(TileID.FLOWERS);
        break;
        case 3:
        tileIDs.add(TileID.GRASS);
        break;
      }
      break;
      /*case LevelArea.FORESTLAND:
      switch(ID) {
        case 0:
        break;
      }
      break;
      case LevelArea.DESERTLAND:
      switch(ID) {
        case 0:
        break;
      }
      break;
      case LevelArea.TROPICALLAND:
      switch(ID) {
        case 0:
        break;
      }
      break;
      case LevelArea.ICELAND:
      switch(ID) {
        case 0:
        break;
      }
      break;
      case LevelArea.BLUEDUNGEON:
      switch(ID) {
        case 0:
        break;
      }
      break;
      case LevelArea.PURPLEDUNGEON:
      switch(ID) {
        case 0:
        break;
      }
      break;
      case LevelArea.REDDUNGEON:
      switch(ID) {
        case 0:
        break;
      }
      break;
      case LevelArea.FINALDUNGEON:
      switch(ID) {
        case 0:
        break;
      }
      break;*/
    }
  }

}
