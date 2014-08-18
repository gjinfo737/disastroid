package com.example.roodootdootdadoo.play;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.example.roodootdootdadoo.R.drawable;
import com.example.roodootdootdadoo.Range;
import com.example.roodootdootdadoo.TickerTimer.ITickerTimerListener;
import com.example.roodootdootdadoo.TickerTimer.TickInterval;
import com.example.roodootdootdadoo.ingameobjs.Obstacle;

public class PlayViewControl extends View implements ITickerTimerListener {

	private float min = 0;
	private float max = 100;
	private float width = -1;
	private float height;
	private Paint paint;
	private PointF center;
	private float maxRadius;
	private float margin = 1;
	private float lineWidth = 3;
	private float tickRatio = .12f;
	private Map<Double, PointF> valuepoints;
	private Map<Double, PointF> maxRadiusPoints;
	private Map<Double, PointF> innerTickPoints;
	private float textSizeSmall = 10f;
	private float textSizeMedium = 17f;
	private float textSizeLarge = 22f;
	private DecimalFormat decimalFormat = new DecimalFormat("##");
	private String label = "";
	private String units = "";
	private int waterMarkColor = Color.RED;
	private int backgroundColor = Color.GRAY;
	private int tickColor = Color.BLACK;
	private int plateColor = Color.BLACK;
	private int labelColor = Color.WHITE;
	private int unitsColor = Color.WHITE;
	private int tickLabelColor = Color.BLACK;
	private float currentValue;
	private float hpPercentage = 1f;
	private Obstacle obstacle = null;
	private int alphaValue = 100;
	private int hpColor = Color.RED;
	private boolean clockwise;
	private Bitmap bmS;
	private Matrix bmSMatrix;

	public PlayViewControl(Context context) {
		super(context);
		init();
	}

