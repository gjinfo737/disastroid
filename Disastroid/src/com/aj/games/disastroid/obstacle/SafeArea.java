package com.aj.games.disastroid.obstacle;

public class SafeArea {

    private float safeAngle;
    private float[] safetyAngles;

    public SafeArea(float safeAnglePadding) {
	safeAngle = (float) (Math.random() * 180f);
	safetyAngles = new float[] { safeAngle - safeAnglePadding, safeAngle + safeAnglePadding, 0, 0 };
	safetyAngles[2] = safetyAngles[0] + 180f;
	safetyAngles[3] = safetyAngles[1] + 180f;

	for (int i = 0; i < safetyAngles.length; i++) {
	    if (safetyAngles[i] > 360) {
		safetyAngles[i] -= 360;
	    }
	}

    }

    public boolean isInArea(float angle) {
	if (angle > safetyAngles[0] && angle < safetyAngles[1]) {
	    return true;
	} else if (angle > safetyAngles[2] && angle < safetyAngles[3]) {
	    return true;
	}
	return false;
    }
}
