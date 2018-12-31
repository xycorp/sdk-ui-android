package network.xyo.ui.views

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.util.AttributeSet

open class XYRibbon (context: Context, attrs: AttributeSet?, defStyle: Int) : RecyclerView(context, attrs, defStyle) {

    open var listener: XYPanel.Listener? = null
}