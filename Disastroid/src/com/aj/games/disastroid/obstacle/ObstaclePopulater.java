package com.aj.games.disastroid.obstacle;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Rect;

import com.aj.games.disastroid.levels.Leveler.ILevelListener;
import com.aj.games.disastroid.time.TickerTimer.ITickerTimerListener;
import com.aj.games.disastroid.time.TickerTimer.TickInterval;

public class ObstaclePopulater implements ITickerTimerListener, ILevelListener {

    private List<Obstacle> obstacles = new ArrayList<Obstacle>();
    private int level;
    private Rect populationRect;

    public ObstaclePopulater(Rect populationRect) {
	this.populationRect = populationRect;
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
