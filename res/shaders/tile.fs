#version 460 core

out vec4 FragColor;

in vec2 atileTex;

uniform vec3 tileColor;
uniform sampler2D tileTexture; 

void main() {
  FragColor = texture(tileTexture, atileTex);
}
