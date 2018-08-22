package network.xyo.ui.views

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import network.xyo.core.XYBase
import network.xyo.ui.helpers.overscroll.*
import network.xyo.ui.ui

open class XYSmallRibbon(context: Context, attrs: AttributeSet?, defStyle: Int) : XYRibbon(context, attrs, defStyle) {

    private var _reloadTriggered = false
    private var _pendingAnimation = false

    private var _bounceTrigger = 50

    open class Listener : XYPanel.Listener () {
        open fun pull() {

        }
    }

    constructor(context: Context) : this(context, null, 0)

    constructor(context: Context, attrs: AttributeSet) : this(context, attrs, 0)

    init {
        XYBase.logInfo(TAG, "init")
        setAdapter(adapter)
        layoutManager = object : LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false) {
            override fun supportsPredictiveItemAnimations(): Boolean {
                return true
            }
        }

        initRecyclerView()

        _bounceTrigger = dpToPx(50)

    }

    fun dpToPx(dp: Int): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), resources.displayMetrics).toInt()
    }

    open fun initRecyclerView() {
        XYBase.logInfo(TAG, "initRecyclerView")

        this.itemAnimator = null

        val decor = OverScrollDecoratorHelper.setUpStaticOverScroll(this, OverScrollDecoratorHelper.ORIENTATION_HORIZONTAL)

        this.overScrollMode = View.OVER_SCROLL_ALWAYS

        decor.setOverScrollUpdateListener(object : IOverScrollUpdateListener {
            override fun onOverScrollUpdate(decor: IOverScrollDecor, state: Int, offset: Float) {
                if (offset > _bounceTrigger) {
                    _reloadTriggered = true
                }
            }
        })

        decor.setOverScrollStateListener(object : IOverScrollStateListener {
            override fun onOverScrollStateChange(decor: IOverScrollDecor, oldState: Int, newState: Int) {
                when (newState) {
                    IOverScrollState.STATE_IDLE -> {
                        XYBase.logInfo(TAG, "onOverScrollStateChange: STATE_IDLE")
                        if (_reloadTriggered) {
                            XYBase.logInfo(TAG, "onOverScrollStateChange: _reloadTriggered")

                            (listener as Listener).pull()

                            _reloadTriggered = false
                            _pendingAnimation = true
                        }
                    }
                    IOverScrollState.STATE_DRAG_START_SIDE -> XYBase.logInfo(TAG, "onOverScrollUpdate: STATE_DRAG_START_SIDE")
                    IOverScrollState.STATE_DRAG_END_SIDE -> XYBase.logInfo(TAG, "onOverScrollUpdate: STATE_DRAG_END_SIDE")
                    IOverScrollState.STATE_BOUNCE_BACK -> XYBase.logInfo(TAG, "onOverScrollUpdate: STATE_BOUNCE_BACK")
                    else -> {
                    }
                }// Dragging started at the left-end.
                // Dragging started at the right-end.
            }
        })
    }

    override fun scrollToPosition(position: Int) {
        ui { smoothScrollToPosition(position) }
    }

    companion object {

        private val TAG = "XYSmallRibbon"

    }
}