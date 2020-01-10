package network.xyo.ui.views

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import me.everything.android.ui.overscroll.IOverScrollDecor
import me.everything.android.ui.overscroll.IOverScrollState
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper
import network.xyo.base.XYBase
import network.xyo.ui.ui

open class XYSmallRibbon(context: Context, attrs: AttributeSet?, defStyle: Int) : XYRibbon(context, attrs, defStyle) {

    private var _reloadTriggered = false
    private var _pendingAnimation = false

    private var _bounceTrigger = 40

    open class Listener : XYPanel.Listener () {
        open fun pull() {

        }
    }

    constructor(context: Context) : this(context, null, 0)

    constructor(context: Context, attrs: AttributeSet) : this(context, attrs, 0)

    init {
        log.info("init")

        layoutManager = object : LinearLayoutManager(context, HORIZONTAL, false) {
            override fun supportsPredictiveItemAnimations(): Boolean {
                return true
            }
        }

        this.initRecyclerView()

        _bounceTrigger = dpToPx(40f)
    }

    private fun setOverScrollUpdateListener(decor: IOverScrollDecor) {
        decor.setOverScrollUpdateListener { _, _, offset ->
            if (offset > _bounceTrigger) {
                _reloadTriggered = true
            }
        }
    }

    private fun setOverScrollStateListener(decor: IOverScrollDecor) {
        decor.setOverScrollStateListener { _, _, newState ->
            when (newState) {
                IOverScrollState.STATE_IDLE -> {
                    log.info("onOverScrollStateChange: STATE_IDLE")
                    if (_reloadTriggered) {
                        log.info("onOverScrollStateChange: _reloadTriggered")

                        (listener as Listener).pull()

                        _reloadTriggered = false
                        _pendingAnimation = true
                    }
                }
                IOverScrollState.STATE_DRAG_START_SIDE -> log.info( "onOverScrollUpdate: STATE_DRAG_START_SIDE")
                IOverScrollState.STATE_DRAG_END_SIDE -> log.info( "onOverScrollUpdate: STATE_DRAG_END_SIDE")
                IOverScrollState.STATE_BOUNCE_BACK -> log.info("onOverScrollUpdate: STATE_BOUNCE_BACK")
                else -> {
                }
            }
        }
    }

    open fun initRecyclerView() {
        log.info("initRecyclerView")
        adapter = adapter
        this.itemAnimator = null

        val decor = OverScrollDecoratorHelper.setUpOverScroll(this, OverScrollDecoratorHelper.ORIENTATION_HORIZONTAL)

        this.overScrollMode = View.OVER_SCROLL_ALWAYS

        setOverScrollUpdateListener(decor)
        setOverScrollStateListener(decor)
    }

    override fun scrollToPosition(position: Int) {
        ui { smoothScrollToPosition(position) }
    }

    companion object: XYBase() {
        fun Context.dpToPx(dp: Float): Int = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics).toInt()
        fun View.dpToPx(dp: Float): Int = context.dpToPx(dp)
    }
}