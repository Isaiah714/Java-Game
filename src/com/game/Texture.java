package com.game;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;
import org.joml.Vector2i;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL46;

class TextureRegion {
  public float u1, u2, v1, v2;

  public void updateRegion(Vector2i spriteSize, Vector2i sheetSize, Vector2i sprite) throws Exception {
    // Converting pixel position to UV space (UV space ranges 0-1)
    this.u1 = (float) (sprite.x * spriteSize.x) / sheetSize.x; // sprite.x is pixel position for x
    this.v1 = (float) (sprite.y * spriteSize.y) / sheetSize.y; // spirte.y is pixel position for y
    this.u2 = this.u1 + ((float) spriteSize.x / sheetSize.x);   // spriteSize.x sheetSize.x is width
    this.v2 = this.v1 + ((float) spriteSize.y / sheetSize.y);   // spriteSize.y sheetSize.y is height
  }
}

public class Texture extends TextureRegion {
  private final int SHEET_SIZE = 160;
  private final int SPRITE_SIZE = 16;
  private Vector2i sprite;
  private String texturePath;

  public int ID;
  public Vector2i spriteSize;
  public Vector2i sheetSize;

  public Texture(String tPath, Vector2i sprite) throws Exception {
    this.spriteSize = new Vector2i(SPRITE_SIZE, SPRITE_SIZE);
    this.sheetSize = new Vector2i(SHEET_SIZE, SHEET_SIZE);
    this.sprite = sprite;
    this.texturePath = tPath;

    updateRegion(this.spriteSize, this.sheetSize, this.sprite);

    ByteBuffer imageBuffer = ioResourceToByteBuffer(tPath);
    try(MemoryStack stack = MemoryStack.stackPush()) {
      IntBuffer w = stack.mallocInt(1);
      IntBuffer h = stack.mallocInt(1);
      IntBuffer channels = stack.mallocInt(1);

      STBImage.stbi_set_flip_vertically_on_load(false);

      ByteBuffer buf = STBImage.stbi_load_from_memory(imageBuffer, w, h, channels, 4);
      if(buf == null) {
        throw new RuntimeException("Texture file of path: " + this.texturePath + " couldn't be found");
      }

      int width = w.get(0);
      int height = h.get(0);

      generateTexture(width, height, buf);

      STBImage.stbi_image_free(buf);
    }
  }

  public void bind() {
    GL46.glBindTexture(GL46.GL_TEXTURE_2D, ID);
  }

  public void clean() {
    GL46.glDeleteTextures(ID);
  }

  private void generateTexture(int w, int h, ByteBuffer b) {
    ID = GL46.glGenTextures();

    GL46.glBindTexture(GL46.GL_TEXTURE_2D, ID);
    GL46.glPixelStorei(GL46.GL_UNPACK_ALIGNMENT, 1);
    GL46.glTexParameteri(GL46.GL_TEXTURE_2D, GL46.GL_TEXTURE_WRAP_S, GL46.GL_REPEAT);
    GL46.glTexParameteri(GL46.GL_TEXTURE_2D, GL46.GL_TEXTURE_WRAP_T, GL46.GL_REPEAT);
    GL46.glTexParameteri(GL46.GL_TEXTURE_2D, GL46.GL_TEXTURE_MIN_FILTER, GL46.GL_NEAREST);
    GL46.glTexParameteri(GL46.GL_TEXTURE_2D, GL46.GL_TEXTURE_MAG_FILTER, GL46.GL_NEAREST);
    GL46.glTexImage2D(GL46.GL_TEXTURE_2D, 0, GL46.GL_RGBA, w, h, 0, GL46.GL_RGBA, GL46.GL_UNSIGNED_BYTE, b);
    GL46.glGenerateMipmap(GL46.GL_TEXTURE_2D);


  }

  private ByteBuffer ioResourceToByteBuffer(String resPath) throws IOException {
    ByteBuffer buffer;
    try(InputStream source = getClass().getResourceAsStream(resPath)) {
      if(source == null) {
        throw new IOException("Resource not found on classpath: " + resPath);
      }

      byte[] bytes = source.readAllBytes();

      buffer = BufferUtils.createByteBuffer(bytes.length);
      buffer.put(bytes);
      buffer.flip();
    }
    return buffer;
  }
}
