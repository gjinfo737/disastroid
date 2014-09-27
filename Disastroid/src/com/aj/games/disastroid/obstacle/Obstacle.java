package com.aj.games.disastroid.obstacle;

import com.aj.games.disastroid.ship.Ship;

/**
 * Obstacles are treated as circles of varying diameters
 *
 * Hit detection is done by calculating the cone that is formed by the
 * obstacle's outer edges and the ship's center is the hit zone.
 * If the ship's wing's center is within that zone, it is considered a hit. 
 */
public class Obstacle {
	private int x;
	private int y; 
	private int diameter;
	private int zoomPct;
	
	public Obstacle(int x, int y, int diameter) {
		this.x = x;
		this.y = y;
		this.diameter = diameter;
		zoomPct = 0;
	}

	public boolean isHittingShip(Ship ship) {
		if (zoomPct < 90) { return false; }
		return false;
	}
	
}
