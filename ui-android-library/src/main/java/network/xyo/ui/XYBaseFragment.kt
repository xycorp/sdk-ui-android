package network.xyo.ui

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import network.xyo.core.XYBase
import network.xyo.core.XYLogging

import network.xyo.ui.dialogs.XYThrobberDialog

open class XYBaseFragment : Fragment() {

    var throbber: XYThrobberDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        log.info("onCreate")
        super.onCreate(savedInstanceState)
        throbber = XYThrobberDialog(activity as Activity)
    }

    open fun loadViews() {

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        log.info("onCreateView")
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        loadViews()
    }

    open fun dpToPx(res: Resources, dp: Int): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), res.displayMetrics).toInt()
    }

    override fun onResume() {
        log.info("onResume")
        super.onResume()
    }

    override fun onPause() {
        log.info("onPause")
        super.onPause()
        throbber?.dismiss()
    }

    protected fun showToast(message: String) {
        log.info("showToast")
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
    }

    override fun onAttach(context: Context) {
        log.info("onAttach")
        super.onAttach(context)
    }

    companion object {
        private val log = XYLogging(XYBaseFragment::class.java.simpleName)
    }
}

