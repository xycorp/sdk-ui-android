package network.xyo.ui.helpers.overscroll.adapters

import android.graphics.Canvas
import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

/**
 * @author amitd
 *
 * @see HorizontalOverScrollBounceEffectDecorator
 *
 * @see VerticalOverScrollBounceEffectDecorator
 */
class RecyclerViewOverScrollDecorAdapter : IOverScrollDecoratorAdapter {

    private val mRecyclerView: RecyclerView
    private val mImpl: Impl

    override val view: View
        get() = mRecyclerView

    var helperCallback: ItemTouchHelperCallback? = null

    override val isInAbsoluteStart: Boolean
        get() {
            helperCallback?.mIsItemTouchInEffect?.let {
                return !it && mImpl.isInAbsoluteStart
            }
            return false
        }

    override val isInAbsoluteEnd: Boolean
        get() {
            helperCallback?.mIsItemTouchInEffect?.let {
                return !it && mImpl.isInAbsoluteEnd
            }
            return false
        }

    /**
     * A delegation of the adapter implementation of this view that should provide the processing
     * of [.isInAbsoluteStart] and [.isInAbsoluteEnd]. Essentially needed simply
     * because the implementation depends on the layout manager implementation being used.
     */
    interface Impl {
        val isInAbsoluteStart: Boolean
        val isInAbsoluteEnd: Boolean
    }

    constructor(recyclerView: RecyclerView) {

        mRecyclerView = recyclerView

        val layoutManager = recyclerView.layoutManager
        mImpl = if (layoutManager is LinearLayoutManager || layoutManager is StaggeredGridLayoutManager) {
            val orientation = if (layoutManager is LinearLayoutManager)
                (layoutManager).orientation
            else
                (layoutManager as StaggeredGridLayoutManager).orientation

            if (orientation == LinearLayoutManager.HORIZONTAL) {
                ImplHorizLayout()
            } else {
                ImplVerticalLayout()
            }
        } else {
            throw IllegalArgumentException("Recycler views with custom layout managers are not supported by this adapter out of the box." + "Try implementing and providing an explicit 'impl' parameter to the other c'tors, or otherwise create a custom adapter subclass of your own.")
        }
    }

    constructor(recyclerView: RecyclerView, impl: Impl) {
        mRecyclerView = recyclerView
        mImpl = impl
    }

    constructor(recyclerView: RecyclerView, itemTouchHelperCallback: ItemTouchHelperCallback) : this(recyclerView) {
        setUpTouchHelperCallback(itemTouchHelperCallback)
    }

    constructor(recyclerView: RecyclerView, impl: Impl, itemTouchHelperCallback: ItemTouchHelperCallback) : this(recyclerView, impl) {
        setUpTouchHelperCallback(itemTouchHelperCallback)
    }

    abstract class ItemTouchHelperCallback: ItemTouchHelper.Callback() {

        var mIsItemTouchInEffect = false

        override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
            mIsItemTouchInEffect = actionState != 0
            super.onSelectedChanged(viewHolder, actionState)
        }
    }

    private fun setUpTouchHelperCallback(itemTouchHelperCallback: ItemTouchHelperCallback) {
        helperCallback = itemTouchHelperCallback
        ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(mRecyclerView)
    }

    private inner class ImplHorizLayout : Impl {

        override val isInAbsoluteStart: Boolean
            get() = !mRecyclerView.canScrollHorizontally(-1)

        override val isInAbsoluteEnd: Boolean
            get() = !mRecyclerView.canScrollHorizontally(1)
    }

    private inner class ImplVerticalLayout : Impl {

        override val isInAbsoluteStart: Boolean
            get() = !mRecyclerView.canScrollVertically(-1)

        override val isInAbsoluteEnd: Boolean
            get() = !mRecyclerView.canScrollVertically(1)
    }
}
