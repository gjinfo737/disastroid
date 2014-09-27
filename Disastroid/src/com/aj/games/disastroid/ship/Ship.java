package com.aj.games.disastroid.ship;

import java.util.Random;

import android.graphics.Point;

public class Ship {
	private Point center; // Location on screen of the center of the ship
	private boolean isClockwise; // Direction the ship is rotating
	private int health; // HP of the Ship
	private int angle; // Angle of the ship's left wing
	private int angularVelocity; // How many degrees the ship rotates on update
	private int wingLength; // How far the ship's wings extend from center

	private final int START_HEALTH = 100;
	private final int ANGLE_MAX = 360;
	private final int DEFAULT_ANG_VEL = 1;
	private final int WING_LENGTH = 40;

	public Ship() {
		Random r = new Random();

		this.health = START_HEALTH;
		this.isClockwise = r.nextInt() % 2 == 0;
		this.angle = r.nextInt(ANGLE_MAX);
		this.angularVelocity = DEFAULT_ANG_VEL;
		this.wingLength = WING_LENGTH;
		this.center = new Point(100, 100); //TODO How to get center?
	}

	public Ship(boolean isClockwise, int angle, Point center) {
		this.center = center;
		this.health = START_HEALTH;
		this.isClockwise = isClockwise;
		this.angle = angle;
		this.angularVelocity = DEFAULT_ANG_VEL;
		this.wingLength = WING_LENGTH;
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
	public Point getLeftWingTip() {
		return 
	}
	
	public Point getCenter() {
		return center;
	}
	
	public int getAngle() {
		return angle;
	}
	
	public int getWingLength() { 
		return wingLength;
	}

	/* SETTERS */
	public void setCenter(Point center) {
		this.center = center;
	}
	
	public void changeRotation() {
		isClockwise = !isClockwise;
	}

	public void setAngularVelocity(int angularVelocity) {
		this.angularVelocity = angularVelocity;
	}
	
	public void setWingLength(int wingLength) {
		this.wingLength = wingLength;
	}

}
