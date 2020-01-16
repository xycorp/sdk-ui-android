package company.xy.sdk.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.ContextThemeWrapper
import company.xy.sdk.ui.R
import company.xy.sdk.ui.XYGlobalFonts

open class XYAwesomeButton @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) : XYButton(ContextThemeWrapper(context, R.style.xy_awesome_button), attrs, defStyle) {
    init {
        this.typeface = XYGlobalFonts.getFontAwesome(context)
    }
}
