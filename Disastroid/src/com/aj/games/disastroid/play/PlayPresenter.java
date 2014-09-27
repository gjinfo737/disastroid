package com.aj.games.disastroid.play;

import java.util.List;

import android.app.Activity;
import android.widget.Toast;

import com.aj.games.disastroid.levels.Leveler;
import com.aj.games.disastroid.levels.Leveler.ILevelListener;
import com.aj.games.disastroid.ship.Ship;
import com.aj.games.disastroid.time.TickerTimer;
import com.aj.games.disastroid.time.TickerTimer.ITickerTimerListener;
import com.aj.games.disastroid.time.TickerTimer.TickInterval;

public class PlayPresenter implements ITickerTimerListener, ILevelListener {

    private static final long PERIOD = 33L;
    private Activity activity;

    private TickerTimer tickerTimer;
    private Ship ship;
    private PlayView view;
    private Leveler leveler;

    public PlayPresenter(Activity activity) {
	this.activity = activity;
	this.view = new PlayView(this, activity);
	this.tickerTimer = new TickerTimer(PERIOD);

	this.ship = new Ship();
	this.leveler = new Leveler(this);

	this.tickerTimer.registerNewListener(this);
	this.tickerTimer.registerNewListener(this.leveler);

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

    @Override
    public void onLevelUp(final int level) {
	activity.runOnUiThread(new Runnable() {
	    public void run() {
		Toast.makeText(activity, "Level up! " + level, Toast.LENGTH_SHORT).show();
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
	this.ship.changeRotation();
    }

}
