package com.aj.games.disastroid.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;

public class SpaceShipView extends View {

	private Paint paint;
	private Object spaceShip;

	public SpaceShipView(Context context) {
		super(context);
		init();
	}

	public SpaceShipView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public SpaceShipView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	private void init() {
		this.paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		this.paint.setStrokeWidth(5f);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		PointF center = getCenter(canvas);

		paint.setColor(Color.CYAN);
		canvas.drawCircle(center.x, center.y, 30f, paint);
		paint.setColor(Color.MAGENTA);
		canvas.drawLine(center.x - 100f, center.y, center.x + 100f, center.y,
				paint);

	}

	public void render(Object spaceShip) {
		this.spaceShip = spaceShip;
		this.invalidate();
	}

	private PointF getCenter(Canvas canvas) {
		PointF center = new PointF();
		center.x = canvas.getWidth() / 2f;
		center.y = canvas.getHeight() / 2f;
		return center;
	}
}
