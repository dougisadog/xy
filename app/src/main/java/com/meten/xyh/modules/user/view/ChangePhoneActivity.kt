package com.meten.xyh.modules.user.view

import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import com.meten.xyh.BR
import com.meten.xyh.R
import com.meten.xyh.databinding.ActivityChangePhoneBinding
import com.meten.xyh.modules.user.viewmodel.ChangePhoneViewModel
import com.meten.xyh.utils.BusinessUtil
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
            viewModel.sendVerifyMessage()
        })

    }

    override fun initViewObserver() {
        BusinessUtil.addVerifyListener(viewModel.remainTime, this, binding.verifyCodeTv)
    }

}