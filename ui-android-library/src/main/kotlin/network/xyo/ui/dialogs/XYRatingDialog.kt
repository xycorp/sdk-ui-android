package network.xyo.ui.dialogs

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.text.format.DateUtils
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.Window
import android.view.animation.AnimationUtils
import android.widget.RatingBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatDialog
import kotlinx.android.synthetic.main.dialog_rating.*
import network.xyo.ui.R

/**
 * App Rating Dialog
 * inspired by: https://github.com/codemybrainsout/smart-app-rate
 */
class XYRatingDialog(context: Context, private var builder: Builder)
    : AppCompatDialog(context), RatingBar.OnRatingBarChangeListener, View.OnClickListener {

    private var preferences: SharedPreferences = context.getSharedPreferences(SHARED_PREFS, 0)
    private var userRating: Float = 0F

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setContentView(R.layout.dialog_rating)

        tv_dialog_rating_title.text = builder.title
        tv_dialog_rating_button_positive.text = builder.positiveButtonText
        tv_dialog_rating_button_negative.text = builder.negativeButtonText

        tv_dialog_rating_feedback_title.text = builder.feedbackTitle
        et_dialog_rating_feedback.hint = builder.feedbackHint
        tv_dialog_rating_button_feedback_submit.text = builder.feedbackSubmitText
        tv_dialog_rating_button_feedback_cancel.text = builder.feedbackCancelText

        builder.icon?.let { ico ->
            iv_dialog_rating?.setImageDrawable(ico)
        }

        rb_dialog_rating.onRatingBarChangeListener = this
        tv_dialog_rating_button_positive.setOnClickListener(this)
        tv_dialog_rating_button_negative.setOnClickListener(this)
        tv_dialog_rating_button_feedback_submit.setOnClickListener(this)
        tv_dialog_rating_button_feedback_cancel.setOnClickListener(this)
    }

    override fun show() {
        val editor = preferences.edit()

        if (preferences.getBoolean(PREVS_SHOW_NEVER, false)) {
            return
        }

        val launchCount = preferences.getLong(PREFS_LAUNCH_COUNT, 0) + 1
        editor.putLong(PREFS_LAUNCH_COUNT, launchCount)
        val firstLaunchDate = preferences.getLong(PREFS_LAUNCH_DATE, 0)

        val now = System.currentTimeMillis()
        if (firstLaunchDate == 0L) {
            //first app launch since installing
            editor.putLong(PREFS_LAUNCH_DATE, now)
        }

        editor.apply()

        //determine if app rating dialog is shown
        val targetLaunchDate = firstLaunchDate + builder.minDaysUntilPrompt * DateUtils.DAY_IN_MILLIS
        if (!isShowing && launchCount > builder.minLaunchesUntilPrompt && now > targetLaunchDate) {
            super.show()
        }
    }

    override fun onRatingChanged(ratingBar: RatingBar?, rating: Float, fromUser: Boolean) {
        ratingBar?.rating?.let { rtg ->
            userRating = rtg
            if (rtg > builder.minRatingShowStore) {
                dismiss()
                openPlayStore()
            } else {
                openForm()
            }
        }
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.tv_dialog_rating_button_positive -> {
                showLater()
            }
            R.id.tv_dialog_rating_button_negative -> {
                showNever()
            }
            R.id.tv_dialog_rating_button_feedback_submit -> {
                builder.formSubmitListener?.let { form ->
                    val feedback = et_dialog_rating_feedback?.text.toString().trim()
                    if (feedback.isNotEmpty()) {
                        form.onFormSubmitted(userRating, feedback)
                        showNever()
                    } else {
                        val shake = AnimationUtils.loadAnimation(context, R.anim.shake)
                        et_dialog_rating_feedback?.startAnimation(shake)
                        return
                    }

                }
                dismiss()
            }
            R.id.tv_dialog_rating_button_feedback_cancel -> {
                dismiss()
            }
        }
    }

    private fun openPlayStore() {
        val marketUri = Uri.parse(builder.playStoreUrl)
        try {
            context.startActivity(Intent(Intent.ACTION_VIEW, marketUri))
        } catch (ex: android.content.ActivityNotFoundException) {
            Toast.makeText(context, context.getString(R.string.rating_dialog_error_no_play_store), Toast.LENGTH_LONG).show()
        }
    }

    private fun openForm() {
        tv_dialog_rating_feedback_title?.visibility = VISIBLE
        et_dialog_rating_feedback?.visibility = VISIBLE
        ll_dialog_rating_feedback_buttons?.visibility = VISIBLE
        ll_dialog_rating_buttons?.visibility = GONE
        iv_dialog_rating?.visibility = GONE
        tv_dialog_rating_title?.visibility = GONE
        rb_dialog_rating?.visibility = GONE
    }

    private fun showLater() {
        //Reset launch counter
        val editor = preferences.edit()
        editor.putLong(PREFS_LAUNCH_COUNT, 0)
        editor.apply()

        dismiss()
    }

    private fun showNever() {
        val editor = preferences.edit()
        editor.putBoolean(PREVS_SHOW_NEVER, true)
        editor.apply()

        dismiss()
    }

    class Builder(private var context: Context) {

        var minLaunchesUntilPrompt: Long = 0
        var minDaysUntilPrompt: Long = 0
        var minRatingShowStore: Int = 4

        var title: String
        var positiveButtonText: String
        var negativeButtonText: String

        var feedbackTitle: String
        var feedbackHint: String
        var feedbackSubmitText: String
        var feedbackCancelText: String
        var playStoreUrl: String
        var icon: Drawable? = null

        var formSubmitListener: FormSubmitListener? = null

        interface FormSubmitListener {
            fun onFormSubmitted(userRating: Float, feedback: String)
        }

        init {
            icon = context.getDrawable(R.drawable.xy_load_logo)
            playStoreUrl = "http://play.google.com/store/apps/details?id=" + context.packageName
            title = context.getString(R.string.rating_dialog_title, context.applicationContext.applicationContext.packageName)
            positiveButtonText = context.getString(R.string.rating_dialog_maybe_later)
            negativeButtonText = context.getString(R.string.rating_dialog_never)

            feedbackTitle = context.getString(R.string.rating_dialog_experience)
            feedbackHint = context.getString(R.string.rating_dialog_suggestions)
            feedbackSubmitText = context.getString(R.string.rating_dialog_submit)
            feedbackCancelText = context.getString(R.string.rating_dialog_cancel)
        }

        fun minDaysUntilPrompt(minDays: Long): Builder {
            this.minDaysUntilPrompt = minDays
            return this
        }

        fun minLaunchesUntilPrompt(minLaunches: Long): Builder {
            this.minLaunchesUntilPrompt = minLaunches
            return this
        }

        fun icon(drawable: Drawable): Builder {
            this.icon = drawable
            return this
        }

        fun title(title: String): Builder {
            this.title = title
            return this
        }

        fun positiveButtonText(positiveButtonText: String): Builder {
            this.positiveButtonText = positiveButtonText
            return this
        }

        fun negativeButtonText(negativeButtonText: String): Builder {
            this.negativeButtonText = negativeButtonText
            return this
        }

        fun feedbackTitle(feedbackTitle: String): Builder {
            this.feedbackTitle = feedbackTitle
            return this
        }

        fun feedbackHint(feedbackHint: String): Builder {
            this.feedbackHint = feedbackHint
            return this
        }

        fun feedbackSubmitText(feedbackSubmitText: String): Builder {
            this.feedbackSubmitText = feedbackSubmitText
            return this
        }

        fun feedbackCancelText(feedbackCancelText: String): Builder {
            this.feedbackCancelText = feedbackCancelText
            return this
        }

        fun playStoreUrl(playStoreUrl: String): Builder {
            this.playStoreUrl = playStoreUrl
            return this
        }

        fun onFormSubmitted(formSubmitListener: FormSubmitListener): Builder {
            this.formSubmitListener = formSubmitListener
            return this
        }

        fun build(): XYRatingDialog {
            return XYRatingDialog(context, this)
        }

    }

    companion object {
        private const val SHARED_PREFS = "prefs_xy_apprate"
        private const val PREFS_LAUNCH_COUNT = "xy_launch_count"
        private const val PREFS_LAUNCH_DATE = "xy_launch_date"
        private const val PREVS_SHOW_NEVER = "xy_show_never"
    }

}