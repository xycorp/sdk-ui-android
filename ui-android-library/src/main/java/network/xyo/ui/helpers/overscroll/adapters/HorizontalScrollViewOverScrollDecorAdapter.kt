package network.xyo.ui.overscroll

import android.view.View
import android.widget.HorizontalScrollView

import network.xyo.ui.helpers.overscroll.adapters.IOverScrollDecoratorAdapter

/**
 * An adapter that enables over-scrolling support over a [HorizontalScrollView].
 * <br></br>Seeing that [HorizontalScrollView] only supports horizontal scrolling, this adapter
 * should only be used with a [HorizontalOverScrollBounceEffectDecorator].
 *
 * @author amit
 *
 * @see HorizontalOverScrollBounceEffectDecorator
 *
 * @see VerticalOverScrollBounceEffectDecorator
 */
class HorizontalScrollViewOverScrollDecorAdapter(protected val mView: HorizontalScrollView) : IOverScrollDecoratorAdapter {

    override val view: View
        get() = mView

    override val isInAbsoluteStart: Boolean
        get() = !mView.canScrollHorizontally(-1)

    override val isInAbsoluteEnd: Boolean
        get() = !mView.canScrollHorizontally(1)
}
