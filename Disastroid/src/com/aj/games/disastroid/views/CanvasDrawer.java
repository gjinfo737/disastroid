package com.aj.games.disastroid.views;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class CanvasDrawer {

    private Resources resources;

    public CanvasDrawer(Resources resources) {
	this.resources = resources;
    }

    public void drawFrame(Canvas canvas, IDrawableItem drawableItem, IFrameResource frameResource, boolean isFrameStepping) {
	int drawableResourceID = drawableItem.getDrawableResource();
	if (isFrameStepping)
	    drawableResourceID = frameResource.getCurrentFrameDrawableResource();
	float x = drawableItem.getX();
	float y = drawableItem.getY();
	float rotation = drawableItem.getRotation();
	Bitmap bm = BitmapFactory.decodeResource(this.resources, drawableResourceID);
	drawBitmap(canvas, x, y, rotation, bm, true);
    }

    public void drawBitmap(Canvas canvas, float x, float y, float rotation, Bitmap bitmap) {
	drawBitmap(canvas, x, y, rotation, bitmap, false);
    }

    public void drawBitmap(Canvas canvas, float x, float y, float rotation, Bitmap bitmap, boolean center) {
	canvas.save();
	canvas.rotate(rotation, x, y);
	canvas.drawBitmap(bitmap, getLeftDraw(x, bitmap), getTopDraw(y, bitmap), null);
	canvas.restore();
    }

    public float getLeftDraw(float x, Bitmap bitmap) {
	return (x - bitmap.getWidth() / 2);
    }

    public float getTopDraw(float y, Bitmap bitmap) {
	return (y - bitmap.getHeight() / 2);
    }
}
