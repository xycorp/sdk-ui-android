package network.xyo.ui

import android.content.Context
import android.graphics.Typeface
import android.view.View
import android.widget.TextView
import network.xyo.core.XYBase
import java.lang.NullPointerException

class XYGlobalFonts : XYBase() {
    companion object : XYBase() {
        private var fonts = HashMap<String, Array<Typeface>>()

        private fun getTypefaces(context: Context, fontPath: String): Array<Typeface> {
            val result = Array(4) { Typeface.DEFAULT }
            try {
                result[Typeface.NORMAL] = Typeface.createFromAsset(context.assets, fontPath)
            } catch (ex: Exception) {
                result[Typeface.NORMAL] = Typeface.DEFAULT
                log.error(ex, true)
            }

            for (i in 1..3) {
                result[i] = Typeface.create(result[Typeface.NORMAL], i)
            }
            return result
        }

        private fun getFontFromName(context: Context, name:String, path: String): Typeface {
            synchronized(fonts) {
                if (fonts[name] == null) {
                    fonts[name] = getTypefaces(context, path)
                }
            }
            fonts[name]?.let {
                return it[Typeface.NORMAL]
            }
            throw NullPointerException()
        }

        fun getFontAwesome(context: Context): Typeface {
            return getFontFromName(context, "awesome", "fonts/FontAwesome.otf")
        }

        fun getFont(context: Context): Typeface {
            return getFontFromName(context, "font", "fonts/Quicksand.otf")
        }

        fun setPreferenceFont(context: Context, view: View) {
            setViewFont(context, view.findViewById<View>(android.R.id.title) as? TextView)
            setViewFont(context, view.findViewById<View>(android.R.id.summary) as? TextView)
        }

        fun setViewFont(context: Context, view: TextView?) {
            view?.typeface = getFont(context)
        }

        fun setViewFontAwesome(context: Context, view: TextView?) {
            view?.typeface = getFontAwesome(context)
        }

        fun getFontAwesomeDrawable(context: Context, text: Int, color: Int, size: Float): XYDrawableText {
            return XYDrawableText(context.resources.getString(text), @Suppress("DEPRECATION") context.resources.getColor(color), size, getFontAwesome(context))
        }
    }
}
