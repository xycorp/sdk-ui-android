package network.xyo.ui.helpers.overscroll.adapters

import android.view.View
import android.widget.AbsListView

import network.xyo.ui.helpers.overscroll.adapters.IOverScrollDecoratorAdapter

/**
 * An adapter to enable over-scrolling over object of [AbsListView], namely [ ] and it's extensions, and [android.widget.GridView].
 *
 * @author amit
 *
 * @see HorizontalOverScrollBounceEffectDecorator
 *
 * @see VerticalOverScrollBounceEffectDecorator
 */
class AbsListViewOverScrollDecorAdapter(protected val mView: AbsListView) : IOverScrollDecoratorAdapter {

    override val view: View
        get() = mView

    override val isInAbsoluteStart: Boolean
        get() = mView.childCount > 0 && !canScrollListUp()

    override val isInAbsoluteEnd: Boolean
        get() = mView.childCount > 0 && !canScrollListDown()

    private fun canScrollListUp(): Boolean {
        // Ported from AbsListView#canScrollList() which isn't compatible to all API levels
        val firstTop = mView.getChildAt(0).top
        val firstPosition = mView.firstVisiblePosition
        return firstPosition > 0 || firstTop < mView.listPaddingTop
    }

    private fun canScrollListDown(): Boolean {
        // Ported from AbsListView#canScrollList() which isn't compatible to all API levels
        val childCount = mView.childCount
        val itemsCount = mView.count
        val firstPosition = mView.firstVisiblePosition
        val lastPosition = firstPosition + childCount
        val lastBottom = mView.getChildAt(childCount - 1).bottom
        return lastPosition < itemsCount || lastBottom > mView.height - mView.listPaddingBottom
    }
}
