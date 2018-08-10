package network.xyo.ui.views

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet

open class XYRibbon (context: Context, attrs: AttributeSet?, defStyle: Int) : RecyclerView(context, attrs, defStyle) {

    open var listener: XYPanel.Listener? = null
}