package network.xyo.ui.sample

import android.os.Bundle

import network.xyo.ui.XYBaseActivity

/**
 * Created by arietrouw on 12/29/17.
 */

class XYOSecondaryActivity : XYBaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.secondary_activity)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        toolbar?.enableBackNavigation(this)
        super.onPostCreate(savedInstanceState)
    }
}
