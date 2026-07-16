package com.game;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.opengl.GL46;

//https://ahbejarano.gitbook.io/lwjglgamedev/chapter-07

public class Texture {
  private String texturePath;
  private int textureID;
  
  public Texture(String tPath) {
    try(MemoryStack stack = MemoryStack.stackPush()) {
      this.texturePath = tPath;
      IntBuffer w = stack.mallocInt(1);
      IntBuffer h = stack.mallocInt(1);
      IntBuffer channels = stack.mallocInt(1);

      ByteBuffer buf = STBImage.stbi_load(this.texturePath, w, h, channels, 0);
      if(buf == null) {
        throw new RuntimeException("Texture file of path: " + this.texturePath + " couldn't be loaded");
      }

      int width = w.get();
      int height = w.get();

      generateTexture(width, height, buf);

      STBImage.stbi_image_free(buf);
    }
  }

  public void bind() {
    GL46.glDeleteTextures(textureID);
  }

  public void clean() {
    GL46.glBindTexture(GL46.GL_TEXTURE_2D, textureID);
  }

  private void generateTexture(int w, int h, ByteBuffer b) {
    textureID = GL46.glGenTextures();

    GL46.glBindTexture(GL46.GL_TEXTURE_2D, textureID);
    GL46.glPixelStorei(GL46.GL_UNPACK_ALIGNMENT, 1);
    GL46.glTexParameteri(GL46.GL_TEXTURE_2D, GL46.GL_TEXTURE_MIN_FILTER, GL46.GL_NEAREST);
    GL46.glTexParameteri(GL46.GL_TEXTURE_2D, GL46.GL_TEXTURE_MAG_FILTER, GL46.GL_NEAREST);
    GL46.glTexImage2D(GL46.GL_TEXTURE_2D, 0, GL46.GL_RGBA, w, h, 0, GL46.GL_RGBA, GL46.GL_UNSIGNED_BYTE, b);
    GL46.glGenerateMipmap(GL46.GL_TEXTURE_2D);
  }
}
