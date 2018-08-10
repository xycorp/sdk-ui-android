package network.xyo.ui

import android.graphics.Canvas
import android.graphics.ColorFilter
import android.graphics.Paint
import android.graphics.PixelFormat
import android.graphics.Typeface
import android.graphics.drawable.Drawable

class XYDrawableText(private val _text: String, color: Int, private val _size: Float, typeFace: Typeface) : Drawable() {
    private val _paint: Paint

    init {

        _paint = Paint()
        _paint.typeface = typeFace
        _paint.color = color
        _paint.textSize = _size
        _paint.isAntiAlias = true
        _paint.isFakeBoldText = true
        _paint.style = Paint.Style.FILL
        _paint.textAlign = Paint.Align.LEFT
    }

    override fun draw(canvas: Canvas) {
        canvas.drawText(_text, 0f, _size, _paint)
    }

    override fun setAlpha(alpha: Int) {
        _paint.alpha = alpha
    }

    override fun setColorFilter(cf: ColorFilter?) {
        _paint.colorFilter = cf
    }

    override fun getOpacity(): Int {
        return PixelFormat.TRANSLUCENT
    }
}
