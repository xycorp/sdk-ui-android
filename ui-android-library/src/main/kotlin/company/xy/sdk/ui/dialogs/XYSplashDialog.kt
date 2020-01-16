package company.xy.sdk.ui.dialogs

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatDialog
import kotlinx.android.synthetic.main.dialog_splash.*
import company.xy.sdk.ui.R


open class XYSplashDialog(context: Context) : AppCompatDialog(context, R.style.xy_full_screen_dialog) {

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

            }

        })
    }

    open fun startAnimation() {
        still_splash_layout.visibility = View.INVISIBLE
        animation_splash_layout.visibility = View.VISIBLE

        splash_ring2.startAnimation(AnimationUtils.loadAnimation(context, R.anim.full_rotate_reverse_infinite))
        splash_ring1.startAnimation(AnimationUtils.loadAnimation(context, R.anim.full_rotate_infinite))

        animate(splash_signal1, R.animator.scale_outer)
        animate(splash_signal0, R.animator.scale_outer2)
        animate(splash_signal2, R.animator.scale_outer_reverse)
        animate(splash_signal_small, R.animator.scale_inner)
    }
}
