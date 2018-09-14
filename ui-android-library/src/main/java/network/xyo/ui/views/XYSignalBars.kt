package network.xyo.ui.views

import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Paint.Style
import android.graphics.RectF
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.view.animation.AlphaAnimation
import network.xyo.ui.R
import network.xyo.ui.ui


class XYSignalBars @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) : View(context, attrs, defStyle) {

    private var barMaxCount = 3
    private var barCount = 0

    private var paintStroke: Paint? = null

    private var paintEmpty: Paint? = null

    private var paintFill: Paint? = null

    private val rect = RectF()

    init {

        var color = Color.WHITE
        if (attrs != null) {
            val value = attrs.getAttributeResourceValue("http://schemas.android.com/apk/res/android", "color", 0)
            color = if (value != 0) {
                @Suppress("DEPRECATION")resources.getColor(value)
            } else {
                attrs.getAttributeIntValue("http://schemas.android.com/apk/res/android", "color", Color.WHITE)
            }
        }

        if (attrs != null) {
            val attributeArray = context.obtainStyledAttributes(
                    attrs,
                    R.styleable.XYSignalBars,
                    0, 0)

            if (attributeArray != null) {
                barMaxCount = attributeArray.getInt(R.styleable.XYSignalBars_maxBars, 3)
                attributeArray.recycle()
            }
        }

        paintStroke = Paint(Paint.ANTI_ALIAS_FLAG)
        paintStroke!!.style = Style.STROKE
        paintStroke!!.strokeWidth = 2f

        paintEmpty = Paint(Paint.ANTI_ALIAS_FLAG)
        paintEmpty!!.alpha = 96
        paintEmpty!!.style = Style.FILL

        paintFill = Paint(Paint.ANTI_ALIAS_FLAG)
        paintFill!!.alpha = 192
        paintFill!!.style = Style.FILL

        setColor(color)
    }

    fun setColor(color: Int) {
        paintStroke!!.color = color
        paintStroke!!.alpha = 255
        paintEmpty!!.color = color
        paintEmpty!!.alpha = 96
        paintFill!!.color = color
        paintFill!!.alpha = 192
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val width = (measuredWidth - paddingLeft - paddingRight).toFloat()
        val height = (measuredHeight - paddingTop - paddingBottom).toFloat()

        val barWidth = width / barMaxCount
        val barStep = height / barMaxCount

        for (i in 0 until barMaxCount) {
            val left = i * barWidth + paddingLeft
            val top = paddingTop + (barMaxCount - 1 - i) * barStep

            rect.left = left + barWidth * (1 - barWidthPercent) / 2
            rect.top = top

            rect.right = left + barWidth * barWidthPercent + barWidth * (1 - barWidthPercent) / 2
            rect.bottom = (measuredHeight - paddingBottom).toFloat()

            val currPaint = (if (i < barCount) paintFill else paintEmpty) ?: return
            val radius = dpToPx(resources, 2)
            canvas.drawRoundRect(rect, radius.toFloat(), radius.toFloat(), currPaint)
        }
    }

    fun setBarCount(updatedBarCount: Int, animate:Boolean) {
        ui {
            if (barCount != updatedBarCount) {
                barCount = updatedBarCount
                invalidate()
            }
            if (animate) {
                val animation = AlphaAnimation(0.0f, 1.0f)
                animation.duration = 500
                this@XYSignalBars.startAnimation(animation)
            }
        }
    }

    fun dpToPx(res: Resources, dp: Int): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), res.displayMetrics).toInt()
    }

    companion object {
        private const val barWidthPercent = 0.70f
    }

}
    