package com.meten.xyh.modules.login.view

import android.content.Context
import android.content.Intent
import androidx.lifecycle.Observer
import com.meten.xyh.BR
import com.meten.xyh.R
import com.meten.xyh.databinding.ActivitySingleInputBinding
import com.meten.xyh.modules.login.viewmodel.SingleInputViewModel
import com.meten.xyh.utils.extension.setCustomEnable
import com.shuange.lesson.base.BaseActivity
import com.shuange.lesson.view.NonDoubleClickListener
import kotlinx.android.synthetic.main.layout_header.view.*


/**
 * 通用单行输入
 */
abstract class SingleInputActivity<VM : SingleInputViewModel> :
    BaseActivity<ActivitySingleInputBinding, VM>() {

    companion object {
        fun start(context: Context) {
            val i = Intent(context, SingleInputActivity::class.java)
            context.startActivity(i)
        }
    }


    override val layoutId: Int
        get() = R.layout.activity_single_input

    override val viewModelId: Int
        get() = BR.singleInputViewModel

    override fun initView() {
        viewModel.initValue()
        binding.header.title.text = ""
        initListener()
    }

    private fun initListener() {
        binding.header.back.setOnClickListener(NonDoubleClickListener {
            finish()
        })
        binding.nextTv.setOnClickListener(NonDoubleClickListener {
            viewModel.confirm(viewModel.input.value ?: "")
        })
    }

    override fun initViewObserver() {
        viewModel.input.observe(this, Observer {
            binding.nextTv.setCustomEnable(!it.isNullOrBlank())
        })
    }


}