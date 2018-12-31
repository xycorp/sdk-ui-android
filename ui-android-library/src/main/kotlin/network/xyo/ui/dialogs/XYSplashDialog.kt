package network.xyo.ui.dialogs

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.RelativeLayout
import network.xyo.ui.R


class XYSplashDialog(context: Context) : Dialog(context, R.style.xy_full_screen_dialog) {

    public override fun onCreate(savedInstance: Bundle?) {
        super.onCreate(savedInstance)
        setContentView(R.layout.dialog_splash)
    }

    override fun onStart() {
        super.onStart()
        startAnimation()
    }

    private fun animate(v: View, animResource: Int) {
        val anim = AnimationUtils.loadAnimation(context, animResource)
        v.animation = anim

        anim.setAnimationListener(object : Animation.AnimationListener {

            var nextAnim: Animation? = null

            override fun onAnimationStart(animation: Animation) {

                nextAnim = AnimationUtils.loadAnimation(context, animResource)
            }

            override fun onAnimationEnd(animation: Animation) {
                nextAnim?.setAnimationListener(this)
                v.startAnimation(nextAnim)
            }

            override fun onAnimationRepeat(animation: Animation) {
                // TODO Auto-generated method stub

            }

        })
    }

    fun startAnimation() {
        val still_splash_layout = findViewById<RelativeLayout>(R.id.still_splash_layout)
        still_splash_layout.visibility = View.INVISIBLE

        val animation_splash_layout = findViewById<RelativeLayout>(R.id.animation_splash_layout)
        animation_splash_layout.visibility = View.VISIBLE

        val outerRing = findViewById<ImageView>(R.id.splash_ring2)
        outerRing.startAnimation(AnimationUtils.loadAnimation(context, R.anim.full_rotate_reverse_infinite))

        val middleRing = findViewById<ImageView>(R.id.splash_ring1)
        middleRing.startAnimation(AnimationUtils.loadAnimation(context, R.anim.full_rotate_infinite))

        val signalLarge = findViewById<ImageView>(R.id.splash_signal1)

        val signalLargeTwo = findViewById<ImageView>(R.id.splash_signal0)

        val signalLargeThree = findViewById<ImageView>(R.id.splash_signal2)

        val signalSmall = findViewById<ImageView>(R.id.splash_signal_small)

        animate(signalLarge, R.animator.scale_outer)
        animate(signalLargeTwo, R.animator.scale_outer2)
        animate(signalLargeThree, R.animator.scale_outer_reverse)
        animate(signalSmall, R.animator.scale_inner)
    }
}
