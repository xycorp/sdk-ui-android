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
import network.xyo.ui.helpers.overscroll.adapters.AbsListViewOverScrollDecorAdapter
import network.xyo.ui.helpers.overscroll.adapters.HorizontalScrollViewOverScrollDecorAdapter

/**
 * @author amit
 */
object OverScrollDecoratorHelper {

    const val ORIENTATION_VERTICAL = 0
    const val ORIENTATION_HORIZONTAL = 1

    fun setUpOverScroll(recyclerView: RecyclerView, orientation: Int): IOverScrollDecor {
        return when (orientation) {
            ORIENTATION_HORIZONTAL -> HorizontalOverScrollBounceEffectDecorator(RecyclerViewOverScrollDecorAdapter(recyclerView))
            ORIENTATION_VERTICAL -> VerticalOverScrollBounceEffectDecorator(RecyclerViewOverScrollDecorAdapter(recyclerView))
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
    
    fun setUpStaticOverScroll(view: View, orientation: Int): IOverScrollDecor {
        return when (orientation) {
            ORIENTATION_HORIZONTAL -> HorizontalOverScrollBounceEffectDecorator(StaticOverScrollDecorAdapter(view))

            ORIENTATION_VERTICAL -> VerticalOverScrollBounceEffectDecorator(StaticOverScrollDecorAdapter(view))

            else -> throw IllegalArgumentException("orientation")
        }
    }

    fun setUpOverScroll(viewPager: ViewPager): IOverScrollDecor {
        return HorizontalOverScrollBounceEffectDecorator(ViewPagerOverScrollDecorAdapter(viewPager))
    }

}
