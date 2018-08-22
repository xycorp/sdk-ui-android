package network.xyo.ui.views

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.RecyclerView

open class XYRibbon (context: Context, attrs: AttributeSet?, defStyle: Int) : RecyclerView(context, attrs, defStyle) {

    open var listener: XYPanel.Listener? = null
}