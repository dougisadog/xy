package com.shuange.lesson.view.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import com.shuange.lesson.EmptyTask
import com.shuange.lesson.R
import kotlinx.android.synthetic.main.layout_common_dialog.*


class CommonDialog(context: Context, val cancelable: Boolean = true) : Dialog(context, R.style.CustomDialog) {

    var titleText: String? = null

    var contentText: String? = null

    var confirmButtonText: String? = null

    var cancelButtonText: String? = null


    var onClick: EmptyTask = null

    var onCancel: EmptyTask = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setCancelable(cancelable)
        setContentView(R.layout.layout_common_dialog);
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
        confirmButtonText?.let {
            confirmTv.text = it
        }

        confirmTv.setOnClickListener {
            onClick?.invoke()
            dismiss()
        }
        cancelButtonText?.let {
            cancelTv.text = it
            cancelTv.visibility = View.VISIBLE
            onCancel?.let {
                cancelTv.setOnClickListener {
                    onCancel?.invoke()
                    dismiss()
                }
            }
        }

    }


}