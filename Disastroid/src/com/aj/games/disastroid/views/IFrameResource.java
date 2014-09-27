package com.aj.games.disastroid.views;

public interface IFrameResource {

    public int getCurrentFrameDrawableResource();

    public int getFrame();

    public void setFrame(int frame);

    public int nextFrame();

    public int getFrameCount();

    boolean isLooping();

    void setLooping(boolean isLooping);
}
