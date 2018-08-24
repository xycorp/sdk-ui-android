package network.xyo.ui.helpers.overscroll

/**
 * @author amit
 */
interface IOverScrollState {
    companion object {

        /** No over-scroll is in-effect.  */
        val STATE_IDLE = 0

        /** User is actively touch-dragging, thus enabling over-scroll at the view's *start* side.  */
        val STATE_DRAG_START_SIDE = 1

        /** User is actively touch-dragging, thus enabling over-scroll at the view's *end* side.  */
        val STATE_DRAG_END_SIDE = 2

        /** User has released their touch, thus throwing the view back into place via bounce-back animation.  */
        val STATE_BOUNCE_BACK = 3
    }
}
