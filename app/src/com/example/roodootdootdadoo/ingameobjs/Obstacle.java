package com.example.roodootdootdadoo.ingameobjs;

import java.util.Random;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.os.SystemClock;

import com.example.roodootdootdadoo.Range;
import com.example.roodootdootdadoo.play.Frameimated;

public class Obstacle extends Frameimated {
	private Range range;
	private Random random = new Random();
	private PointF offset = new PointF();
	private long timeBetweenLevels = 2500;
	private long lengthOfLevel = 2000;
	private long prepLevelStartTime;
	private long levelStartTime;

	public Obstacle(Resources resources, long timeBetweenLevels, long lengthOfLevel) {
		super("_a", 1, 190, resources);
		this.range = new Range(0, 0, 0, 0);
		this.timeBetweenLevels = timeBetweenLevels;
		this.lengthOfLevel = lengthOfLevel;
	}

	public Range getRange() {
		return range;
	}

	public void draw(Canvas canvas, float angle, float width, float height, PointF center, float total) {
		Bitmap bm = getCurrentBitmap();
		if (bm == null)
			return;

		float halfBMwidth = bm.getWidth() * .5f;
		float halfBMheight = bm.getHeight() * .5f;
		float scale = width / bm.getWidth();
		float percentCloseness = range.getPercentCloseness();
		scale *= percentCloseness;
		bmSMatrix.reset();
		bmSMatrix.setScale(scale, scale);
		float tx = center.x - halfBMwidth * scale;
		float ty = center.y - (halfBMheight * scale);
		offset.x *= (1 - percentCloseness);
		offset.y *= (1 - percentCloseness);
		tx += offset.x * width / 4f;
		ty += offset.y * height / 4f;
		bmSMatrix.postTranslate(tx, ty);
		float rAngle = (360f * range.middle() / (total)) + 90f;
		bmSMatrix.postRotate(rAngle, center.x, center.y);
		canvas.drawBitmap(bm, bmSMatrix, null);

	}

	public void reset(Range range) {
		this.range = range;
		currentFrameIndex = 0;
		offset.x = random.nextFloat();
		offset.y = random.nextFloat();
		offset.x *= random.nextBoolean() ? 1 : -1;
		offset.y *= random.nextBoolean() ? 1 : -1;
		prepLevelStartTime = SystemClock.uptimeMillis();
	}

	private float getPercentNextLevelWaitingComplete() {
		float n = SystemClock.uptimeMillis() - prepLevelStartTime;
		float p = n / timeBetweenLevels;

		p = p < 0 ? 0 : p;
		return p;
	}

	public void update() {
		range.setPercentCloseness(getPercentNextLevelWaitingComplete());
	}

	public long getLengthOfLevel() {
		return lengthOfLevel;
	}

	public void startLevel() {
		levelStartTime = SystemClock.uptimeMillis();
	}

	private long timeLeftForLevel() {
		return lengthOfLevel - (SystemClock.uptimeMillis() - levelStartTime);
	}

	public boolean isComplete() {
		return timeLeftForLevel() <= 0 && range.isActive();
	}

	public void updateTimes(long timeSpentPaused) {
		levelStartTime += timeSpentPaused;
		prepLevelStartTime += timeSpentPaused;
	}

}
