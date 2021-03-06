package company.xy.sdk.ui.views

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.os.Handler
import androidx.appcompat.widget.AppCompatButton
import android.util.AttributeSet
import android.view.ContextThemeWrapper
import android.view.inputmethod.InputMethodManager
import company.xy.sdk.ui.R
import company.xy.sdk.ui.XYGlobalFonts

open class XYButton @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) :
        AppCompatButton(ContextThemeWrapper(context, R.style.xy_button), attrs, defStyle) {
    private var _onClickListener: OnClickListener? = null

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

        this.typeface = XYGlobalFonts.getFont(context)

        super.setOnClickListener { v ->
            hideKeyboard()
            if (_onClickListener != null) {
                _onClickListener!!.onClick(v)
            }
        }
    }

    override fun setOnClickListener(listener: OnClickListener?) {
        _onClickListener = listener
    }

    override fun performClick(): Boolean {
        super.performClick()
        return true
    }

    open fun hideKeyboard() {
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
