package com.aj.games.disastroid.ship;

public class Ship {
	private boolean isClockwise;
	private float rate;
	private int health;
	
	private final int START_HEALTH = 100;
	
	public Ship(boolean isClockwise, float rate) {
		health = START_HEALTH;
		this.isClockwise = isClockwise;
		this.rate = rate;
	}
	
	public void changeRotation() {
		isClockwise = !isClockwise;
	}
	
	public void onUpdate() {
		
	}
}
