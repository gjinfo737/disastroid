package com.aj.games.disastroid.views;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class CanvasDrawer {

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
