package company.xy.sdk.ui.views

import android.content.Context
import androidx.appcompat.widget.AppCompatTextView
import android.util.AttributeSet
import android.view.ContextThemeWrapper
import company.xy.sdk.ui.R
import company.xy.sdk.ui.XYGlobalFonts

open class XYTextView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) :
        AppCompatTextView(ContextThemeWrapper(context, R.style.xy_textview), attrs, defStyle) {

    init {

        this.typeface = XYGlobalFonts.getFont(context)
    }

    override fun performClick(): Boolean {
        super.performClick()
        return true
    }
}
