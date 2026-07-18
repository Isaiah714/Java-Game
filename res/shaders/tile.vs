#version 460 core

layout (location = 0) in vec3 tilePos;
layout (location = 1) in vec2 tileTex;

out vec2 atileTex;
out vec3 currentColor;
out vec3 currentPos;

uniform float scale;

uniform bool hasColor;
uniform bool hasScale;
uniform bool diffPos;

uniform vec2 position;
uniform vec3 color;

void main() {

  currentPos = tilePos;

  if(hasScale) currentPos *= scale;
  //if(hasColor) currentColor = 0;
  if(diffPos) {
    currentPos += position.x;
    currentPos += position.y;
  }

  gl_Position = vec4(currentPos, 1.0f);
  atileTex = tileTex;
}
