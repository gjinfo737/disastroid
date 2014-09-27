package com.aj.games.disastroid.views;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.aj.games.disastroid.R.drawable;
import com.aj.games.disastroid.obstacle.Obstacle;
import com.aj.games.disastroid.ship.Ship;

public class ShipView extends ImageView {

    private Paint paint;
    private Ship ship;
    private CanvasDrawer canvasDrawer;
    private Bitmap bm;
    private FramingItem framingItem;
    private List<Obstacle> obstacles;
    private Bitmap icon;

    public ShipView(Context context) {
	super(context);
	init();
    }

    public ShipView(Context context, AttributeSet attrs) {
	super(context, attrs);
	init();
    }

    public ShipView(Context context, AttributeSet attrs, int defStyle) {
	super(context, attrs, defStyle);
	init();
    }

    private void init() {
	this.paint = new Paint(Paint.ANTI_ALIAS_FLAG);
	this.paint.setStrokeWidth(5f);
	icon = BitmapFactory.decodeResource(getResources(), drawable.ic_launcher);
	canvasDrawer = new CanvasDrawer();
	framingItem = new FramingItem(new int[] { drawable.ship_0001, drawable.ship_0002 });
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
	PointF center = getCenter(canvas);

	if (isInEditMode()) {
	    paint.setColor(Color.CYAN);
	    canvas.drawCircle(center.x, center.y, 30f, paint);
	    paint.setColor(Color.MAGENTA);
	    canvas.drawLine(center.x - 100f, center.y, center.x + 100f, center.y, paint);
	} else if (this.ship != null) {
	    bm = BitmapFactory.decodeResource(getResources(), this.framingItem.getCurrentFrame());
	    canvasDrawer.drawBitmap(canvas, center.x, center.y, this.ship.getLeftWingAngle(), bm, true);

	    for (int i = 0; i < this.obstacles.size(); i++) {
		Point obCenter = this.obstacles.get(i).getCenter();
		float zoomPercent = this.obstacles.get(i).getZoomPct() / 100f;
		canvasDrawer.drawBitmap(canvas, obCenter.x, obCenter.y, 0f, zoomPercent, icon, true);
	    }
	}
    }

    public void render(Ship ship, List<Obstacle> obstacles) {
	this.ship = ship;
	this.obstacles = obstacles;
	this.invalidate();

	this.framingItem.incrementFrame();
    }

    private PointF getCenter(Canvas canvas) {
	PointF center = new PointF();
	center.x = canvas.getWidth() / 2f;
	center.y = canvas.getHeight() / 2f;
	return center;
    }
}
