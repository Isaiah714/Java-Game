#version 460 core

layout (location = 0) in vec3 tilePos;
layout (location = 1) in vec2 tileTex;

out vec2 atileTex;
out vec3 color;

void main() {
  gl_Position = vec4(tilePos, 1.0f);
  atileTex = tileTex;
}
