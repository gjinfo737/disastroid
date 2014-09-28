package com.aj.games.disastroid.play;

import java.util.ArrayList;
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
import com.aj.games.disastroid.obstacles.Explosion;
import com.aj.games.disastroid.ship.Ship;
import com.aj.games.disastroid.time.TickerTimer;
import com.aj.games.disastroid.time.TickerTimer.ITickerTimerListener;
import com.aj.games.disastroid.time.TickerTimer.TickInterval;

public class PlayPresenter implements ITickerTimerListener {

    private static final long PERIOD = 30L;
    private Activity activity;

    private TickerTimer tickerTimer;
    private Ship ship;
    private PlayView view;
    private Leveler leveler;
    private ObstaclePopulater obstaclePopulater;
    private List<Explosion> explosions = new ArrayList<Explosion>();
    private boolean isPaused = true;

    public PlayPresenter(Activity activity) {
	this.activity = activity;
	this.view = new PlayView(this, activity);
	this.tickerTimer = new TickerTimer(PERIOD);

	Toast.makeText(this.activity, "Loading...", Toast.LENGTH_LONG).show();
	initializeAfterDelay();
	view.showPaused(isPaused);
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
	}, 2000);
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

	while (!cleanExplosions()) {
	}

	for (int i = 0; i < explosions.size(); i++) {
	    explosions.get(i).getFramingItem().incrementFrame();
	}

	activity.runOnUiThread(new Runnable() {
	    public void run() {
		view.update(ship, obstaclePopulater.getObstacles(), explosions, leveler.getLevel());
	    }
	});
	detectCollisions();
    }

    private boolean cleanExplosions() {

	if (explosions == null)
	    return true;
	if (explosions.size() == 0) {
	    return true;
	}

	int removeIndex = -1;
	for (int i = 0; i < explosions.size(); i++) {
	    Explosion ob = explosions.get(i);
	    if (ob.getFramingItem().isAtEnd()) {
		removeIndex = i;
		break;
	    }
	}
	if (removeIndex != -1) {
	    explosions.remove(removeIndex);
	    return false;
	} else {
	    return true;
	}

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
	ship.takeHit(obstacle);

	explosions.add(new Explosion(obstacle.getCenter()));

	if (ship.isDead()) {
	    gameOver();
	}
    }

    private void gameOver() {
	onPause();
	activity.runOnUiThread(new Runnable() {
	    public void run() {
		view.gameOver();
		Toast.makeText(activity, "Game over!", Toast.LENGTH_SHORT).show();
	    }
	});

    }

    public void onResume() {
	view.showPaused(isPaused);
    }

    public void onPause() {
	pause();
    }

    private void resume() {
	this.isPaused = false;
	this.tickerTimer.start();
	view.showPaused(isPaused);
    }

    private void pause() {
	this.isPaused = true;
	this.tickerTimer.stop();
	view.showPaused(isPaused);
    }

    public void onTap() {
	this.ship.changeRotation();
    }

    public void togglePause() {
	this.isPaused = !this.isPaused;
	if (this.isPaused) {
	    pause();
	} else {
	    resume();
	}
    }
}
