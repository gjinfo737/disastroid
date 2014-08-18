package com.example.roodootdootdadoo.play;

import java.util.List;

import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

import com.example.roodootdootdadoo.BaseActivity;
import com.example.roodootdootdadoo.TickerTimer;
import com.example.roodootdootdadoo.TickerTimer.ITickerTimerListener;
import com.example.roodootdootdadoo.TickerTimer.TickInterval;

public class PlayPresenter implements ITickerTimerListener, OnTouchListener {
	private TickerTimer tickerTimer;
	private PlayView view;
	private BaseActivity activity;
	private PlayLeveler playLeveler;
	private Handler handler = new Handler();

	public PlayPresenter(BaseActivity activity) {
		this.activity = activity;
		view = new PlayView(activity);
		tickerTimer = new TickerTimer(33);
		PlayViewControl playViewControl = view.getPlayViewControl();
		createLeveler(playViewControl);

	}

	private void onLevelerCreated(PlayLeveler playLeveler) {
		this.playLeveler = playLeveler;

		tickerTimer.registerNewListener(this);
		playLeveler.start();
	}

	private void createLeveler(final PlayViewControl playViewControl) {
		new Thread(new Runnable() {
			public void run() {
				final PlayLeveler _playLeveler = new PlayLeveler(activity.getResources(), playViewControl, handler);
				handler.post(new Runnable() {
					public void run() {
						onLevelerCreated(_playLeveler);
					}
				});
				return;
			}
		}).start();
	}

	public void startTimer() {
		tickerTimer.start();
	}

	public void stopTimer() {
		tickerTimer.stop();
	}

	@Override
	public void onTimerTick(List<TickInterval> intervals, int tick, long period) {
		if (playLeveler != null)
			playLeveler.onTimerTick(intervals, tick, period);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if (playLeveler != null)
			return playLeveler.onTouch(v, event);
		return false;
	}

	public void resume(long timeSpentPaused) {
		if (playLeveler != null)
			playLeveler.updateTimes(timeSpentPaused);
	}
}
