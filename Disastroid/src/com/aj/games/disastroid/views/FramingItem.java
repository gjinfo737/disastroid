package com.aj.games.disastroid.views;

import android.content.Context;

public class FramingItem {
    private int frameIndex = 1;
    private String prefix;
    private int numberOfFrames;
    private boolean loops = true;

    public FramingItem(String prefix, int numberOfFrames) {
	this.prefix = prefix;
	this.numberOfFrames = numberOfFrames;
    }

    public void incrementFrame() {
	if (frameIndex < numberOfFrames) {
	    frameIndex++;
	} else if (loops) {
	    frameIndex = 1;
	}
    }

    public int getCurrentFrame(Context context) {

	String numberStr = String.format("%04d", frameIndex);
	String idStr = this.prefix + numberStr;

	return getDrawableeId(idStr, context);
    }

    public static int getDrawableeId(String idStr, Context context) {
	try {
	    return context.getResources().getIdentifier(idStr, "drawable", context.getPackageName());
	} catch (Exception e) {
	    e.printStackTrace();
	    return -1;
	}
    }

    public void setLooping(boolean loops) {
	this.loops = loops;
    }

    public boolean isAtEnd() {
	return frameIndex == numberOfFrames && !loops;
    }
}
