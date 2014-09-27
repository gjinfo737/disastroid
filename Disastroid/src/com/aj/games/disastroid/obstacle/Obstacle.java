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

	public final int DEFAULT_ZOOM_SPD = 1;

	public Obstacle(Point location, int diameter, Point center) {
		this.center = center;
		this.diameter = diameter;
		zoomSpeed = DEFAULT_ZOOM_SPD;
		zoomPct = 0;
	}

	public void onUpdate() {
		zoomPct += zoomSpeed;
	}

	public boolean hasPassed() {
		return (zoomPct >= 100);
	}

	public boolean isHittingShip(Ship ship) {
		if (zoomPct < 90) {
			return false;
		}

		return false;
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
}
