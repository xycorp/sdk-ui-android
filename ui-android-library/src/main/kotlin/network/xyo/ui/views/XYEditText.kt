package network.xyo.ui.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.Drawable
import androidx.appcompat.widget.AppCompatEditText
import android.util.AttributeSet
import android.view.ContextThemeWrapper
import android.view.inputmethod.InputMethodManager
import network.xyo.ui.R
import network.xyo.ui.XYGlobalFonts

/**
 * Created by arietrouw on 1/15/17.
 */

class XYEditText @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) :
        AppCompatEditText(ContextThemeWrapper(context, R.style.xy_edittext), attrs, defStyle) {
    private val _hintPaint: Paint
    private val _hintRect = Rect()
    private var _hintMargin = 0
    private var _readOnly = false

    var isReadOnly: Boolean
        get() = _readOnly
        set(readOnly) {
            _readOnly = readOnly
            updateFocusable()
        }

    private val _showImeRunnable = Runnable {
        val imm = getContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(this@XYEditText, 0)
    }

    init {

        this.typeface = XYGlobalFonts.getFontAwesome(context)

        if (attrs != null) {
            val attributeArray = getContext().obtainStyledAttributes(
                    attrs,
                    R.styleable.XYEditText,
                    0, 0)
            _readOnly = attributeArray.getBoolean(R.styleable.XYEditText_readOnly, false)
            attributeArray.recycle()
        }

        _hintPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        _hintPaint.color = currentHintTextColor
        _hintPaint.typeface = XYGlobalFonts.getFont(context)
        _hintPaint.textSize = convertDpToPixel(8f, context)
        _hintPaint.textAlign = Paint.Align.LEFT

        _hintMargin = convertDpToPixel(4f, context).toInt()

        updateFocusable()
        updatePadding()

    }

    private fun updateFocusable() {
        isFocusable = isEnabled && !isReadOnly
        isFocusableInTouchMode = isEnabled && !isReadOnly
        setSelectAllOnFocus(true)

    }

    private fun updatePadding() {
        val hint = hint
        if (hint != null && hint.isNotEmpty()) {
            val padding = paddingTop + paddingBottom
            setPadding(paddingLeft, padding * 75 / 100, paddingRight, padding * 25 / 100)
        }
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        updateFocusable()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val hint = hint
        val text = text
        if (text != null && text.isNotEmpty() && hint != null && hint.isNotEmpty()) {
            _hintPaint.getTextBounds(hint.toString(), 0, hint.toString().length, _hintRect)
            canvas.drawText(getHint().toString(), _hintMargin.toFloat(), (_hintRect.height() + _hintMargin).toFloat(), _hintPaint)
        }
    }

    override fun setError(error: CharSequence?, icon: Drawable?) {
        setCompoundDrawables(null, null, icon, null)
    }

    private fun setImeVisibility(visible: Boolean) {
        if (visible) {
            postDelayed(_showImeRunnable, 100)
        } else {
            removeCallbacks(_showImeRunnable)
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

            imm.hideSoftInputFromWindow(this.windowToken, 0)
        }
    }

    fun setAttention() {
        requestFocus()
        setImeVisibility(true)
        selectAll()
    }

    companion object {

        private val TAG = XYEditText::class.java.simpleName

        fun convertDpToPixel(dp: Float, context: Context): Float {
            val resources = context.resources
            val metrics = resources.displayMetrics
            return dp * (metrics.densityDpi / 160f)
        }
    }

}
