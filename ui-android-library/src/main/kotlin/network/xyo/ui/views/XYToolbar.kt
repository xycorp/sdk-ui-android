package network.xyo.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.ContextThemeWrapper
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import network.xyo.ui.R
import network.xyo.ui.XYBaseActivity

class XYToolbar : Toolbar {

    var isBackNavigationEnabled = false
        private set

    constructor(context: Context) : super(ContextThemeWrapper(context, R.style.xy_toolbar)) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet) :
            super(ContextThemeWrapper(context, R.style.xy_toolbar), attrs) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init(context)
    }

    private fun init(context: Context) {
        if (context is XYBaseActivity) {
            context.setSupportActionBar(this)
            val actionBar = context.supportActionBar
            if (actionBar != null) {
                actionBar.setDisplayShowHomeEnabled(true)
                actionBar.setDisplayHomeAsUpEnabled(false)
                actionBar.setDisplayShowCustomEnabled(true)
                actionBar.setDisplayShowTitleEnabled(false)
            }
        }
    }

    fun enableMenuNavigation(onClickListener: View.OnClickListener) {
        isBackNavigationEnabled = false
        navigationIcon = ContextCompat.getDrawable(context, R.drawable.xy_ui_toolbar_menu)
        setNavigationOnClickListener(onClickListener)
    }

    fun enableBackNavigation(activity: XYBaseActivity) {
        isBackNavigationEnabled = true
        navigationIcon = ContextCompat.getDrawable(context, R.drawable.xy_ui_toolbar_back)
        setNavigationOnClickListener { activity.onBackPressed() }
    }

    fun enableBackNavigation(onClickListener: View.OnClickListener) {
        isBackNavigationEnabled = true
        navigationIcon = ContextCompat.getDrawable(context, R.drawable.xy_ui_toolbar_back)
        setNavigationOnClickListener(onClickListener)
    }

    companion object {

        private val TAG = XYToolbar::class.java.simpleName
    }
}
