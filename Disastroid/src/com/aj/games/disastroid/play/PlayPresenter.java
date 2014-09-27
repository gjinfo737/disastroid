package com.aj.games.disastroid.play;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.Display;
import android.widget.Toast;

import com.aj.games.disastroid.levels.Leveler;
import com.aj.games.disastroid.obstacle.Obstacle;
import com.aj.games.disastroid.obstacle.ObstaclePopulater;
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
    private Leveler leveler;
    private ObstaclePopulater obstaclePopulater;

    public PlayPresenter(Activity activity) {
	this.activity = activity;
	this.view = new PlayView(this, activity);
	this.tickerTimer = new TickerTimer(PERIOD);

	Toast.makeText(this.activity, "Loading...", Toast.LENGTH_LONG).show();
	initializeAfterDelay();
    }

    private void initializeAfterDelay() {
	Timer t = new Timer();
	t.schedule(new TimerTask() {
	    public void run() {
		activity.runOnUiThread(new Runnable() {
		    public void run() {
			initialize();
		    }
		});
	    }
	}, 3000);
    }

    private void initialize() {
	Display display = this.activity.getWindowManager().getDefaultDisplay();
	Point size = new Point();
	display.getSize(size);

	this.obstaclePopulater = new ObstaclePopulater(new Rect(0, 0, size.x, size.y));

	this.ship = new Ship();
	this.leveler = new Leveler(this.obstaclePopulater);

	this.tickerTimer.registerNewListener(this);
	this.tickerTimer.registerNewListener(this.leveler);
	this.tickerTimer.registerNewListener(this.obstaclePopulater);
    }

    @Override
    public void onTimerTick(List<TickInterval> intervals, int tick, long period) {
	ship.onUpdate();

	activity.runOnUiThread(new Runnable() {
	    public void run() {
		view.update(ship);
	    }
	});
	detectCollisions();
    }

    private void detectCollisions() {
	List<Obstacle> obstacles = this.obstaclePopulater.getObstacles();
	for (int i = 0; i < obstacles.size(); i++) {
	    if (!obstacles.get(i).hasPassed()) {
		if (obstacles.get(i).isHittingShip(ship)) {
		    onShipHit(obstacles.get(i));
		}
	    }
	}
    }

    private void onShipHit(Obstacle obstacle) {
	activity.runOnUiThread(new Runnable() {
	    public void run() {
		Toast.makeText(activity, "HIT!", Toast.LENGTH_SHORT).show();
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
