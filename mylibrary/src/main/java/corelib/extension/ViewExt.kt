package corelib.extension

import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation

/**
 *
 * Created by yamamoto on 2018/02/05.
 */

var View.intTag: Int
    get() = tag as? Int ?: 0
    set(value) {
        tag = value
    }

var View.isHidden: Boolean
    get() = visibility == View.INVISIBLE || visibility == View.GONE
    set(value) {
        visibility = if (value) View.INVISIBLE else View.VISIBLE
    }

var View.isGone: Boolean
    get() = visibility == View.GONE
    set(value) {
        visibility = if (value) View.GONE else View.VISIBLE
    }


fun View.animate(withDuration: Long, toAlpha: Float) {
    var alphaAnimate = AlphaAnimation(this.alpha, toAlpha)
    alphaAnimate.duration = withDuration
    alphaAnimate.fillAfter = true
    startAnimation(alphaAnimate)
}

fun View.removeFromSuperview() {
    (parent as? ViewGroup)?.removeView(this)
}
