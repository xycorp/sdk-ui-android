package network.xyo.ui.dialogs

/*
 Created by arietrouw on 12/27/17.
 */

import android.app.Dialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import network.xyo.ui.R
import network.xyo.ui.ui

class XYThrobberDialog(context: Context) : Dialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_throbber)
        val window = this.window
        if (window != null) {
            this.window!!.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))

            val dividerId = this.context.resources.getIdentifier("android:id/titleDivider", null, null)
            val divider = this.findViewById<View>(dividerId)
            if (divider != null) {
                divider.visibility = View.GONE
            }
        }
    }

    override fun show() {
        ui {
            super.show()
        }
    }

    override fun hide() {
        ui {
            super.hide()
        }
    }

    companion object {

        private val TAG = XYThrobberDialog::class.java.simpleName
    }
}
