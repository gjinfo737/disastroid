package com.aj.games.disastroid.ship;

import java.util.Random;

import android.graphics.Point;

import com.aj.games.disastroid.obstacle.Obstacle;

public class Ship {
    private Point center; // Location on screen of the center of the ship
    private boolean isClockwise; // Direction the ship is rotating
    private float health; // HP of the Ship
    private int leftWingAngle; // Angle of the ship's left wing
    private int angularVelocity; // How many degrees the ship rotates on update
    private int wingLength; // How far the ship's wings extend from center

    private final int START_HEALTH = 100;
    private final int ANGLE_MAX = 360;
    private final int DEFAULT_ANG_VEL = 3;
    private final int WING_LENGTH = 40;

    public Ship() {
	Random r = new Random();
	this.health = START_HEALTH;
	this.isClockwise = r.nextInt() % 2 == 0;
	this.leftWingAngle = r.nextInt(ANGLE_MAX);
	this.angularVelocity = DEFAULT_ANG_VEL;
	this.wingLength = WING_LENGTH;
	this.center = new Point(100, 100); // TODO How to get center?
    }

    public Ship(boolean isClockwise, int angle, Point center) {
	this.center = center;
	this.health = START_HEALTH;
	this.isClockwise = isClockwise;
	this.leftWingAngle = angle;
	this.angularVelocity = DEFAULT_ANG_VEL;
	this.wingLength = WING_LENGTH;
    }

    public void takeHit(Obstacle obstacle) {
	health -= obstacle.getDamage();
	if (health <= 0) { /* TODO game over */
	}
    }

    /* UPDATES */
    public void onUpdate() {
	leftWingAngle = isClockwise ? (leftWingAngle - angularVelocity) : (leftWingAngle + angularVelocity);
	leftWingAngle = (int) cleanAngle(leftWingAngle);
    }

    private float cleanAngle(float angle) {
	while (angle < 0) {
	    angle += 360;
	}
	while (angle >= 360) {
	    angle -= 360;
	}
	return angle;
    }

    /* GETTERS */
    public Point getCenter() {
	return center;
    }

    public int getLeftWingAngle() {
	return leftWingAngle;
    }

    public int getRightWingAngle() {
	return (leftWingAngle + 180) % 360;
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
