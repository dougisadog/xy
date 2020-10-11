package com.meten.xyh.view.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.meten.xyh.R
import com.shuange.lesson.EmptyTask
import kotlinx.android.synthetic.main.layout_gift_dialog.*


class GiftDialog(context: Context, val cancelable: Boolean = true) :
    Dialog(context, R.style.CustomDialog) {

    var onClick: ((String) -> Unit)? = null

    var onCancel: EmptyTask = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setCancelable(cancelable)
        setContentView(R.layout.layout_gift_dialog);
        setCanceledOnTouchOutside(true)
        //初始化界面控件
        initView();
        //初始化界面数据
        refreshView();
        //初始化界面控件的事件
        initEvent();
    }

    private fun initEvent() {
    }

    private fun refreshView() {
    }

    private fun initView() {
        confirmTv.setOnClickListener {
            onClick?.invoke(giftCodeEt.text.toString())
            dismiss()
        }

    }


}