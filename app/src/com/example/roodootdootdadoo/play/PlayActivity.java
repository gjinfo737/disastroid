package com.example.roodootdootdadoo.play;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.MotionEvent;

import com.example.roodootdootdadoo.BaseActivity;

public class PlayActivity extends BaseActivity {

	private PlayPresenter presenter;
	private long pauseStartTime;
	private boolean paused = true;
	private boolean hasStarted;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		paused = true;
		hasStarted = false;
		presenter = new PlayPresenter(this);
		pauseStartTime = SystemClock.uptimeMillis();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	private void resumeGame() {
		presenter.startTimer();
		long timeSpentPaused = SystemClock.uptimeMillis() - pauseStartTime;
		timeSpentPaused = hasStarted ? timeSpentPaused : 0;
		presenter.resume(timeSpentPaused);
		hasStarted = true;
	}

	@Override
	protected void onPause() {
		paused = true;
		pauseStartTime = SystemClock.uptimeMillis();
		presenter.stopTimer();
		super.onPause();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (paused) {
			resumeGame();
			paused = false;
			return false;
		} else
			return presenter.onTouch(null, event);
	}

}
