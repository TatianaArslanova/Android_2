package com.example.ama.lesson07

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View

class ShapeView @JvmOverloads constructor(
        context: Context,
        attributeSet: AttributeSet? = null) : View(context, attributeSet) {

    companion object {
        const val DEFAULT_SIZE = 100
        const val DEFAULT_CORNERS = 0
    }

    private val shapePaint = Paint()
    private var corners: Int = 0
    var shapeColor: Int = Color.GRAY
        set(value) {
            field = value
            shapePaint.color = field
            invalidate()
        }

    init {
        val array = context.obtainStyledAttributes(attributeSet, R.styleable.ShapeView)
        corners = array.getInteger(R.styleable.ShapeView_shapeType, DEFAULT_CORNERS)
        shapeColor = array.getColor(R.styleable.ShapeView_shapeColor, Color.GRAY)
        shapePaint.color = shapeColor
        array.recycle()
    }

    override fun onDraw(canvas: Canvas?) {
        if (canvas != null) {
            when (corners) {
                0 -> drawCircle(canvas)
                3 -> drawTriangle(canvas)
                4 -> drawSquare(canvas)
            }
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(
                measureSize(widthMeasureSpec, paddingLeft + paddingRight),
                measureSize(heightMeasureSpec, paddingBottom + paddingTop)
        )
    }

    private fun drawCircle(canvas: Canvas) {
        val centerX = canvas.width / 2
        val centerY = canvas.height / 2
        val radius = if (canvas.width <= canvas.height) canvas.width / 2 else canvas.height / 2
        canvas.drawCircle(centerX.toFloat(), centerY.toFloat(), radius.toFloat(), shapePaint)
    }

    private fun drawTriangle(canvas: Canvas) {
        val widthLess = canvas.width < canvas.height
        val leftCornerX = if (widthLess) 0 else canvas.width / 2 - canvas.height / 2
        val bottomY = if (widthLess) canvas.height / 2 + canvas.width / 2 else canvas.height
        val rightCornerX = if (widthLess) canvas.width else canvas.width / 2 + canvas.height / 2
        val topCornerX = canvas.width / 2
        val topY = if (widthLess) canvas.height / 2 - canvas.width / 2 else 0
        val path = Path()
        path.moveTo(leftCornerX.toFloat(), bottomY.toFloat())
        path.lineTo(rightCornerX.toFloat(), bottomY.toFloat())
        path.lineTo(topCornerX.toFloat(), topY.toFloat())
        path.lineTo(leftCornerX.toFloat(), bottomY.toFloat())
        canvas.drawPath(path, shapePaint)
    }

    private fun drawSquare(canvas: Canvas) {
        val widthLess = canvas.width < canvas.height
        val left = if (widthLess) 0 else canvas.width / 2 - canvas.height / 2
        val top = if (widthLess) canvas.height / 2 - canvas.width / 2 else 0
        val right = if (widthLess) canvas.width else canvas.width / 2 + canvas.height / 2
        val bottom = if (widthLess) canvas.height / 2 + canvas.width / 2 else canvas.height
        canvas.drawRect(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat(), shapePaint)
    }

    private fun measureSize(measureSpec: Int, paddingSum: Int): Int {
        val mode = View.MeasureSpec.getMode(measureSpec)
        val size = View.MeasureSpec.getSize(measureSpec)
        return if (mode == MeasureSpec.EXACTLY) {
            size
        } else {
            if (mode == MeasureSpec.AT_MOST) {
                Math.min(DEFAULT_SIZE + paddingSum, size)
            } else DEFAULT_SIZE + paddingSum
        }
    }
}