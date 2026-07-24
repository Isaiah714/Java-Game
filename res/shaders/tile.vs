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

uniform mat4 viewProj;

void main() {

  currentPos = tilePos;

  if(hasScale) currentPos *= scale;
  if(diffPos) {
    currentPos.xy += position;
  }

  gl_Position = viewProj * vec4(currentPos, 1.0f);
  atileTex = tileTex;
}
