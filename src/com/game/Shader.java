package com.game;

import org.lwjgl.opengl.GL46;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

enum ShapeType {
  SQUARE,
  CIRCLE,
  TRIANGLE
}

class Shape {
  protected float[] vertices;
  protected int VAO;
  protected int VBO;
  protected int EBO;
  protected ShapeType shape;

  protected void createShapeData(ShapeType sh) {
    this.shape = sh;
    switch(this.shape) {
      case ShapeType.SQUARE:
      float[] sv = 
      {
        0.5f,  0.5f, 0.0f,   1.0f, 1.0f,
        0.5f, -0.5f, 0.0f,   1.0f, 0.0f,
       -0.5f, -0.5f, 0.0f,   0.0f, 0.0f,
       -0.5f,  0.5f, 0.0f,    0.0f, 0.1f
      };
      int[] si = 
      {
        0, 1, 3, 1, 2, 3
      };
      setVao(sv, si);
      break;
      case ShapeType.CIRCLE:
      float[] cv = 
      {
        -0.5f, -0.5f, 0.0f,
         0.5f, -0.5f, 0.0f,
         0.0f,  0.5f, 0.0f
      };
      int[] ci = 
      {
        0
      };
      setVao(cv, ci);
      break;
      case ShapeType.TRIANGLE:
      float[] tv =
      {
        -0.5f, -0.5f, 0.0f,
         0.5f, -0.5f, 0.0f,
         0.0f,  0.5f, 0.0f
      };
      int[] ti = 
      {
        0
      };
      setVao(tv, ti);
      break;
    }
  }

  private void setVao(float[] vertices, int[] indices) {
    int floatSize = 4;
    int stride = 5 * floatSize;
    VAO = GL46.glGenVertexArrays();
    GL46.glBindVertexArray(VAO);

    VBO = GL46.glGenBuffers();
    GL46.glBindBuffer(GL46.GL_ARRAY_BUFFER, VBO);
    GL46.glBufferData(GL46.GL_ARRAY_BUFFER, vertices, GL46.GL_STATIC_DRAW);

    EBO = GL46.glGenBuffers();
    GL46.glBindBuffer(GL46.GL_ELEMENT_ARRAY_BUFFER, EBO);
    GL46.glBufferData(GL46.GL_ELEMENT_ARRAY_BUFFER, indices, GL46.GL_STATIC_DRAW);

    GL46.glVertexAttribPointer(0, 3, GL46.GL_FLOAT, false, stride, 0);
    GL46.glEnableVertexAttribArray(0);

    GL46.glVertexAttribPointer(1, 2, GL46.GL_FLOAT, false, stride, 3 * floatSize);
    GL46.glEnableVertexAttribArray(1);
  }
}

public class Shader extends Shape {
  private final int shaderProgram;
  private int vertexShader;
  private int fragmentShader;

  public Shader(String vPath, String fPath, ShapeType shape) throws Exception {

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

    createShapeData(shape);

    //GL46.glDetachShader(shaderProgram, vertexShader);
    //GL46.glDetachShader(shaderProgram, fragmentShader);
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
    try(InputStream in = getClass().getResourceAsStream(shaderPath)) {
      if(in == null) {
        throw new FileNotFoundException("Shader source file not found" + shaderPath);
      }
      try(BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
        return reader.lines().collect(Collectors.joining("\n"));
      }
    }
  }

  private void unBind() {
    GL46.glUseProgram(0);
  }

  public void cleanup() {
    unBind();
    if(shaderProgram != 0) {
      GL46.glDeleteProgram(shaderProgram);
    }
  }

  public void changeColor(float red, float blue, float green) {
    int shaderLocation = GL46.glGetUniformLocation(shaderProgram, "tileColor");
    GL46.glUniform3f(shaderLocation, red, blue, green);
  }

  public void changePos(float x, float y) {
    int shaderLocation = GL46.glGetUniformLocation(shaderProgram, "position");
    GL46.glUniform3f(shaderLocation, x, y, 0);
  }

  public void draw() {
    GL46.glBindVertexArray(VAO);
    GL46.glUseProgram(shaderProgram);
    GL46.glDrawElements(GL46.GL_TRIANGLES, 6, GL46.GL_UNSIGNED_INT, 0);
  }

  public static Shader createShader(String vertexPath, String fragementPath, ShapeType type) {
    Shader shader = null;
    try {
      shader = new Shader(vertexPath, fragementPath, type);
    } catch(Exception e) {
      e.printStackTrace();
      System.exit(1);
    }
    return shader;
  }
}
