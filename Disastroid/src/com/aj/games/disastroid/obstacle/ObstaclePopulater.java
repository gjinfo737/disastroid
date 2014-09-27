package com.aj.games.disastroid.obstacle;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;
import android.widget.Toast;

import com.aj.games.disastroid.MainActivity;
import com.aj.games.disastroid.levels.Leveler.ILevelListener;
import com.aj.games.disastroid.time.TickerTimer;
import com.aj.games.disastroid.time.TickerTimer.ITickerTimerListener;
import com.aj.games.disastroid.time.TickerTimer.TickInterval;

public class ObstaclePopulater implements ITickerTimerListener, ILevelListener {

	private List<Obstacle> obstacles = new ArrayList<Obstacle>();
	private static final float SAFE_ANGLE_PADDING = 15;
	private int level;
	private Rect populationRect;
	private float radius;
	private SafeArea safeArea;
	private float safeAreaChangePeriod = 10;
	private float populatePeriod = .5f;
	private float chanceOfPopulate = .1f;

	public ObstaclePopulater(Rect populationRect) {
		this.populationRect = populationRect;
		caculateRadius();
		createSafeArea();
	}

	private void caculateRadius() {
		int smallestDimmension = this.populationRect.width() > this.populationRect
				.height() ? this.populationRect.height() : this.populationRect
				.width();
		radius = (float) smallestDimmension / 4f;
	}

	private void createSafeArea() {
		safeArea = new SafeArea(SAFE_ANGLE_PADDING);
	}

	@Override
	public void onTimerTick(List<TickInterval> intervals, int tick, long period) {
		for (Obstacle ob : obstacles) {
			ob.onUpdate();
		}

		if (TickerTimer.everySeconds(safeAreaChangePeriod, tick, period)) {
			createSafeArea();
		}

		if (TickerTimer.everySeconds(populatePeriod, tick, period)) {
			if (Math.random() < chanceOfPopulate) {
				populate();
			}
			while (!cleanObstaclesList()) {
			}
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
		obstacles.add(new Obstacle((int) radius, createRandomPointOnCircle()));
	}

	private Point createRandomPointOnCircle() {
		Point point = new Point();
		float angle = (float) (Math.random() * 360f);
		while (this.safeArea.isInArea(angle)) {
			angle = (float) (Math.random() * 360f);
		}

		point.x = (int) (radius * Math.cos(angle)) + populationRect.centerX();
		point.y = (int) (radius * Math.sin(angle)) + populationRect.centerY();

		return point;
	}

	@Override
	public void onLevelUp(int level) {
		this.level = level;
		chanceOfPopulate = (float) (0.1 * level);
		
		if (chanceOfPopulate > 0.9) { chanceOfPopulate = 0.9f; }
		Log.i("Level", "Levelled up to level " + level);
		Log.i("Level", "Chance of population is now " + chanceOfPopulate);
	}

	public List<Obstacle> getObstacles() {
		return obstacles;
	}

}
