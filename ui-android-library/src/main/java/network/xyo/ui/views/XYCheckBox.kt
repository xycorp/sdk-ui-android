package network.xyo.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.ContextThemeWrapper
import androidx.appcompat.widget.AppCompatCheckBox
import network.xyo.ui.R

import network.xyo.ui.XYGlobalFonts

/**
 * Created by arietrouw on 1/15/17.
 */

class XYCheckBox @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) :
        AppCompatCheckBox(ContextThemeWrapper(context, R.style.xy_checkbox), attrs, defStyle) {

    init {
        XYGlobalFonts.setViewFont(context, this)
    }
}
