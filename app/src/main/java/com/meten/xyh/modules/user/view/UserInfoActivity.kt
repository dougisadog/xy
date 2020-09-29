package com.meten.xyh.modules.user.view

import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import com.meten.xyh.BR
import com.meten.xyh.R
import com.meten.xyh.base.DataCache
import com.meten.xyh.base.adapter.ActionAdapter
import com.meten.xyh.base.bean.ActionItem
import com.meten.xyh.databinding.ActivityUserInfoBinding
import com.meten.xyh.enumeration.SignatureType
import com.meten.xyh.enumeration.UserSettingType
import com.meten.xyh.modules.user.viewmodel.UserInfoViewModel
import com.meten.xyh.modules.usersetting.view.BaseUserSettingActivity
import com.meten.xyh.modules.usersetting.view.InterestActivity
import com.meten.xyh.modules.usersetting.view.SignatureActivity
import com.shuange.lesson.base.BaseActivity
import com.shuange.lesson.base.viewmodel.BaseShareModelFactory
import kotlinx.android.synthetic.main.layout_header.view.*


/**
 * 用户信息
 */
class UserInfoActivity : BaseActivity<ActivityUserInfoBinding, UserInfoViewModel>() {

    companion object {
        fun start(context: Context) {
            val i = Intent(context, UserInfoActivity::class.java)
            context.startActivity(i)
        }
    }

    override val viewModel: UserInfoViewModel by viewModels {
        BaseShareModelFactory()
    }

    private val actionAdapter: ActionAdapter by lazy {
        ActionAdapter(
            data = viewModel.actionItems
        )
    }

    override val layoutId: Int
        get() = R.layout.activity_user_info
    override val viewModelId: Int
        get() = BR.userInfoViewModel


    override fun initView() {
        binding.header.title.text = "用户信息"
        viewModel.loadData()
        initActions()
        initListener()
    }

    fun initActions() {
        with(binding.actionRv) {
            layoutManager = androidx.recyclerview.widget.LinearLayoutManager(
                this@UserInfoActivity,
                androidx.recyclerview.widget.RecyclerView.VERTICAL,
                false
            )
            actionAdapter.setOnItemClickListener { adapter, view, position ->
                actionAdapter.data[position].action?.invoke()
            }
            isNestedScrollingEnabled = false
            adapter = actionAdapter
        }
    }

    fun refreshActionsData() {
        val user = DataCache.currentUser()
        val actions = arrayListOf<ActionItem>()
        actions.add(ActionItem().apply {
            title = "昵称"
            value = user?.userName ?: ""
            action = { SignatureActivity.start(this@UserInfoActivity, SignatureType.NICKNAME) }
        })
        actions.add(ActionItem().apply {
            title = "个性签名"
            value = user?.introduction ?: ""
            action = { SignatureActivity.start(this@UserInfoActivity, SignatureType.SIGNATURE) }
        })
        actions.add(ActionItem().apply {
            title = "学习阶段"
            value = user?.subUser?.stage ?: ""
            action = { BaseUserSettingActivity.start(this@UserInfoActivity, UserSettingType.STAGE) }
        })
        actions.add(ActionItem().apply {
            title = "感兴趣的"
            value = user?.subUser?.interest ?: ""
            action =
                { InterestActivity.start(this@UserInfoActivity) }
        })
        actions.add(ActionItem().apply {
            title = "需提升的"
            action =
                { BaseUserSettingActivity.start(this@UserInfoActivity, UserSettingType.OBJECTIVE) }
        })
        actions.add(ActionItem().apply {
            title = "上课提醒手机"
            value = user?.subUser?.phone ?: ""
            action = { ChangePhoneActivity.start(this@UserInfoActivity) }
        })
        actions.add(ActionItem().apply {
            title = "登录密码"
            value = "修改"
            action = { ChangePasswordActivity.start(this@UserInfoActivity, false) }
        })
        viewModel.actionItems.clear()
        viewModel.actionItems.addAll(actions)
    }

    private fun initListener() {
        binding.header.back.setOnClickListener {
            finish()
        }
    }


    override fun initViewObserver() {
    }

    override fun onResume() {
        super.onResume()
        refreshActionsData()
    }
}