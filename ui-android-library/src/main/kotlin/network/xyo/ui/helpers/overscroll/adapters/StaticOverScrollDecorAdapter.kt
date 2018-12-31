package network.xyo.ui.helpers.overscroll.adapters
import android.view.View

/**
 * A static adapter for views that are ALWAYS over-scroll-able (e.g. image view).
 *
 * @author amit
 *
 * @see HorizontalOverScrollBounceEffectDecorator
 *
 * @see VerticalOverScrollBounceEffectDecorator
 */
class StaticOverScrollDecorAdapter(override val view: View) : IOverScrollDecoratorAdapter {

    override val isInAbsoluteStart: Boolean
        get() = true

    override val isInAbsoluteEnd: Boolean
        get() = true
}
