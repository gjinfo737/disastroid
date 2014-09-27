package com.aj.games.disastroid.play;

import java.util.List;

import android.app.Activity;
import android.util.Log;

import com.aj.games.disastroid.ship.Ship;
import com.aj.games.disastroid.time.TickerTimer;
import com.aj.games.disastroid.time.TickerTimer.ITickerTimerListener;
import com.aj.games.disastroid.time.TickerTimer.TickInterval;
import com.aj.games.disastroid.views.ShipView;

public class PlayPresenter implements ITickerTimerListener {

    private static final long PERIOD = 33L;
    private Activity activity;
    private PlayView view;
    private TickerTimer tickerTimer;
    private ShipView shipView;

    public PlayPresenter(Activity activity) {
	this.activity = activity;
	this.view = new PlayView(this, activity);
	this.tickerTimer = new TickerTimer(PERIOD);
	this.tickerTimer.registerNewListener(this);

	shipView = new ShipView(this.activity);
    }

    @Override
    public void onTimerTick(List<TickInterval> intervals, int tick, long period) {
	Log.i("s", "s" + Math.random());
	this.shipView.render(new Ship());
    }

    public void onResume() {
	this.tickerTimer.start();
    }

    public void onPause() {
	this.tickerTimer.stop();
    }

}
