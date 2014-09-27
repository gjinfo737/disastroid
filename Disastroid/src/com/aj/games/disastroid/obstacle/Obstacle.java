package com.aj.games.disastroid.obstacle;

import android.graphics.Point;

import com.aj.games.disastroid.ship.Ship;

/**
 * Obstacles are treated as circles of varying diameters
 * 
 * Hit detection is done by calculating the cone that is formed by the
 * obstacle's outer edges and the ship's center is the hit zone. If the ship's
 * wing's center is within that zone, it is considered a hit.
 */
public class Obstacle {
	private Point center;
	private int diameter;
	private int zoomPct;
	private int zoomSpeed;
	private int damage;

	public final int DEFAULT_ZOOM_SPD = 1;
	public final int ANGLE_DELTA_FOR_HIT = 5; //TODO Calculate based on diameter?
	public final int DEFAULT_DMG = 10;
	
	public Obstacle(int diameter, Point center) {
		this.center = center;
		this.diameter = diameter;
		this.zoomSpeed = DEFAULT_ZOOM_SPD;
		this.zoomPct = 0;
		this.damage = DEFAULT_DMG;
	}

	public void onUpdate() {
		zoomPct += zoomSpeed;
	}

	public boolean hasPassed() {
		return (zoomPct >= 100);
	}

	// Obstacles will always be set distance away from ship, so if the angle of the ship's
	// left or right wing is within a delta of the center of the obstacle, that means it is 
	// colliding with that obstacle. 
	public boolean isHittingShip(Ship ship) {
		if (zoomPct < 90) {
			return false;
		}

		int angleWRespectToShip = (int)(center.y - ship.getCenter().y) / 
				(center.x - ship.getCenter().x);
		int lowAngle = angleWRespectToShip - ANGLE_DELTA_FOR_HIT;
		int highAngle = angleWRespectToShip + ANGLE_DELTA_FOR_HIT;
		
		return ((ship.getLeftWingAngle() >= lowAngle && ship.getLeftWingAngle() <= highAngle) ||
			    (ship.getRightWingAngle() >= lowAngle && ship.getRightWingAngle() <= highAngle));
	}

	/* GETTERS */
	public int getX() {
		return center.x;
	}

	public int getY() {
		return center.y;
	}

	public Point getCenter() {
		return center;
	}

	public int getDiameter() {
		return diameter;
	}

	public int getZoomPct() {
		return zoomPct;
	}
	
	public int getDamage() { 
		return damage;
	}

	/* SETTERS */
	public void setCenter(Point center) {
		this.center = center;
	}

	public void setDiameter(int diameter) {
		this.diameter = diameter;
	}

	public void setZoomPct(int zoomPct) {
		this.zoomPct = zoomPct;
	}
	
	public void setDamage(int damage) {
		this.damage = damage;
	}
}
