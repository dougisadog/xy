package com.meten.xyh.modules.user.view

import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.meten.xyh.BR
import com.meten.xyh.R
import com.meten.xyh.databinding.ActivityChangePhoneBinding
import com.meten.xyh.modules.user.viewmodel.ChangePhoneViewModel
import com.meten.xyh.utils.BusinessUtil
import com.meten.xyh.utils.extension.setCustomEnable
import com.shuange.lesson.base.BaseActivity
import com.shuange.lesson.base.viewmodel.BaseShareModelFactory
import com.shuange.lesson.view.NonDoubleClickListener
import kotlinx.android.synthetic.main.layout_header.view.*


/**
 * 修改修改开课提醒电话
 */
class ChangePhoneActivity : BaseActivity<ActivityChangePhoneBinding, ChangePhoneViewModel>() {

    companion object {
        fun start(context: Context) {
            val i = Intent(context, ChangePhoneActivity::class.java)
            context.startActivity(i)
        }
    }

    override val viewModel: ChangePhoneViewModel by viewModels {
        BaseShareModelFactory()
    }

    override val layoutId: Int
        get() = R.layout.activity_change_phone
    override val viewModelId: Int
        get() = BR.changePhoneViewModel


    override fun initView() {
        binding.header.title.text = "修改开课提醒电话"
        initListener()
    }


    private fun initListener() {
        binding.header.back.setOnClickListener {
            finish()
        }
        binding.nextTv.setOnClickListener(NonDoubleClickListener {
            viewModel.changePhone()
        })
        binding.verifyCodeTv.setOnClickListener(NonDoubleClickListener {
            if (viewModel.checkPhone()) {
                viewModel.sendVerifyCode {
                    binding.verifyCodeTv.isEnabled = false
                }
            }
        })

    }

    override fun initViewObserver() {
        BusinessUtil.addVerifyListener(viewModel.remainTime, this, binding.verifyCodeTv)
        viewModel.phoneChanged.observe(this, Observer {
            finish()
        })
        viewModel.phone.observe(this, Observer {
            checkButton()
        })
        viewModel.verifyCode.observe(this, Observer {
            checkButton()
        })
    }

    private fun checkButton() {
        val isEnable =
            !viewModel.phone.value.isNullOrBlank() && !viewModel.verifyCode.value.isNullOrBlank()
        binding.nextTv.setCustomEnable(isEnable)
    }

}