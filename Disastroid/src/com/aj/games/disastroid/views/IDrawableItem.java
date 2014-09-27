package com.aj.games.disastroid.views;

public interface IDrawableItem {
    public int getDrawableResource();

    public void setDrawableResource(int drawableResource);

    public float getX();

    public void setX(float x);

    public float getY();

    public void setY(float y);

    public boolean isVisible();

    public void setVisible(boolean visible);

    public float getRotation();

    public void setRotation(float rotation);
}
