package com.shuange.lesson.utils

import android.app.Activity
import android.content.Context
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager


/**
 * 键盘控制
 * @author Jeffrey
 */
object KeyboardUitls {
    fun hideKeyboard(activity: Activity) {
        val imm =
            activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (activity.window
                .attributes.softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN
        ) {
            if (activity.currentFocus != null) imm.hideSoftInputFromWindow(
                activity.currentFocus!!.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
        }
    }

}
