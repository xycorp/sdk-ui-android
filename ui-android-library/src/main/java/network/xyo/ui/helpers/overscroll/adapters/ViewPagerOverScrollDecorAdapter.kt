package network.xyo.ui.helpers.overscroll.adapters

import android.view.View
import androidx.viewpager.widget.ViewPager

/**
 * Created by Bruce too
 * Enhance by amit
 * On 2016/6/16
 * At 14:51
 * An adapter to enable over-scrolling over object of [ViewPager]
 *
 * @see HorizontalOverScrollBounceEffectDecorator
 */
class ViewPagerOverScrollDecorAdapter(protected val mViewPager: ViewPager) : IOverScrollDecoratorAdapter, ViewPager.OnPageChangeListener {

    protected var mLastPagerPosition = 0
    protected var mLastPagerScrollOffset: Float = 0.toFloat()

    override val view: View
        get() = mViewPager

    override val isInAbsoluteStart: Boolean
        get() = mLastPagerPosition == 0 && mLastPagerScrollOffset == 0f

    override val isInAbsoluteEnd: Boolean
        get() = mLastPagerPosition == mViewPager.adapter!!.count - 1 && mLastPagerScrollOffset == 0f

    init {

        mViewPager.addOnPageChangeListener(this)

        mLastPagerPosition = mViewPager.currentItem
        mLastPagerScrollOffset = 0f
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        mLastPagerPosition = position
        mLastPagerScrollOffset = positionOffset
    }

    override fun onPageSelected(position: Int) {

    }

    override fun onPageScrollStateChanged(state: Int) {

    }
}
