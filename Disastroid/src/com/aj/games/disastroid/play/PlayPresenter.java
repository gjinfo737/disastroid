package com.aj.games.disastroid.play;

import java.util.List;

import android.app.Activity;

import com.aj.games.disastroid.time.TickerTimer;
import com.aj.games.disastroid.time.TickerTimer.ITickerTimerListener;
import com.aj.games.disastroid.time.TickerTimer.TickInterval;

public class PlayPresenter implements ITickerTimerListener {

    private Activity activity;
    private PlayView view;

    public PlayPresenter(Activity activity) {
	this.activity = activity;
	this.view = new PlayView(this, activity);

    }

    @Override
    public void onTimerTick(List<TickInterval> intervals, int tick, long period) {
	if (TickerTimer.everySeconds(1f / 30f, tick, period)) {
	    // 30fps

	}
    }

}
