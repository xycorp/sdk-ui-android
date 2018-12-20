package network.xyo.ui

import android.content.Context
import android.graphics.Typeface
import android.view.View
import android.widget.TextView
import network.xyo.core.XYBase

class XYGlobalFonts : XYBase() {
    companion object {
        private var _font: Array<Typeface>? = null
        private var _awesome: Array<Typeface>? = null

        @JvmOverloads
        fun getFontAwesome(context: Context, style: Int = Typeface.NORMAL): Typeface {
            synchronized(XYGlobalFonts::class.java) {
                if (_awesome == null) {
                    _awesome = Array(4) { _ -> Typeface.DEFAULT }
                    val awesome = _awesome!!
                    try {
                        awesome[Typeface.NORMAL] = Typeface.createFromAsset(context.assets, "fonts/FontAwesome.otf")
                    } catch (ex: Exception) {
                        awesome[Typeface.NORMAL] = Typeface.DEFAULT
                        logError("XYGlobalFonts", "Exception: ${ex.toString()}", true)
                    }

                    for (i in 1..3) {
                        awesome[i] = Typeface.create(awesome[Typeface.NORMAL], i)
                    }
                }
            }
            return _awesome!![style]
        }

        @JvmOverloads
        fun getFont(context: Context, style: Int = Typeface.NORMAL): Typeface {
            synchronized(XYGlobalFonts::class.java) {
                if (_font == null) {
                    _font = Array(4) { _ -> Typeface.DEFAULT }
                    val font = _font!!
                    try {
                        font[Typeface.NORMAL] = Typeface.createFromAsset(context.assets, "fonts/Quicksand.otf")
                    } catch (ex: Exception) {
                        font[Typeface.NORMAL] = Typeface.DEFAULT
                        logError("XYGlobalFonts", ex, true)
                    }

                    for (i in 1..3) {
                        font[i] = Typeface.create(font[Typeface.NORMAL], i)
                    }
                }
            }
            return _font!![style]
        }

        fun setViewFont(context: Context, view: TextView) {
            val typeFace = view.typeface
            if (typeFace != null) {
                view.typeface = getFont(context, typeFace.style)
            } else {
                view.typeface = getFont(context)
            }
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

        fun setViewFontAwesome(context: Context, view: TextView) {
            val typeFace = view.typeface
            if (typeFace != null) {
                view.typeface = getFontAwesome(context, typeFace.style)
            } else {
                view.typeface = getFontAwesome(context)
            }
        }

        @JvmOverloads
        fun getFontAwesomeDrawable(context: Context, text: Int, color: Int, size: Float, style: Int = Typeface.NORMAL): XYDrawableText {
            return XYDrawableText(context.resources.getString(text), @Suppress("DEPRECATION") context.resources.getColor(color), size, getFontAwesome(context, style))
        }
    }
}
