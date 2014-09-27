package com.aj.games.disastroid.views;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class CanvasDrawer {

    private Paint paint;

    public CanvasDrawer() {
	paint = new Paint();
	paint.setAntiAlias(true);
	paint.setFilterBitmap(true);
	paint.setDither(true);

    }

    public void drawBitmap(Canvas canvas, float x, float y, float rotation, Bitmap bitmap) {
	drawBitmap(canvas, x, y, rotation, bitmap, false);
    }

    public void drawBitmap(Canvas canvas, float x, float y, float rotation, Bitmap bitmap, boolean center) {
	this.drawBitmap(canvas, x, y, rotation, 1f, bitmap, center);
    }

    public void drawBitmap(Canvas canvas, float x, float y, float rotation, float zoomPercent, Bitmap bitmap, boolean center) {
	canvas.save();
	canvas.rotate(rotation, x, y);
	canvas.scale(zoomPercent, zoomPercent, x, y);
	canvas.drawBitmap(bitmap, getLeftDraw(x, bitmap), getTopDraw(y, bitmap), paint);
	canvas.restore();
    }

    public float getLeftDraw(float x, Bitmap bitmap) {
	return (x - bitmap.getWidth() / 2);
    }

    public float getTopDraw(float y, Bitmap bitmap) {
	return (y - bitmap.getHeight() / 2);
    }
}
