package com.aj.games.disastroid.obstacle;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Point;
import android.graphics.Rect;

import com.aj.games.disastroid.levels.Leveler.ILevelListener;
import com.aj.games.disastroid.time.TickerTimer;
import com.aj.games.disastroid.time.TickerTimer.ITickerTimerListener;
import com.aj.games.disastroid.time.TickerTimer.TickInterval;

public class ObstaclePopulater implements ITickerTimerListener, ILevelListener {

    private List<Obstacle> obstacles = new ArrayList<Obstacle>();
    private static final float SAFE_ANGLE_PADDING = 25;
    private int level;
    private Rect populationRect;
    private float radius;
    private SafeArea safeArea;
    private float safeAreaChangePeriod = 20;
    private float populatePeriod = .5f;
    private float chanceOfPopulate = 1f;
    private int countDownToDanger;

    public ObstaclePopulater(Rect populationRect) {
	this.populationRect = populationRect;
	caculateRadius();
	createSafeArea();
    }

    private void caculateRadius() {
	int smallestDimmension = this.populationRect.width() > this.populationRect.height() ? this.populationRect.height() : this.populationRect
		.width();
	radius = (float) smallestDimmension / 4f;
    }

    private void createSafeArea() {
	safeArea = new SafeArea(SAFE_ANGLE_PADDING);
	countDownToDanger = 5;
    }

    @Override
    public void onTimerTick(List<TickInterval> intervals, int tick, long period) {
	for (Obstacle ob : obstacles) {
	    ob.onUpdate();
	}

	if (countDownToDanger <= 0) {
	    if (TickerTimer.everySeconds(safeAreaChangePeriod, tick, period)) {
		createSafeArea();
	    }
	    if (TickerTimer.everySeconds(populatePeriod, tick, period)) {
		if (Math.random() < chanceOfPopulate) {
		    populate();
		}

	    }
	} else if (TickerTimer.everySeconds(1, tick, period)) {
	    countDownToDanger--;
	}

	while (!cleanObstaclesList()) {
	}
    }

    private boolean cleanObstaclesList() {
	if (obstacles == null)
	    return true;
	if (obstacles.size() == 0) {
	    return true;
	}

	int removeIndex = -1;
	for (int i = 0; i < obstacles.size(); i++) {
	    Obstacle ob = obstacles.get(i);
	    if (ob.hasPassed()) {
		removeIndex = i;
		break;
	    }
	}
	if (removeIndex != -1) {
	    obstacles.remove(removeIndex);
	    return false;
	} else {
	    return true;
	}

    }

    private void populate() {
	float angle = (float) (Math.random() * 360f);
	obstacles.add(new Obstacle((int) radius, createRandomPointOnCircle(angle), angle, level));
    }

    private Point createRandomPointOnCircle(float angle) {
	Point point = new Point();

	while (this.safeArea.isInArea(angle)) {
	    angle = (float) (Math.random() * 360f);
	}

	point.x = (int) (radius * Math.cos(toRadians(angle))) + populationRect.centerX();
	point.y = (int) ((int) populationRect.centerY() - (radius * Math.sin(toRadians(angle))));
	return point;
    }

    private float toRadians(float angle) {
	return (float) (angle * Math.PI / 180f);
    }

    @Override
    public void onLevelUp(int level) {
	this.level = level;

    }

    public List<Obstacle> getObstacles() {
	return obstacles;
    }

}
