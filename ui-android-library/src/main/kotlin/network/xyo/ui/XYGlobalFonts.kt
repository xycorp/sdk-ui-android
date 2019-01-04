package network.xyo.ui

import android.content.Context
import android.graphics.Typeface
import android.view.View
import android.widget.TextView
import network.xyo.core.XYBase

class XYGlobalFonts : XYBase() {
    companion object : XYBase() {
        private var _font: Array<Typeface>? = null
        private var _awesome: Array<Typeface>? = null

        private fun getTypefaces(context: Context, fontPath: String): Array<Typeface> {
            val result = Array(4) { _ -> Typeface.DEFAULT }
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

        fun getFontAwesome(context: Context, style: Int = Typeface.NORMAL): Typeface {
            synchronized(XYGlobalFonts::class.java) {
                if (_awesome == null) {
                    _awesome = getTypefaces(context, "fonts/FontAwesome.otf")
                }
            }
            return _awesome!![style]
        }

        fun getFont(context: Context, style: Int = Typeface.NORMAL): Typeface {
            synchronized(XYGlobalFonts::class.java) {
                if (_font == null) {
                    _font = getTypefaces(context, "fonts/Quicksand.otf")
                }
            }
            return _font!![style]
        }

        fun setPreferenceFont(context: Context, view: View) {
            val titleView = view.findViewById<View>(android.R.id.title) as? TextView
            if (titleView != null) {
                XYGlobalFonts.setViewFont(context, titleView)
            }

            val summaryView = view.findViewById<View>(android.R.id.summary) as? TextView
            if (summaryView != null) {
                XYGlobalFonts.setViewFont(context, summaryView)
            }
        }

        fun setViewFont(context: Context, view: TextView) {
            val typeFace = view.typeface
            if (typeFace != null) {
                view.typeface = getFont(context, typeFace.style)
            } else {
                view.typeface = getFont(context)
            }
        }

        fun setViewFontAwesome(context: Context, view: TextView) {
            val typeFace = view.typeface
            if (typeFace != null) {
                view.typeface = getFontAwesome(context, typeFace.style)
            } else {
                view.typeface = getFontAwesome(context)
            }
        }

        fun getFontAwesomeDrawable(context: Context, text: Int, color: Int, size: Float): XYDrawableText {
            return XYDrawableText(context.resources.getString(text), @Suppress("DEPRECATION") context.resources.getColor(color), size, getFontAwesome(context, Typeface.NORMAL))
        }
    }
}
