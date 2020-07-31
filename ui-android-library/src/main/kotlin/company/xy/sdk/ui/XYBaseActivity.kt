package company.xy.sdk.ui

import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.Keep
import androidx.appcompat.app.AppCompatActivity
import network.xyo.base.XYBase
import network.xyo.base.XYLogging

import company.xy.sdk.ui.dialogs.XYThrobberDialog
import company.xy.sdk.ui.views.XYToolbar

import java.net.URL

open class XYBaseActivity : AppCompatActivity() {

    open var toolbar: XYToolbar? = null

    open var throbber: XYThrobberDialog? = null

    open val tag: String
        get () {
            return sourceNameFromAny(this)
        }

    open val log = XYLogging(this.classNameFromObject())

    private fun classNameFromObject(): String {
        val parts = this.javaClass.kotlin.simpleName?.split('.') ?: return "Unknown"
        return parts[parts.lastIndex]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        log.status("Activity Created: $tag")
        throbber = XYThrobberDialog(this)
    }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        log.status("Activity Created: $tag")
        throbber = XYThrobberDialog(this)
    }

    override fun onPostCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onPostCreate(savedInstanceState, persistentState)
        toolbar = findViewById(R.id.toolbar)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        toolbar = findViewById(R.id.toolbar)
    }

    override fun onResume() {
        log.status("Activity Resumed: $tag")
        super.onResume()
        activityCount++
        log.info("onResume:$activityCount:$tag")
    }

    public override fun onStart() {
        log.status("Activity Started: $tag")
        super.onStart()
    }

    public override fun onStop() {
        log.status("Activity Stopped: $tag")
        throbber?.dismiss()
        super.onStop()
        activityCount--
    }

    override fun onDestroy() {
        log.status("Activity Destroyed: $tag")
        throbber?.dismiss()
        super.onDestroy()
    }

    override fun onPause() {
        log.status("Activity Paused: $tag")
        throbber?.dismiss()
        hideKeyboard()
        super.onPause()
    }

    open fun getRemoteFile(location: String, useCache: Boolean = true): ByteArray? {
        val url: URL = if (useCache) {
            URL(location)
        } else {
            URL(location + "?t=" + Math.random())
        }
        val inputStream = url.openStream()

        val result = inputStream.readBytes()

        inputStream.close()

        return result
    }

    fun getXorValue(array: ByteArray): Byte {
        var cooked = 0
        for (i in array.indices) {
            cooked = (cooked xor array[i].toInt())
        }
        return cooked.toByte()
    }

    open fun showToast(message: String) {
        log.info("showProgressBar")
        ui { Toast.makeText(this@XYBaseActivity, message, Toast.LENGTH_LONG).show() }
    }

    open fun hideKeyboard() {
        log.info("hideKeyboard")
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        //Find the currently focused view, so we can grab the correct window token from it.
        var view = currentFocus
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = View(this)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    companion object: XYBase() {

        var activityCount = 0

        private fun classNameFromObject(objectToCheck: Any): String {
            val parts = objectToCheck.javaClass.kotlin.simpleName?.split('.') ?: return "Unknown"
            return parts[parts.lastIndex]
        }

        private fun sourceNameFromAny(source: Any): String {
            return (source as? String) ?: classNameFromObject(source)
        }

        val isForeground: Boolean
            get() = activityCount > 0
    }
}

