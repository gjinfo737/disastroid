package com.aj.games.disastroid.ship;

import java.util.Random;

import android.util.Log;

public class Ship {
    private boolean isClockwise;
    private int health;
    private int angle; // Angle of the ship

    private final int START_HEALTH = 100;
    private final int ANGLE_MAX = 0;

    public Ship() {
	health = START_HEALTH;
	Random r = new Random();
	isClockwise = r.nextInt() % 2 == 0;
	angle = r.nextInt(ANGLE_MAX);
    }

    public Ship(boolean isClockwise, int angle) {
	health = START_HEALTH;
	this.isClockwise = isClockwise;
	this.angle = angle;
    }

    public void changeRotation() {
	isClockwise = !isClockwise;
    }

    public void updateAngle(int angle) {
	if (!isAngleInRange(angle)) {
	    Log.e(Ship.class.getName(), "Attempted to update to inappropriate angle " + angle);
	    return;
	}
	this.angle = angle;
    }

    public boolean isAngleInRange(int angle) {
	return (0 < angle && angle < ANGLE_MAX);
    }

    public void takeHit(int hitAmount) {
	health -= hitAmount;
	if (health <= 0) { /* TODO game over */
	}
    }

    public int getAngle() {
	return angle;
    }
}
