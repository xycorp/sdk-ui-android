package network.xyo.ui.helpers.overscroll

import android.view.View
import android.widget.GridView
import android.widget.HorizontalScrollView
import android.widget.ListView
import android.widget.ScrollView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import network.xyo.ui.helpers.overscroll.adapters.RecyclerViewOverScrollDecorAdapter
import network.xyo.ui.helpers.overscroll.adapters.ScrollViewOverScrollDecorAdapter
import network.xyo.ui.helpers.overscroll.adapters.StaticOverScrollDecorAdapter
import network.xyo.ui.helpers.overscroll.adapters.ViewPagerOverScrollDecorAdapter
import network.xyo.ui.overscroll.AbsListViewOverScrollDecorAdapter
import network.xyo.ui.overscroll.HorizontalScrollViewOverScrollDecorAdapter

/**
 * @author amit
 */
object OverScrollDecoratorHelper {

    val ORIENTATION_VERTICAL = 0
    val ORIENTATION_HORIZONTAL = 1

    /**
     * Set up the over-scroll effect over a specified [RecyclerView] view.
     * <br></br>Only recycler-views using **native** Android layout managers (i.e. [LinearLayoutManager],
     * [GridLayoutManager] and [StaggeredGridLayoutManager]) are currently supported
     * by this convenience method.
     *
     * @param recyclerView The view.
     * @param orientation Either [.ORIENTATION_HORIZONTAL] or [.ORIENTATION_VERTICAL].
     *
     * @return The over-scroll effect 'decorator', enabling further effect configuration.
     */
    fun setUpOverScroll(recyclerView: RecyclerView, orientation: Int): IOverScrollDecor {
        when (orientation) {
            ORIENTATION_HORIZONTAL -> return HorizontalOverScrollBounceEffectDecorator(RecyclerViewOverScrollDecorAdapter(recyclerView))
            ORIENTATION_VERTICAL -> return VerticalOverScrollBounceEffectDecorator(RecyclerViewOverScrollDecorAdapter(recyclerView))
            else -> throw IllegalArgumentException("orientation")
        }
    }

    fun setUpOverScroll(listView: ListView): IOverScrollDecor {
        return VerticalOverScrollBounceEffectDecorator(AbsListViewOverScrollDecorAdapter(listView))
    }

    fun setUpOverScroll(gridView: GridView): IOverScrollDecor {
        return VerticalOverScrollBounceEffectDecorator(AbsListViewOverScrollDecorAdapter(gridView))
    }

    fun setUpOverScroll(scrollView: ScrollView): IOverScrollDecor {
        return VerticalOverScrollBounceEffectDecorator(ScrollViewOverScrollDecorAdapter(scrollView))
    }

    fun setUpOverScroll(scrollView: HorizontalScrollView): IOverScrollDecor {
        return HorizontalOverScrollBounceEffectDecorator(HorizontalScrollViewOverScrollDecorAdapter(scrollView))
    }

    /**
     * Set up the over-scroll over a generic view, assumed to always be over-scroll ready (e.g.
     * a plain text field, image view).
     *
     * @param view The view.
     * @param orientation One of [.ORIENTATION_HORIZONTAL] or [.ORIENTATION_VERTICAL].
     *
     * @return The over-scroll effect 'decorator', enabling further effect configuration.
     */
    fun setUpStaticOverScroll(view: View, orientation: Int): IOverScrollDecor {
        when (orientation) {
            ORIENTATION_HORIZONTAL -> return HorizontalOverScrollBounceEffectDecorator(StaticOverScrollDecorAdapter(view))

            ORIENTATION_VERTICAL -> return VerticalOverScrollBounceEffectDecorator(StaticOverScrollDecorAdapter(view))

            else -> throw IllegalArgumentException("orientation")
        }
    }

    fun setUpOverScroll(viewPager: ViewPager): IOverScrollDecor {
        return HorizontalOverScrollBounceEffectDecorator(ViewPagerOverScrollDecorAdapter(viewPager))
    }

}
