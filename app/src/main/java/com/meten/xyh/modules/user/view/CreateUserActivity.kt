package com.meten.xyh.modules.user.view

import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import com.meten.xyh.BR
import com.meten.xyh.R
import com.meten.xyh.base.DataCache
import com.meten.xyh.databinding.ActivityCreateUserBinding
import com.meten.xyh.enumeration.UserSettingType
import com.meten.xyh.modules.user.viewmodel.CreateUserViewModel
import com.meten.xyh.modules.usersetting.view.BaseUserSettingActivity
import com.meten.xyh.modules.usersetting.view.InterestActivity
import com.meten.xyh.modules.usersetting.view.SignatureActivity
import com.meten.xyh.service.response.bean.SubUser
import com.shuange.lesson.base.BaseActivity
import com.shuange.lesson.base.viewmodel.BaseShareModelFactory
import com.shuange.lesson.view.NonDoubleClickListener
import kotlinx.android.synthetic.main.layout_header.view.*


/**
 * 新增用户
 */
class CreateUserActivity : BaseActivity<ActivityCreateUserBinding, CreateUserViewModel>() {

    companion object {
        fun start(context: Context) {
            val i = Intent(context, CreateUserActivity::class.java)
            context.startActivity(i)
        }
    }

    override val viewModel: CreateUserViewModel by viewModels {
        BaseShareModelFactory()
    }

    override val layoutId: Int
        get() = R.layout.activity_create_user
    override val viewModelId: Int
        get() = BR.createUserViewModel


    override fun initView() {
        DataCache.newSubUser = SubUser()
        binding.header.title.text = "新增用户"
        viewModel.loadData()
        initListener()
    }


    private fun initListener() {
        binding.header.back.setOnClickListener {
            finish()
        }
        binding.saveTv.setOnClickListener(NonDoubleClickListener {
            viewModel.save {
                finish()
            }
        })
        binding.titleCl.root.setOnClickListener(NonDoubleClickListener {
            SignatureActivity.start(this@CreateUserActivity, UserSettingType.NICKNAME, true)
        })
        binding.signatureCl.root.setOnClickListener(NonDoubleClickListener {
            SignatureActivity.start(
                this@CreateUserActivity,
                UserSettingType.SIGNATURE,
                true
            )
        })
        binding.stageCl.root.setOnClickListener(NonDoubleClickListener {
            BaseUserSettingActivity.start(
                this@CreateUserActivity,
                UserSettingType.STAGE,
                isSetting = true,
                isCreate = true
            )
        })
        binding.interestCl.root.setOnClickListener(NonDoubleClickListener {
            InterestActivity.start(this@CreateUserActivity, true)
        })
        binding.objectiveCl.root.setOnClickListener(NonDoubleClickListener {
            BaseUserSettingActivity.start(
                this@CreateUserActivity,
                UserSettingType.OBJECTIVE,
                isSetting = true,
                isCreate = true

            )
        })
    }

    override fun initViewObserver() {
    }


    override fun onDestroy() {
        super.onDestroy()
        DataCache.newSubUser = null
    }

    override fun onResume() {
        super.onResume()
        viewModel.user.value = DataCache.newSubUser
    }
}