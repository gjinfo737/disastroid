package com.example.roodootdootdadoo.play;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import com.example.roodootdootdadoo.TickerTimer.ITickerTimerListener;
import com.example.roodootdootdadoo.TickerTimer.TickInterval;

public class Frameimated implements ITickerTimerListener {

	protected List<Integer> frames = new ArrayList<Integer>();
	protected boolean loop = false;
	protected boolean treking = false;
	protected int currentFrameIndex = 0;
	protected Map<Integer, Bitmap> bms = new HashMap<Integer, Bitmap>();
	protected Resources resources;
	protected Matrix bmSMatrix = new Matrix();

	public Frameimated(String framePrefix, int start, int end, Resources resources) {
		List<Integer> frames = new ArrayList<Integer>();

		for (int i = start; i <= end; i++) {
			int identifier = resources.getIdentifier(framePrefix + String.format("%04d", i), "drawable", "com.example.roodootdootdadoo");
			frames.add(identifier);
		}

		init(frames, resources);
	}

	public Frameimated(List<Integer> frames, Resources resources) {
		init(frames, resources);
	}

	private void init(List<Integer> frames, Resources resources) {
		this.frames = frames;
		this.resources = resources;

		for (int i = 0; i < frames.size(); i++) {
			Integer resID = frames.get(i);
			bms.put(i, BitmapFactory.decodeResource(resources, resID));
		}
	}

	public Bitmap getCurrentBitmap() {
		return bms.get(currentFrameIndex);
	}

	public int getCurrentFrame() {
		return frames.get(currentFrameIndex);
	}

	@Override
	public void onTimerTick(List<TickInterval> intervals, int tick, long period) {
		if (treking)
			nextFrame();
	}

	private void nextFrame() {
		if (currentFrameIndex < frames.size() - 1)
			currentFrameIndex++;
		else if (loop)
			reset();

	}

	private void reset() {
		currentFrameIndex = 0;
	}

	public void setLoop(boolean loop) {
		this.loop = loop;
	}

	public void startFrames() {
		treking = true;
	}

	public void stop() {
		treking = false;
	}
}
