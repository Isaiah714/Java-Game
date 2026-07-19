package com.game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Vector;

enum TileID {
  SMALLFLOWERS,
  ROCKS,
  FLOWERS,
  GRASS
}

enum LevelArea {
  FLOWERLAND,
  FORESTLAND,
  DESERTLAND,
  TROPICALLAND,
  ICELAND,
  BLUEDUNGEON,
  PURPLEDUNGEON,
  REDDUNGEON,
  FINALDUNGEON
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
  private LevelArea area;
  private FilePath path;
  private Vector<TileID> tileIDs;

  public Level(String sheetPath, String mapPath) {
    this.path = new FilePath("/shaders/tile.vs",
                                           "/shaders/tile.fs",
                                           this.path.textureSheet,
                                           this.path.tileMap);
    parseLevel();
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
      case LevelArea.FORESTLAND:
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
      break;
    }
  }

}