	public PlayViewControl(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public PlayViewControl(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	private void init() {
		paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		center = new PointF(0f, 0f);

		bmS = BitmapFactory.decodeResource(getResources(), drawable.s);
	}

	@Override
	public void onTimerTick(List<TickInterval> intervals, int tick, long period) {
		float largestDistance = Math.abs(max - min);
		float delta = largestDistance / 100f;

		delta *= .6f;
		if (clockwise)
			currentValue += delta;
		else
			currentValue -= delta;

		if (currentValue >= 100) {
			currentValue -= 100;
		}
		if (currentValue < 0) {
			currentValue += 100;
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		if (isInEditMode()) {
			canvas.drawColor(Color.GREEN);
			return;
		}

		if (width != canvas.getWidth()) {
			width = canvas.getWidth();
			height = canvas.getHeight();
			if (isInEditMode()) {
				width = 150f;
				height = 150f;
			}
			center.x = width / 2f;
			center.y = height / 2f;
			maxRadius = center.y - margin;
			valuepoints = createRadialPoints(maxRadius - (2f * lineWidth), center);
			maxRadiusPoints = createRadialPoints(maxRadius, center);
			innerTickPoints = createRadialPoints(maxRadius - tickLength(), center);
		}
		// drawBG(canvas);
		// drawCurrentValue(canvas);
		// drawPlate(canvas);

		float total = max - min;
		float angle = (360f * currentValue / total) + 90f;
		float xscale = .9f * width / bmS.getWidth();
		float yscale = .9f * height / bmS.getHeight();
		float halfBMwidth = bmS.getWidth() * .5f;
		float halfBMheight = bmS.getHeight() * .5f;

		if (bmSMatrix == null)
			bmSMatrix = new Matrix();
		if (obstacle != null) {
			obstacle.draw(canvas, angle, width, height, center, total);
			drawRanges(canvas);
		}
		drawHP(canvas);
		drawSSS(canvas, angle, xscale, yscale, halfBMwidth, halfBMheight);
		drawPlateLabel(canvas);
	}

	private void drawSSS(Canvas canvas, float angle, float xscale, float yscale, float halfBMwidth, float halfBMheight) {
		bmSMatrix.reset();
		bmSMatrix.setScale(xscale, yscale);
		bmSMatrix.postTranslate(center.x - halfBMwidth * xscale, center.y - halfBMheight * yscale);
		bmSMatrix.postRotate(angle, center.x, center.y);
		canvas.drawBitmap(bmS, bmSMatrix, null);
	}

	// private void drawObstacle(Canvas canvas, float scale, float halfBMwidth,
	// float halfBMheight, Range r) {
	// float rScale = scale;
	// rScale *= r.getPercentCloseness();
	// bmSMatrix.reset();
	// bmSMatrix.setScale(rScale, rScale);
	// bmSMatrix.postTranslate(center.x - halfBMwidth * scale, center.y -
	// halfBMheight * scale);
	// float rAngle = (360f * r.middle() / (max - min)) + 90f;
	// bmSMatrix.postRotate(rAngle, center.x, center.y);
	// canvas.drawBitmap(bmR, bmSMatrix, null);
	// }

	private void drawCurrentValue(Canvas canvas) {
		float d = 1f;
		float low1 = fixvalue(currentValue - d);
		float high1 = fixvalue(currentValue + d);
		drawValueBetween(canvas, center, getPercentValue(low1), getPercentValue(high1), Color.WHITE, alphaValue);
		float low2 = fixvalue(currentValue + (50 - d));
		float high2 = fixvalue(currentValue + 50 + d);
		drawValueBetween(canvas, center, getPercentValue(low2), getPercentValue(high2), Color.CYAN, alphaValue);
	}

	public float fixvalue(float v) {
		if (v >= max)
			v -= max;
		else if (v < min)
			v += max;

		return v;
	}

	private void drawRanges(Canvas canvas) {
		Range range = obstacle.getRange();
		drawValueBetween(canvas, center, getPercentValue(range.low), getPercentValue(range.high),

		range.getColor(), 5);
	}

	private void drawHP(Canvas canvas) {
		paint.setColor(hpColor);
		paint.setStrokeWidth(5f);

		// for (double a = 0; a < hpPercentage * 360f; a += 1) {
		// PointF pa = maxRadiusPoints.get(a);
		// PointF pb = innerTickPoints.get(a);
		// canvas.drawLine(pa.x, pa.y, pb.x, pb.y, paint);
		// }
		float percentValueHigh = hpPercentage;
		for (double a = 0; a < 270; a++) {
			float percentAngle = ((float) ((270f - a) / 270f));
			percentAngle *= .75f;
			if (percentValueHigh >= percentAngle && 0f <= percentAngle) {
				PointF pa = maxRadiusPoints.get(a);
				PointF pb = innerTickPoints.get(a);
				try {
					canvas.drawLine(pa.x, pa.y, pb.x, pb.y, paint);
				} catch (Exception e) {
				}
			}
		}
		if (percentValueHigh >= .75) {
			for (float i = 0; i < 90; i++) {
				double a = 360 - i;
				float percentAngle = (i / 360f) + .75f;
				if (percentValueHigh >= percentAngle && 0f <= percentAngle) {
					PointF pa = maxRadiusPoints.get(a);
					PointF pb = innerTickPoints.get(a);
					try {
						canvas.drawLine(pa.x, pa.y, pb.x, pb.y, paint);
					} catch (Exception e) {
					}
				}
			}
		}
	}

	private void drawValue(Canvas canvas, PointF center, float percentValueHigh, int color, int _alpha) {
		drawValueBetween(canvas, center, 0, percentValueHigh, color, _alpha);
	}

	private void drawValueBetween(Canvas canvas, PointF center, float percentValueLow, float percentValueHigh, int color, int _alphaValue) {
		paint.setStrokeWidth(5f);
		paint.setColor(color);
		paint.setAlpha(_alphaValue);

		if (isInEditMode())
			percentValueHigh = .65f;
		for (double a = 0; a < 270; a++) {
			float percentAngle = ((float) ((270f - a) / 270f));
			percentAngle *= .75f;
			if (percentValueHigh >= percentAngle && percentValueLow <= percentAngle) {
				PointF pointF = innerTickPoints.get(a);
				if (pointF != null) {
					canvas.drawLine(center.x, center.y, pointF.x, pointF.y, paint);
				}
			}
		}
		if (percentValueHigh >= .75) {
			for (float i = 0; i < 90; i++) {
				double a = 360 - i;
				float percentAngle = (i / 360f) + .75f;
				if (percentValueHigh >= percentAngle && percentValueLow <= percentAngle) {
					PointF pointF = innerTickPoints.get(a);
					if (pointF != null) {
						canvas.drawLine(center.x, center.y, pointF.x, pointF.y, paint);
					}
				}
			}
		}
	}

	private Map<Double, PointF> createRadialPoints(float radius, PointF center) {
		Map<Double, PointF> points = new HashMap<Double, PointF>();
		for (double a = 0; a < 360; a++) {
			double radians = a * Math.PI / 180d;
			float x = (float) (radius * Math.cos(radians));
			float y = (float) (radius * Math.sin(radians));
			x += center.x;
			y += center.y;
			y = height - y;
			points.put(a, new PointF(x, y));
		}
		return points;
	}

	private void drawTicks(Canvas canvas) {
		paint.setColor(tickColor);
		paint.setStrokeWidth(1.2f);

		for (double a = 0; a < 360; a += 10) {
			PointF pa = maxRadiusPoints.get(a);
			PointF pb = innerTickPoints.get(a);
			canvas.drawLine(pa.x, pa.y, pb.x, pb.y, paint);
		}

	}

	private void drawTickLabels(Canvas canvas) {
		paint.setColor(tickLabelColor);
		paint.setTextSize(textSizeSmall);
		float textAdjustment = textSizeMedium / 2f;
		PointF pb = innerTickPoints.get(0d);
		paint.setTextSize(textSizeMedium);
		canvas.drawText(valueAsText(getTickValueAtPercent(.75f)), pb.x - textSizeMedium, pb.y, paint);
		pb = innerTickPoints.get(90d);
		canvas.drawText(valueAsText(getTickValueAtPercent(.5f)), pb.x - textAdjustment, pb.y + textSizeMedium, paint);
		pb = innerTickPoints.get(180d);
		canvas.drawText(valueAsText(getTickValueAtPercent(.25f)), pb.x, pb.y, paint);
		pb = innerTickPoints.get(270d);
		canvas.drawText(valueAsText(getTickValueAtPercent(0f)), pb.x - textAdjustment, pb.y, paint);

	}

	private double getTickValueAtPercent(float percent) {
		double vt = (percent * (max - min)) + min;
		return vt;
	}

	private String valueAsText(double value) {
		return decimalFormat.format(value);
	}

	private float getPercentValue(float v) {
		return (float) ((v - min) / (max - min));
	}

	private void drawPlate(Canvas canvas) {
		drawCircleFromCenter(canvas, plateRadius(), plateColor);
	}

	private float plateRadius() {
		return 1f * maxRadius / 3f;
	}

	private void drawPlateLabel(Canvas canvas) {
		paint.setTextSize(textSizeLarge);
		paint.setColor(labelColor);
		Rect bounds = new Rect();
		paint.getTextBounds(label, 0, label.length(), bounds);
		canvas.drawText(label,

		center.x - (bounds.width() / 2f),

		center.y - bounds.height() / 2.4f,

		paint);
		paint.setTextSize(textSizeMedium);
		paint.getTextBounds(units, 0, units.length(), bounds);
		paint.setColor(unitsColor);
		canvas.drawText(units,

		center.x - (bounds.width() / 2f),

		center.y + bounds.height() / 1.2f,

		paint);
	}

	private float tickLength() {
		return maxRadius * tickRatio;
	}

	private void drawBG(Canvas canvas) {
		float r = maxRadius;
		drawCircleFromCenter(canvas, r, backgroundColor);
		r -= lineWidth;
		drawCircleFromCenter(canvas, r, tickColor);
		r -= lineWidth;
		drawCircleFromCenter(canvas, r, backgroundColor);
	}

	private void drawCircleFromCenter(Canvas canvas, float r, int color) {
		paint.setColor(color);
		canvas.drawCircle(center.x, center.y, r, paint);
	}

	public float getMin() {
		return min;
	}

	public void setMin(float min) {
		if (min < 0)
			this.min = 0;
		else
			this.min = min;
	}

	public float getMax() {
		return max;
	}

	public void setMax(float max) {
		this.max = max;
	}

	public float getMargin() {
		return margin;
	}

	public void setMargin(float margin) {
		this.margin = margin;
	}

	public float getLineWidth() {
		return lineWidth;
	}

	public void setLineWidth(float lineWidth) {
		this.lineWidth = lineWidth;
	}

	public float getTickRatio() {
		return tickRatio;
	}

	public void setTickRatio(float tickRatio) {
		this.tickRatio = tickRatio;
	}

	public float getTextSize() {
		return textSizeSmall;
	}

	public void setTextSize(float textSize) {
		this.textSizeSmall = textSize;
	}

	public DecimalFormat getDecimalFormat() {
		return decimalFormat;
	}

	public void setDecimalFormat(DecimalFormat decimalFormat) {
		this.decimalFormat = decimalFormat;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getUnits() {
		return units;
	}

	public void setUnits(String units) {
		this.units = units;
	}

	public void setWaterMarkColor(int waterMarkColor) {
		this.waterMarkColor = waterMarkColor;
	}

	public int getWaterMarkColor() {
		return waterMarkColor;
	}

	public void setValuepoints(Map<Double, PointF> valuepoints) {
		this.valuepoints = valuepoints;
	}

	public void setTextSizeSmall(float textSizeSmall) {
		this.textSizeSmall = textSizeSmall;
	}

	public void setTextSizeMedium(float textSizeMedium) {
		this.textSizeMedium = textSizeMedium;
	}

	public void setTextSizeLarge(float textSizeLarge) {
		this.textSizeLarge = textSizeLarge;
	}

	public void setBackgroundColor(int backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public void setTickColor(int tickColor) {
		this.tickColor = tickColor;
	}

	public void setPlateColor(int plateColor) {
		this.plateColor = plateColor;
	}

	public void setLabelColor(int labelColor) {
		this.labelColor = labelColor;
	}

	public void setUnitsColor(int unitsColor) {
		this.unitsColor = unitsColor;
	}

	public void setTickLabelColor(int tickLabelColor) {
		this.tickLabelColor = tickLabelColor;
	}

	public int getAlphaValue() {
		return alphaValue;
	}

	public void setAlphaValue(int alphaValue) {
		this.alphaValue = alphaValue;
	}

	public float getCurrentValue() {
		return currentValue;
	}

	public float getHpPercentage() {
		return hpPercentage;
	}

	public void setHpPercentage(float hpPercentage) {
		this.hpPercentage = hpPercentage;
	}

	public int getHpColor() {
		return hpColor;
	}

	public void setHpColor(int hpColor) {
		this.hpColor = hpColor;
	}

	public void reduceHPPerentageBy(float reduction) {
		float hp = this.hpPercentage;
		hp -= reduction;
		this.hpPercentage = hp > 0 ? hp : 0;
		// /
		if (this.hpPercentage <= 0) {
			hpPercentage = 1;
		}
	}

	public boolean isClockwise() {
		return clockwise;
	}

	public void setClockwise(boolean clockwise) {
		this.clockwise = clockwise;
	}

	protected Obstacle getObstacle() {
		return obstacle;
	}

	protected void setObstacle(Obstacle obstacle) {
		this.obstacle = obstacle;
	}
}
