package com.example.roodootdootdadoo.play;

import static com.example.roodootdootdadoo.play.Colors.BLACK;
import static com.example.roodootdootdadoo.play.Colors.GREEN;
import static com.example.roodootdootdadoo.play.Colors.ORANGE;

import java.util.List;
import java.util.Random;

import android.content.res.Resources;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

import com.example.roodootdootdadoo.Range;
import com.example.roodootdootdadoo.TickerTimer;
import com.example.roodootdootdadoo.TickerTimer.ITickerTimerListener;
import com.example.roodootdootdadoo.TickerTimer.TickInterval;
import com.example.roodootdootdadoo.ingameobjs.Obstacle;

public class PlayLeveler implements ITickerTimerListener, OnTouchListener {
	private PlayViewControl vd;
	private Random random = new Random();
	private Obstacle obstacle = null;
	private Handler handler;
	private boolean started;
	private Resources resources;

	public PlayLeveler(Resources resources, PlayViewControl vd, Handler uihandler) {
		this.handler = uihandler;
		this.resources = resources;
		this.vd = vd;
		started = false;
		vd.setBackgroundColor(BLACK);
		vd.setHpColor(ORANGE);
		makeObstacle();
	}

	public void start() {
		if (!started) {
			startLevelSequence();
			started = true;
		}
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		int action = event.getAction();
		if (action == MotionEvent.ACTION_DOWN) {
			switchDirection();

			return true;
		}
		return false;
	}

	@Override
	public void onTimerTick(List<TickInterval> intervals, int tick, long period) {
		if (obstacle != null)
			obstacle.onTimerTick(intervals, tick, period);
		if (obstacle != null && TickerTimer.everySeconds(.25f, tick, period)) {
			if (obstacle.getRange().isActive()) {
				if (!currentWithinRange()) {
					onOutofBounds();
				}
			}
		}
		if (TickerTimer.everySeconds(1f / 30f, tick, period)) {
			vd.onTimerTick(intervals, tick, period);
			vd.invalidate();
			if (obstacle != null) {
				if (obstacle.isComplete()) {
					startLevelSequence();
				}
				obstacle.update();
			}
		}

	}

	private void startLevelSequence() {
		long delay = (long) (random.nextFloat() * 5000L);
		resetForNextLevel();
		handler.postDelayed(new Runnable() {
			public void run() {
				prepNextRange();
				handler.postDelayed(new Runnable() {
					public void run() {
						startLevel();
					}
				}, obstacle.getLengthOfLevel());
			}
		}, delay);
	}

	private void prepNextRange() {
		float low = random.nextFloat() * 90f;
		float high = low + 10f;
		Range range = new Range(low, high, GREEN, ORANGE);
		if (obstacle == null)
			makeObstacle();
		obstacle.reset(range);
		vd.setObstacle(obstacle);
		obstacle.startFrames();
	}

	private void makeObstacle() {
		obstacle = new Obstacle(resources, 2500, 2000);
	}

	private void resetForNextLevel() {
		vd.setObstacle(null);
		if (obstacle != null)
			obstacle.getRange().setActive(false);
	}

	private void startLevel() {

		obstacle.getRange().setActive(true);
		obstacle.startLevel();
	}

	private void onOutofBounds() {
		vd.reduceHPPerentageBy(.01f);
		switchDirection();
	}

	private boolean currentWithinRange() {
		boolean result = false;
		if (fix(vd.getCurrentValue()) < obstacle.getRange().high

		&& fix(vd.getCurrentValue()) > obstacle.getRange().low) {
			result = true;
		} else if (fix(vd.getCurrentValue() + 50) < obstacle.getRange().high

		&& fix(vd.getCurrentValue() + 50) > obstacle.getRange().low) {
			result = true;
		}

		return result;
	}

	private float fix(float v) {
		return vd.fixvalue(v);
	}

	private void switchDirection() {
		vd.setClockwise(!vd.isClockwise());
	}

	public void updateTimes(long timeSpentPaused) {
		if (obstacle != null) {
			obstacle.updateTimes(timeSpentPaused);
		}
	}

}
