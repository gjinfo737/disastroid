package com.aj.games.disastroid.obstacle;

import android.graphics.Point;
import android.util.Log;

import com.aj.games.disastroid.ship.Ship;

/**
 * Obstacles are treated as circles of varying diameters
 * 
 * Hit detection is done by calculating the cone that is formed by the
 * obstacle's outer edges and the ship's center is the hit zone. If the ship's
 * wing's center is within that zone, it is considered a hit.
 */
public class Obstacle {
    private static final int NEAR = 70;
    private Point center;
    private int diameter;
    private float zoomPct;
    private float zoomSpeed;
    private float damage;

    public final float MIN_ZOOM_SPD = 1f;
    public final float MAX_ZOOM_SPD = 10f;
    public final int ANGLE_DELTA_FOR_HIT = 3;
    public final float DEFAULT_DMG = 1f;
    private float angle;
    private int level;

    public Obstacle(int diameter, Point center, float angle, int level) {
	this.center = center;
	this.diameter = diameter;
	this.angle = angle;
	this.level = level > 100 ? 100 : level;
	this.zoomSpeed = MIN_ZOOM_SPD + ((MAX_ZOOM_SPD - MIN_ZOOM_SPD) * (this.level / 100f));
	this.zoomPct = 0;
	this.damage = DEFAULT_DMG;
    }

    public void onUpdate() {
	zoomPct += zoomSpeed;
    }

    public boolean hasPassed() {

	return (zoomPct >= 100);
    }

    // Obstacles will always be set distance away from ship, so if the angle of
    // the ship's
    // left or right wing is within a delta of the center of the obstacle, that
    // means it is
    // colliding with that obstacle.
    public boolean isHittingShip(Ship ship) {
	if (!isNear()) {
	    return false;
	}

	float testAngle = this.angle;

	float lowAngle = cleanAngle(testAngle - ANGLE_DELTA_FOR_HIT);
	float highAngle = cleanAngle(testAngle + ANGLE_DELTA_FOR_HIT);

	int leftWingAngle = (int) cleanAngle(ship.getLeftWingAngle());
	int rightWingAngle = (int) cleanAngle(ship.getRightWingAngle());
	Log.i("", String.format("%s %s ", leftWingAngle, rightWingAngle));
	// Here be dragons
	if (testAngle < ANGLE_DELTA_FOR_HIT) {
	    if (leftWingAngle >= 0 && leftWingAngle <= ANGLE_DELTA_FOR_HIT) {
		return true;
	    } else if (rightWingAngle >= 0 && rightWingAngle <= ANGLE_DELTA_FOR_HIT) {
		return true;
	    } else {
		return false;
	    }
	} else if (testAngle > 360 - ANGLE_DELTA_FOR_HIT) {
	    if (leftWingAngle >= 360 - ANGLE_DELTA_FOR_HIT && leftWingAngle <= 360) {
		return true;
	    } else if (rightWingAngle >= 360 && rightWingAngle <= 360) {
		return true;
	    } else {
		return false;
	    }
	} else {
	    if (leftWingAngle >= lowAngle && leftWingAngle <= highAngle)
		return true;
	    if (rightWingAngle >= lowAngle && rightWingAngle <= highAngle)
		return true;
	    else
		return false;
	}
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

    public float getZoomPct() {
	return zoomPct;
    }

    public float getDamage() {
	return damage;
    }

    /* SETTERS */
    public void setCenter(Point center) {
	this.center = center;
    }

    public void setDiameter(int diameter) {
	this.diameter = diameter;
    }

    public void setDamage(int damage) {
	this.damage = damage;
    }

    public boolean isNear() {
	return zoomPct >= NEAR;
    }

    public float getAngle() {
	return angle;
    }
}
