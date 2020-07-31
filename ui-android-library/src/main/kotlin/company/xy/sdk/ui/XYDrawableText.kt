package company.xy.sdk.ui

import android.graphics.Canvas
import android.graphics.ColorFilter
import android.graphics.Paint
import android.graphics.PixelFormat
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import androidx.annotation.Keep

@Keep
class XYDrawableText(private val _text: String, color: Int, private val _size: Float, typeFace: Typeface) : Drawable() {
    private val paint = Paint()

    init {
        paint.typeface = typeFace
        paint.color = color
        paint.textSize = _size
        paint.isAntiAlias = true
        paint.isFakeBoldText = true
        paint.style = Paint.Style.FILL
        paint.textAlign = Paint.Align.LEFT
    }

    override fun draw(canvas: Canvas) {
        canvas.drawText(_text, 0f, _size, paint)
    }

    override fun setAlpha(alpha: Int) {
        paint.alpha = alpha
    }

    override fun setColorFilter(cf: ColorFilter?) {
        paint.colorFilter = cf
    }

    override fun getOpacity(): Int {
        return PixelFormat.TRANSLUCENT
    }
}
