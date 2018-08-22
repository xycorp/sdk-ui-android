package network.xyo.ui.views

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.os.Handler
import android.util.AttributeSet
import android.view.ContextThemeWrapper
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.AppCompatButton
import network.xyo.ui.R

import network.xyo.ui.XYGlobalFonts


open class XYButton @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) :
        AppCompatButton(ContextThemeWrapper(context, R.style.xy_button), attrs, defStyle) {
    private var _onClickListener: View.OnClickListener? = null

    private val activity: Activity?
        get() {
            var context = context
            while (context is ContextWrapper) {
                if (context is Activity) {
                    return context
                }
                context = context.baseContext
            }
            return null
        }

    init {

        XYGlobalFonts.setViewFont(context, this)

        super.setOnClickListener { v ->
            hideKeyBoard()
            if (_onClickListener != null) {
                _onClickListener!!.onClick(v)
            }
        }
    }

    override fun setOnClickListener(listener: View.OnClickListener?) {
        _onClickListener = listener
    }

    override fun performClick(): Boolean {
        super.performClick()
        return true
    }

    fun hideKeyBoard() {
        Handler().postDelayed({
            val activity = activity
            if (activity != null) {
                val view = activity.currentFocus
                if (view != null) {
                    val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(view.windowToken, 0)
                }
            }
        }, 50)
    }

    companion object {

        private val TAG = XYButton::class.java.simpleName
    }

}
