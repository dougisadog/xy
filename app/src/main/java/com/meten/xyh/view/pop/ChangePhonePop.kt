package com.meten.xyh.view.pop

import android.app.Activity
import android.view.*
import android.widget.PopupWindow
import com.meten.xyh.R
import com.shuange.lesson.view.NonDoubleClickListener


class ChangePhonePop(val context: Activity) : PopupWindow(
    null,
    ViewGroup.LayoutParams.MATCH_PARENT,
    ViewGroup.LayoutParams.WRAP_CONTENT
) {

    var phonePopCallBack: PhonePopCallBack? = null

    init {
        animationStyle = R.style.popupAnimation
        context.window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        setBgAlpha(0.4f)
        contentView = LayoutInflater.from(context).inflate(R.layout.layout_pop_change_phone, null)
        contentView.findViewById<View>(R.id.changeTv).setOnClickListener(NonDoubleClickListener {
            phonePopCallBack?.onChange()
            dismiss()
        })
        contentView.findViewById<View>(R.id.deleteTv).setOnClickListener(NonDoubleClickListener {
            phonePopCallBack?.onDelete()
            dismiss()
        })
        contentView.findViewById<View>(R.id.cancelTv).setOnClickListener(NonDoubleClickListener {
            dismiss()
        })
        setOnDismissListener {
            setBgAlpha(1f)
        }
    }

    private fun setBgAlpha(alpha: Float) {
        val lp: WindowManager.LayoutParams = context.window.attributes
        lp.alpha = alpha
        context.window.attributes = lp
    }

    fun show(anchor: View) {
        if (!isShowing) {
            showAtLocation(anchor, Gravity.BOTTOM, 0, 0)
        }
    }

    interface PhonePopCallBack {
        fun onChange()
        fun onDelete()
    }
}