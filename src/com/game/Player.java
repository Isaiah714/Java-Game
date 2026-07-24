package com.game;

import org.joml.Vector2f;
import org.lwjgl.glfw.GLFW;

public class Player {
  private long window;

  public Camera camera;
  public Vector2f position;

  public Player(long window) {
    this.camera = new Camera(WindowSize.WIDTH, WindowSize.HEIGHT);
    this.window = window;
    this.position = new Vector2f();
  }

  public void move() {
    float velocity = 0.4f;
    if(GLFW.glfwGetKey(this.window, GLFW.GLFW_KEY_ESCAPE) == GLFW.GLFW_PRESS) {
      GLFW.glfwSetWindowShouldClose(window, true);
    }
    if(GLFW.glfwGetKey(this.window, GLFW.GLFW_KEY_W) == GLFW.GLFW_PRESS) {
      this.position.y += velocity;
      this.camera.setPositon(this.position.x, this.position.y);
    }
    if(GLFW.glfwGetKey(this.window, GLFW.GLFW_KEY_S) == GLFW.GLFW_PRESS) {
      this.position.y -= velocity;
      this.camera.setPositon(this.position.x, this.position.y);
    }
    if(GLFW.glfwGetKey(this.window, GLFW.GLFW_KEY_A) == GLFW.GLFW_PRESS) {
      this.position.x -= velocity;
      this.camera.setPositon(this.position.x, this.position.y);
    }
    if(GLFW.glfwGetKey(this.window, GLFW.GLFW_KEY_D) == GLFW.GLFW_PRESS) {
      this.position.x += velocity;
      this.camera.setPositon(this.position.x, this.position.y);
    }
  }
  
}
