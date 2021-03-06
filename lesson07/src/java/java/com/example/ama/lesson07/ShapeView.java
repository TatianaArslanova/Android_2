package com.example.ama.lesson07;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class ShapeView extends View {
    private static final int DEFAULT_SIZE = 100;
    private static final int DEFAULT_CORNERS = 0;
    private Paint shapePaint;
    private Path shapePath;
    private int corners;
    private int shapeColor;

    public ShapeView(Context context) {
        super(context);
    }

    public ShapeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ShapeView);
        corners = array.getInteger(R.styleable.ShapeView_shapeType, DEFAULT_CORNERS);
        shapeColor = array.getColor(R.styleable.ShapeView_shapeColor, Color.GRAY);
        shapePaint = new Paint();
        shapePath = new Path();
        shapePaint.setColor(shapeColor);
        array.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        switch (corners) {
            case 0:
                drawCircle(canvas);
                break;
            case 3:
                drawTriangle(canvas);
                break;
            case 4:
                drawSquare(canvas);
                break;
        }
    }

    private void drawTriangle(Canvas canvas) {
        int canWidth = canvas.getWidth();
        int canHeight = canvas.getHeight();
        boolean widthLess = canWidth < canHeight;
        int leftCornerX = widthLess ? 0 : canWidth / 2 - canHeight / 2;
        int bottomY = widthLess ? canHeight / 2 + canWidth / 2 : canHeight;
        int rightCornerX = widthLess ? canWidth : canWidth / 2 + canHeight / 2;
        int topCornerX = canWidth / 2;
        int topY = widthLess ? canHeight / 2 - canWidth / 2 : 0;
        shapePath.reset();
        shapePath.moveTo(leftCornerX, bottomY);
        shapePath.lineTo(rightCornerX, bottomY);
        shapePath.lineTo(topCornerX, topY);
        shapePath.lineTo(leftCornerX, bottomY);
        canvas.drawPath(shapePath, shapePaint);
    }

    private void drawCircle(Canvas canvas) {
        int centerX = canvas.getWidth() / 2;
        int centerY = canvas.getHeight() / 2;
        int radius = canvas.getWidth() <= canvas.getHeight() ?
                canvas.getWidth() / 2 : canvas.getHeight() / 2;
        canvas.drawCircle(centerX, centerY, radius, shapePaint);
    }

    private void drawSquare(Canvas canvas) {
        int canWidth = canvas.getWidth();
        int canHeight = canvas.getHeight();
        boolean widthLess = canWidth < canHeight;
        int left = widthLess ? 0 : canWidth / 2 - canHeight / 2;
        int top = widthLess ? canHeight / 2 - canWidth / 2 : 0;
        int right = widthLess ? canWidth : canWidth / 2 + canHeight / 2;
        int bottom = widthLess ? canHeight / 2 + canWidth / 2 : canHeight;
        canvas.drawRect(left, top, right, bottom, shapePaint);
    }

    public void setShapeColor(int shapeColor) {
        this.shapeColor = shapeColor;
        shapePaint.setColor(shapeColor);
        invalidate();
    }

    public int getShapeColor() {
        return shapeColor;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(
                measureSize(widthMeasureSpec, getPaddingLeft() + getPaddingRight()),
                measureSize(heightMeasureSpec, getPaddingBottom() + getPaddingTop())
        );
    }

    private int measureSize(int measureSpec, int paddingSum) {
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);
        int result;
        if (mode == MeasureSpec.EXACTLY) {
            result = size;
        } else {
            result = DEFAULT_SIZE + paddingSum;
            if (mode == MeasureSpec.AT_MOST) {
                result = Math.min(result, size);
            }
        }
        return result;
    }
}
