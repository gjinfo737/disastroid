package com.aj.games.disastroid.obstacle;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Rect;

import com.aj.games.disastroid.levels.Leveler.ILevelListener;
import com.aj.games.disastroid.time.TickerTimer.ITickerTimerListener;
import com.aj.games.disastroid.time.TickerTimer.TickInterval;

public class ObstaclePopulater implements ITickerTimerListener, ILevelListener {

    private List<Obstacle> obstacles = new ArrayList<Obstacle>();
    private static final float SAFE_ANGLE_PADDING = 15;
    private int level;
    private Rect populationRect;
    private float radius;
    private SafeArea safeArea;

    public ObstaclePopulater(Rect populationRect) {
	this.populationRect = populationRect;
	caculateRadius();

    }

    private void caculateRadius() {
	int smallestDimmension = this.populationRect.width() > this.populationRect.height() ? this.populationRect.height() : this.populationRect
		.width();
	radius = (float) smallestDimmension / 4f;
    }

    private void createSafeArea() {
	safeArea = new SafeArea(SAFE_ANGLE_PADDING);
    }

    @Override
    public void onTimerTick(List<TickInterval> intervals, int tick, long period) {
	// TODO Auto-generated method stub

    }

    @Override
    public void onLevelUp(int level) {
	this.level = level;

    }

}
