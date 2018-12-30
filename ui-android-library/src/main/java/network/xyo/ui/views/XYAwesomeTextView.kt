package network.xyo.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.ContextThemeWrapper
import network.xyo.ui.R
import network.xyo.ui.XYGlobalFonts

/**
 * Created by arietrouw on 1/15/17.
 */

class XYAwesomeTextView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) :
        XYTextView(ContextThemeWrapper(context, R.style.xy_awesome_textview), attrs, defStyle) {

    init {
        XYGlobalFonts.setViewFontAwesome(context, this)
    }
}
