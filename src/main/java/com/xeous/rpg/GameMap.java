package com.xeous.rpg;

public class GameMap {
	private GameEvent[][] gameMap;
	private int size;
	
	public GameMap(int n){
		size = n;
		gameMap = new GameEvent[n][n];
	}

	public GameMap(){
		size = 8;
		gameMap = new GameEvent[10][10];
	}
	
	public GameEvent getRandomEvent(GameChar gameChar){
		return new GameEvent();
	}
	
}
