package com.game;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class Camera {
  private Vector2f position;
  private float zoom;

  private Matrix4f projection;
  private Matrix4f view;
  private Matrix4f inverseViewProj;

  private int width;
  private int height;

  public Camera(int width, int height) {
    this.width = width;
    this.height = height;
    this.position = new Vector2f();
    this.zoom = 8.0f;

    this.projection = new Matrix4f();
    this.view = new Matrix4f();
    this.inverseViewProj = new Matrix4f();

    updateProjection(width, height);
    updateView();
  }

  public void updateProjection(int width, int height) {
    this.width = width;
    this.height = height;

    float hW = width / 2.0f;
    float hH = height / 2.0f;
    projection.identity().ortho(-hW, hW, -hH, hH, -1.0f, 1.0f);
  }

  public void updateView() {
    view.identity()
    .scale(zoom, zoom, 1.0f)
    .translate(-position.x, -position.y, 0.0f);

    inverseViewProj.set(projection).mul(view).invert();
  }

  public Vector2f screenToWorld(float screenX, float screenY) {
    float ndcX = (2.0f * screenX) / this.width - 1.0f;
    float ndcY = (2.0f * screenY) / height;

    Vector3f world2D = new Vector3f(ndcX, ndcY, 0.0f);
    inverseViewProj.transformProject(world2D);

    return new Vector2f(world2D.x, world2D.y);
  }

  public void setPositon(float x, float y) {
    this.position.set(x, y);
    updateView();
  }

  public Matrix4f getProjectionMatrix() {
    return projection;
  }

  public Matrix4f getViewMatrix() {
    return view;
  }
}
