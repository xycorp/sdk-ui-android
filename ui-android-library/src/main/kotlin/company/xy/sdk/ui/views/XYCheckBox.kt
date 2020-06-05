package company.xy.sdk.ui.views

import android.content.Context
import androidx.appcompat.widget.AppCompatCheckBox
import android.util.AttributeSet
import android.view.ContextThemeWrapper
import company.xy.sdk.ui.R
import company.xy.sdk.ui.XYGlobalFonts

class XYCheckBox @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) :
        AppCompatCheckBox(ContextThemeWrapper(context, R.style.xy_checkbox), attrs, defStyle) {

    init {
        this.typeface = XYGlobalFonts.getFontAwesome(context)
    }
}
