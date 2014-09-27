package com.aj.games.disastroid.ship;

import java.util.Random;

public class Ship {
	private boolean isClockwise; // Direction the ship is rotating
	private int health; // HP of the Ship
	private int angle; // Angle of the ship
	private int angularVelocity; // How many degrees the ship rotates on update

	private final int START_HEALTH = 100;
	private final int ANGLE_MAX = 360;
	private final int DEFAULT_ANG_VEL = 1;

	public Ship() {
		Random r = new Random();

		this.health = START_HEALTH;
		this.isClockwise = r.nextInt() % 2 == 0;
		this.angle = r.nextInt(ANGLE_MAX);
		this.angularVelocity = DEFAULT_ANG_VEL;
	}

	public Ship(boolean isClockwise, int angle) {
		health = START_HEALTH;
		this.isClockwise = isClockwise;
		this.angle = angle;
		this.angularVelocity = DEFAULT_ANG_VEL;
	}

	/* UPDATES */
	public void onUpdate() {
		angle = isClockwise ? (angle - angularVelocity)
				: (angle + angularVelocity);
		// Check for wrap-around (If the end of the ship we are tracking moves 1st or 2nd quad)
		if (angle < 0) {
			angle = ANGLE_MAX - angle;
		} else if (angle > ANGLE_MAX) {
			angle = angle - ANGLE_MAX;
		}
	}

	public void takeHit(int hitAmount) {
		health -= hitAmount;
		if (health <= 0) { /* TODO game over */ }
	}

	/* GETTERS */
	public int getAngle() {
		return angle;
	}

	/* SETTERS */
	public void changeRotation() {
		isClockwise = !isClockwise;
	}

	public void setAngularVelocity(int angularVelocity) {
		this.angularVelocity = angularVelocity;
	}

}
