package com.hotsx.ringview

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import kotlin.math.min

class RingView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    View(context, attrs, defStyleAttr) {

    private val maxValue = 100f//圆环最大值
    private var currentValue = 10f//圆环当前值
    private var ringColor = Color.WHITE//外层圆环颜色
    private var currentColor = Color.BLUE//内层圆弧颜色
    private var textColor = Color.BLACK//字体颜色
    private var ringWidth = 100f//外层圆环宽度
    private val currentRect = RectF()//画内层圆弧所需

    private val paint = Paint()//画笔
    private var animator = ValueAnimator()//进度动画

    init {
        attrs?.let {
            val typeArray = context.obtainStyledAttributes(it, R.styleable.RingView)
            ringColor = typeArray.getColor(R.styleable.RingView_ringColor, Color.WHITE)
            currentColor = typeArray.getColor(R.styleable.RingView_currentColor, Color.BLUE)
            textColor = typeArray.getColor(R.styleable.RingView_textColor, Color.BLACK)
            typeArray.recycle()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthSpecSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightSpecSize = MeasureSpec.getSize(heightMeasureSpec)
        val minSize = min(widthSpecSize, heightSpecSize)
        setMeasuredDimension(minSize, minSize)//保证绘制区域为正方形
    }

    override fun onDraw(canvas: Canvas) {
        val center = width / 2f//圆心点
        val radius = center - ringWidth / 2//圆环半径
        //画圆环底色
        paint.isAntiAlias = true
        paint.color = ringColor
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = ringWidth
        canvas.drawCircle(center, center, radius, paint)
        //画圆环进度弧形
        val currentAngle = currentValue / maxValue * 360f//进度弧形角度
        currentRect.set(ringWidth / 2f, ringWidth / 2f, width - ringWidth / 2f, width - ringWidth / 2f)
        paint.color = currentColor
        paint.strokeWidth = ringWidth - 20f
        canvas.drawArc(currentRect, 90f, currentAngle, false, paint)
        //画百分比文字
        paint.style = Paint.Style.FILL
        paint.color = textColor
        paint.strokeWidth = 12f
        paint.textSize = 100f
        paint.typeface = Typeface.DEFAULT_BOLD
        paint.textAlign = Paint.Align.CENTER
        val text = "${(currentValue / maxValue * 100).toInt()}%"
        val fontMetrics = paint.fontMetrics
        val distance = (fontMetrics.bottom - fontMetrics.top) / 2f - fontMetrics.bottom
        canvas.drawText(text, center, center + distance, paint)
    }

    fun setProgress(progress: Float) {
        if (animator.isRunning) {
            animator.cancel()
        }
        animator = ValueAnimator.ofFloat(currentValue, progress)
        animator.duration = 1000L
        animator.addUpdateListener {
            currentValue = it.animatedValue as Float
            invalidate()
        }
        animator.start()
    }

    fun getProgress() = currentValue
}