package com.game;

import java.util.Vector;

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
	public String texture;

	public FilePath(String vertPath, String fragPath, String texPath) {
		this.vertex = vertPath;
		this.fragment = fragPath;
		this.texture = texPath;
	}
}

public class Level {
  private Vector<String> levelAssets;
  private FilePath path;

  public Level() {
    this.path = new FilePath("/shaders/tile.vs",
                                           "/shaders/tile.fs",
                                           "/textures/tiles.jpg");
  }

  public Level(Vector<String> levelA) {
    this.levelAssets = levelA;
  }


}
