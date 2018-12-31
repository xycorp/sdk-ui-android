package network.xyo.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.ContextThemeWrapper
import network.xyo.ui.R
import network.xyo.ui.XYGlobalFonts

class XYAwesomeButton @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) : XYButton(ContextThemeWrapper(context, R.style.xy_awesome_button), attrs, defStyle) {

    init {
        XYGlobalFonts.setViewFontAwesome(context, this)
    }
}
