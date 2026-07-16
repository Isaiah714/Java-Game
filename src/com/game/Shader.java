package com.game;

import org.lwjgl.opengl.GL46;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

enum ShapeType {
  SQUARE,
  CIRCLE,
  TRIANGLE
}

class Shape {
  protected float[] vertices;
  protected int VAO;
  protected int VBO;
  protected ShapeType shape;

  Shape(ShapeType sh) {
    this.shape = sh;
    switch(this.shape) {
      case ShapeType.SQUARE:
      float[] sv = 
      {
        0.5f,  0.5f, 0.0f,   1.0f, 1.0f,
        0.5f, -0.5f, 0.0f,  1.0f, 0.0f,
       -0.5f, -0.5f, 0.0f,  0.0f, 0.0f,
       -0.5f,  0.5f, 0.0f,   0.0f, 1.0f
      };
      break;
      case ShapeType.CIRCLE:
      float[] cv = 
      {
        -0.5f, -0.5f, 0.0f,
         0.5f, -0.5f, 0.0f,
         0.0f,  0.5f, 0.0f
      };
      break;
      case ShapeType.TRIANGLE:
      float[] tv =
      {
        -0.5f, -0.5f, 0.0f,
         0.5f, -0.5f, 0.0f,
         0.0f,  0.5f, 0.0f
      };
      break;
    }
  }
}

public class Shader extends Shape {
  private final int shaderProgram;
  private int vertexShader;
  private int fragmentShader;

  public Shader(String vPath, String fPath, ShapeType shape) throws Exception {
    super(shape);

    vertexShader = createShader(loadShaderSource(vPath), GL46.GL_VERTEX_SHADER);
    fragmentShader = createShader(loadShaderSource(fPath), GL46.GL_FRAGMENT_SHADER);
    shaderProgram = GL46.glCreateProgram();

    if(shaderProgram == 0) {
      throw new Exception("Couldn't create shader program");
    }
    GL46.glAttachShader(shaderProgram, vertexShader);
    GL46.glAttachShader(shaderProgram, fragmentShader);
    GL46.glLinkProgram(shaderProgram);

    if(GL46.glGetProgrami(shaderProgram, GL46.GL_LINK_STATUS) == GL46.GL_FALSE) {
      throw new Exception("Error linking shader program: " + GL46.glGetProgramInfoLog(shaderProgram, 1024));
    }

    GL46.glDetachShader(shaderProgram, vertexShader);
    GL46.glDetachShader(shaderProgram, fragmentShader);
    GL46.glDeleteShader(vertexShader);
    GL46.glDeleteShader(fragmentShader);
  }

  private int createShader(String shaderCode, int shaderType) throws Exception {
    int shaderID = GL46.glCreateShader(shaderType);
    if(shaderID == 0) {
      throw new Exception("Failed to create shader ID of shader type" + shaderType);
    }
    GL46.glShaderSource(shaderID, shaderCode);
    GL46.glCompileShader(shaderID);

    if(GL46.glGetShaderi(shaderID, GL46.GL_COMPILE_STATUS) == GL46.GL_FALSE) {
      throw new Exception("Error compiling shader code: " +
                                        GL46.glGetShaderInfoLog(shaderID, 1024));
    }
    return shaderID;
  }

  private String loadShaderSource(String shaderPath) throws IOException {
    return new String(Files.readAllBytes(Paths.get(shaderPath)));
  }

  private void unBind() {
    GL46.glUseProgram(0);
  }

  public void bind() {
    GL46.glUseProgram(shaderProgram);
  }

  public void cleanup() {
    unBind();
    if(shaderProgram != 0) {
      GL46.glDeleteProgram(shaderProgram);
    }
  }

}
