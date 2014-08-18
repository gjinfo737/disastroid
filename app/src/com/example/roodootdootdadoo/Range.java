package com.example.roodootdootdadoo;

public class Range {
	public float low;
	public float high;
	private int inactiveColor;
	private int activeColor;
	private int color;
	private boolean isActive;
	private float percentCloseness = 0;

	public Range(float low, float high, int inactiveColor, int activeColor) {
		this.low = low;
		this.high = high;
		this.inactiveColor = inactiveColor;
		this.activeColor = activeColor;
		setActive(false);
	}

	public float middle() {
		return low + ((high - low) / 2f);
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
		this.color = this.isActive ? activeColor : inactiveColor;
	}

	public int getColor() {
		return color;
	}

	public float getPercentCloseness() {
		return percentCloseness;
	}

	public void setPercentCloseness(float percentCloseness) {
		this.percentCloseness = percentCloseness;
	}

}
