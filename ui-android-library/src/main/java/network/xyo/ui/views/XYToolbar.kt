package network.xyo.ui.views

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.ContextThemeWrapper
import android.view.View
import network.xyo.ui.R

import network.xyo.ui.XYBaseActivity

/**
 * Created by arietrouw on 1/23/17.
 */

class XYToolbar : android.support.v7.widget.Toolbar {

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
        navigationIcon = @Suppress("DEPRECATION") resources.getDrawable(R.drawable.xy_ui_toolbar_menu)
        setNavigationOnClickListener(onClickListener)
    }

    fun enableBackNavigation(activity: Activity) {
        isBackNavigationEnabled = true
        navigationIcon = @Suppress("DEPRECATION") resources.getDrawable(R.drawable.xy_ui_toolbar_back)
        setNavigationOnClickListener { activity.onBackPressed() }
    }

    fun enableBackNavigation(onClickListener: View.OnClickListener) {
        isBackNavigationEnabled = true
        navigationIcon = @Suppress("DEPRECATION") resources.getDrawable(R.drawable.xy_ui_toolbar_back)
        setNavigationOnClickListener(onClickListener)
    }

    companion object {

        private val TAG = XYToolbar::class.java.simpleName
    }
}
