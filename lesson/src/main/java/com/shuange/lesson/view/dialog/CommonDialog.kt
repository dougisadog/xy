package com.shuange.lesson.view.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.shuange.lesson.EmptyTask
import com.shuange.lesson.R
import kotlinx.android.synthetic.main.common_dialog_layout.*


class CommonDialog(context: Context) : Dialog(context, R.style.CustomDialog) {

    var titleText: String? = null

    var contentText: String? = null

    var buttonText: String? = null

    var onClick: EmptyTask = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.common_dialog_layout);
        //按空白处不能取消动画
        setCanceledOnTouchOutside(false);
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
        titleText?.let {
            title.text = it
        }
        contentText?.let {
            content.text = it
        }
        buttonText?.let {
            confirmTv.text = it
        }
        confirmTv.setOnClickListener {
            onClick?.invoke()
            dismiss()
        }

    }


}