package com.aj.games.disastroid.time;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.os.Handler;

public class TickerTimer {

    private static final int MAX_TICK = 10000;
    private Timer timer;
    private long period;
    private Handler handler;
    private List<ITickerTimerListener> listeners;
    private int tick = 0;

    public enum TickInterval {
	EVERY_OTHER(2), FIFTH(5), TENTH(10), HUNDREDTH(100), FIVE_HUNDREDTH(500);

	private int mod;

	private TickInterval(int mod) {
	    this.mod = mod;
	}

	public boolean shouldTick(int tick) {
	    return tick % mod == 0;
	}
    }

    public interface ITickerTimerListener {
	public void onTimerTick(List<TickInterval> intervals, int tick, long period);
    }

    public TickerTimer(long period) {
	this.listeners = new ArrayList<TickerTimer.ITickerTimerListener>();
	this.period = period;
	this.handler = new Handler();
    }

    public void start() {
	stop();
	timer = new Timer();
	timer.scheduleAtFixedRate(new TimerTask() {
	    @Override
	    public void run() {
		handler.post(new Runnable() {
		    @Override
		    public void run() {
			onTick();
		    }

		});
	    }
	}, 0, period);
    }

    public void stop() {
	if (timer != null)
	    timer.cancel();
	timer = null;
    }

    private void onTick() {
	tick++;
	if (tick > MAX_TICK) {
	    tick = 0;
	}
	List<TickInterval> intervals = determineInterval();
	for (ITickerTimerListener listener : listeners) {
	    listener.onTimerTick(intervals, tick, period);
	}
    }

    private List<TickInterval> determineInterval() {
	List<TickInterval> intervalsList = new ArrayList<TickerTimer.TickInterval>();
	TickInterval[] tickIntervals = TickInterval.values();
	for (TickInterval ti : tickIntervals) {
	    if (ti.shouldTick(tick)) {
		intervalsList.add(ti);
	    }
	}
	return intervalsList;
    }

    public void unregisterListener(ITickerTimerListener listener) {
	try {
	    listeners.remove(listener);
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    public void registerNewListener(ITickerTimerListener listener) {
	listeners.add(listener);
    }

    public static boolean everyMinutes(float numberOfMinutes, int tickCount, long periodInMillis) {
	return everySeconds(numberOfMinutes * 60, tickCount, periodInMillis);
    }

    public static boolean everySeconds(float numberOfSeconds, int tickCount, long periodInMillis) {
	int t = (int) (numberOfSeconds * 1000f / periodInMillis);
	return tickCount % t == 0;
    }

}
