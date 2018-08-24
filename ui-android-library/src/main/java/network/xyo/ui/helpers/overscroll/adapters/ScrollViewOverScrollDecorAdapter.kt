package network.xyo.ui.helpers.overscroll.adapters

import android.view.View
import android.widget.ScrollView

/**
 * An adapter that enables over-scrolling over a [ScrollView].
 * <br></br>Seeing that [ScrollView] only supports vertical scrolling, this adapter
 * should only be used with a [VerticalOverScrollBounceEffectDecorator]. For horizontal
 * over-scrolling, use [HorizontalScrollViewOverScrollDecorAdapter] in conjunction with
 * a [android.widget.HorizontalScrollView].
 *
 * @author amit
 *
 * @see HorizontalOverScrollBounceEffectDecorator
 *
 * @see VerticalOverScrollBounceEffectDecorator
 */
class ScrollViewOverScrollDecorAdapter(protected val mView: ScrollView) : IOverScrollDecoratorAdapter {

    override val view: View
        get() = mView

    override val isInAbsoluteStart: Boolean
        get() = !mView.canScrollVertically(-1)

    override val isInAbsoluteEnd: Boolean
        get() = !mView.canScrollVertically(1)
}
