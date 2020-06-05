package company.xy.sdk.ui.sample

import android.os.Bundle

import company.xy.sdk.ui.XYBaseActivity

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
