package com.aj.games.disastroid.views;

public class DrawableItem implements IDrawableItem {

    private int drawableResource;
    private float x;
    private float y;
    private boolean visible;
    protected float rotation = 0;

    public DrawableItem(int drawableResource, float x, float y, boolean visible) {
	this.drawableResource = drawableResource;
	this.x = x;
	this.y = y;
	this.visible = visible;
    }

    public int getDrawableResource() {
	return drawableResource;
    }

    public void setDrawableResource(int drawableResource) {
	this.drawableResource = drawableResource;
    }

    public float getX() {
	return x;
    }

    public void setX(float x) {
	this.x = x;
    }

    public float getY() {
	return y;
    }

    public void setY(float y) {
	this.y = y;
    }

    public boolean isVisible() {
	return visible;
    }

    public void setVisible(boolean visible) {
	this.visible = visible;
    }

    @Override
    public float getRotation() {
	return rotation;
    }

    @Override
    public void setRotation(float rotation) {
	this.rotation = rotation;
    }

}
