package company.xy.sdk.ui.dialogs

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDialog
import company.xy.sdk.ui.R
import company.xy.sdk.ui.ui

class XYThrobberDialog(context: Context) : AppCompatDialog(context) {

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

        setCanceledOnTouchOutside(false)
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
