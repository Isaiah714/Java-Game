package com.game;

public class Player {
	public int health;
	public int attack;
	public int velocity;
	public int xPos, yPos;
	public LevelID currentLevel;
	
	private GameInput playerInput;
	
	public Player(GameInput input) {
		this.health = 100;
		this.attack = 100;
		this.velocity = 5;
		this.playerInput = input;
		this.currentLevel = LevelID.FLOWERFIELD; // Default level
	}
	
	public void movePlayer(GameScreen screen) {
		if(playerInput.up) { 
			yPos -= velocity;
			screen.yWorldPos = yPos - (Game.windowWidth / 2);
		}
		if(playerInput.down) {
			yPos += velocity;
			screen.yWorldPos = yPos - (Game.windowWidth / 2);
		}
		if(playerInput.left) {
			xPos -= velocity;
			screen.xWorldPos = xPos - (Game.windowHeight / 2);
		}
		if(playerInput.right) {
			xPos += velocity;
			screen.xWorldPos = xPos - (Game.windowHeight / 2);
		}
	}

}
