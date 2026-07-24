package com.game;

public class Scene {
  private Level level;
  private Player player;

  public Scene(LevelArea area, Player player) {
    this.player = player;
    this.level = new Level(area, this.player.camera);
  }

  public void renderScene() {
    this.level.renderMap(this.player.camera);
    this.player.move();
  }

  public void deleteScene() {
    this.level.clean();
  }
}
