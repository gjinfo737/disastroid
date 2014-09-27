package com.aj.games.disastroid.levels;

import java.util.List;

import com.aj.games.disastroid.time.TickerTimer;
import com.aj.games.disastroid.time.TickerTimer.ITickerTimerListener;
import com.aj.games.disastroid.time.TickerTimer.TickInterval;

public class Leveler implements ITickerTimerListener {

    private static final int SECONDS_BETWEEN_LEVEL_INCREMENT = 10;
    private int level = 1;
    private int playSeconds;

    @Override
    public void onTimerTick(List<TickInterval> intervals, int tick, long period) {
	if (TickerTimer.everySeconds(SECONDS_BETWEEN_LEVEL_INCREMENT, tick, period)) {
	    playSeconds++;
	}
	if (shouldLevelUp()) {
	    levelUp();
	}
    }

    private boolean shouldLevelUp() {
	return playSeconds >= SECONDS_BETWEEN_LEVEL_INCREMENT;
    }

    private void levelUp() {
	level++;
	playSeconds = 0;
    }

}
