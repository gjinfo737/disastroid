package com.aj.games.disastroid.views;

public class FramingItem {
    private int[] frames;
    private int frameIndex = 0;

    public FramingItem(int[] frames) {
	this.frames = frames;
    }

    public void incrementFrame() {
	if (frameIndex < frames.length - 1) {
	    frameIndex++;
	} else {
	    frameIndex = 0;
	}
    }

    public int getCurrentFrame() {
	return this.frames[this.frameIndex];
    }
}
