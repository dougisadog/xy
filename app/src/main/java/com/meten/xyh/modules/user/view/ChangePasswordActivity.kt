package com.meten.xyh.modules.user.view

import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import com.meten.xyh.BR
import com.meten.xyh.R
import com.meten.xyh.databinding.ActivityChangePasswordBinding
import com.meten.xyh.modules.user.viewmodel.ChangePwdViewModel
import com.shuange.lesson.base.BaseActivity
import com.shuange.lesson.base.viewmodel.BaseShareModelFactory
import com.shuange.lesson.view.NonDoubleClickListener
import kotlinx.android.synthetic.main.layout_header.view.*


/**
 * 修改密码
 */
class ChangePasswordActivity : BaseActivity<ActivityChangePasswordBinding, ChangePwdViewModel>() {

    companion object {
        fun start(context: Context) {
            val i = Intent(context, ChangePasswordActivity::class.java)
            context.startActivity(i)
        }
    }

    override val viewModel: ChangePwdViewModel by viewModels {
        BaseShareModelFactory()
    }

    override val layoutId: Int
        get() = R.layout.activity_change_password
    override val viewModelId: Int
        get() = BR.changePwdViewModel


    override fun initView() {
        binding.header.title.text = "修改密码"
        initListener()
    }


    private fun initListener() {
        binding.header.back.setOnClickListener {
            finish()
        }
        binding.forgetPwdTv.setOnClickListener(NonDoubleClickListener {
            //TODO
        })
    }


    override fun initViewObserver() {
    }

    override fun finish() {
        super.finish()
        viewModel.changePwd()
    }
}