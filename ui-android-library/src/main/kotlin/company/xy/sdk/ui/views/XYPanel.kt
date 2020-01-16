package company.xy.sdk.ui.views

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout

open class XYPanel (context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) : LinearLayout(context, attrs, defStyle) {
    open var listener: Listener? = null

    open class Listener {
        open fun selected(panel: XYPanel?) {}
    }

    init {
        setOnClickListener { listener?.selected(this@XYPanel) }

        setOnLongClickListener {
            listener?.selected(this@XYPanel)
            true
        }
    }
}