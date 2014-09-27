package com.aj.games.disastroid.play;

import java.util.List;

import android.app.Activity;

import com.aj.games.disastroid.ship.Ship;
import com.aj.games.disastroid.time.TickerTimer;
import com.aj.games.disastroid.time.TickerTimer.ITickerTimerListener;
import com.aj.games.disastroid.time.TickerTimer.TickInterval;

public class PlayPresenter implements ITickerTimerListener {

    private static final long PERIOD = 33L;
    private Activity activity;

    private TickerTimer tickerTimer;
    private Ship ship;
    private PlayView view;

    public PlayPresenter(Activity activity) {
	this.activity = activity;
	this.view = new PlayView(this, activity);
	this.tickerTimer = new TickerTimer(PERIOD);
	this.tickerTimer.registerNewListener(this);

	this.ship = new Ship();

    }

    @Override
    public void onTimerTick(List<TickInterval> intervals, int tick, long period) {
	ship.onUpdate();

	activity.runOnUiThread(new Runnable() {
	    public void run() {
		view.update(ship);
	    }
	});
    }

    public void onResume() {
	this.tickerTimer.start();
    }

    public void onPause() {
	this.tickerTimer.stop();
    }

    public void onTap() {
	// TODO Auto-generated method stub
    }

}
