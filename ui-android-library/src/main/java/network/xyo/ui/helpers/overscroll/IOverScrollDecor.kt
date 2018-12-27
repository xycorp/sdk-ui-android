package network.xyo.ui.helpers.overscroll

import android.view.View

/**
 * @author amit
 */
interface IOverScrollDecor {
    val view: View

    /**
     * Get the current decorator's runtime state, i.e. one of the values specified by [IOverScrollState].
     * @return The state.
     */
    val currentState: Int

    fun setOverScrollStateListener(listener: IOverScrollStateListener?)
    fun setOverScrollUpdateListener(listener: IOverScrollUpdateListener?)

    /**
     * Detach the decorator from its associated view, thus disabling it entirely.
     *
     *
     * It is best to call this only when over-scroll isn't currently in-effect - i.e. verify that
     * `getCurrentState()==IOverScrollState.STATE_IDLE` as a precondition, or otherwise
     * use a state listener previously installed using
     * [.setOverScrollStateListener].
     *
     *
     * Note: Upon detachment completion, the view in question will return to the default
     * Android over-scroll configuration (i.e. [View.OVER_SCROLL_ALWAYS] mode). This can be
     * overridden by calling `View.setOverScrollMode(mode)` immediately thereafter.
     */
    fun detach()
}
