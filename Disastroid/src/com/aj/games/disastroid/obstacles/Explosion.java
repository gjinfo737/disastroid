package com.aj.games.disastroid.obstacles;

import android.graphics.Point;

import com.aj.games.disastroid.views.FramingItem;

public class Explosion {

    private FramingItem framingItem;
    private Point center;

    public Explosion(Point center) {
	this.center = center;
	this.framingItem = new FramingItem("explosion_", 6);
	this.framingItem.setLooping(false);
    }

    public Point getCenter() {
	return center;
    }

    public FramingItem getFramingItem() {
	return framingItem;
    }

}
