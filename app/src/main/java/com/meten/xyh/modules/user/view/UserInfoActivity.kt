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
import com.meten.xyh.enumeration.UserSettingType
import com.meten.xyh.modules.user.viewmodel.UserInfoViewModel
import com.meten.xyh.modules.usersetting.view.BaseUserSettingActivity
import com.shuange.lesson.base.BaseActivity
import com.shuange.lesson.base.viewmodel.BaseShareModelFactory
import com.shuange.lesson.utils.ToastUtil
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
            action = { ToastUtil.show("昵称") }
        })
        actions.add(ActionItem().apply {
            title = "个性签名"
            value = user?.introduction ?: ""
            action = { ToastUtil.show("个性签名") }
        })
        actions.add(ActionItem().apply {
            title = "学习阶段"
            value = user?.subUser?.stage ?: ""
            action = { BaseUserSettingActivity.start(this@UserInfoActivity, UserSettingType.STAGE) }
        })
        actions.add(ActionItem().apply {
            title = user?.subUser?.interest ?: ""
            action =
                { BaseUserSettingActivity.start(this@UserInfoActivity, UserSettingType.INTEREST) }
        })
        actions.add(ActionItem().apply {
            title = "需提升的"
            action =
                { BaseUserSettingActivity.start(this@UserInfoActivity, UserSettingType.OBJECTIVE) }
        })
        actions.add(ActionItem().apply {
            title = "上课提醒手机"
            value = user?.subUser?.phone ?: ""
            action = { ToastUtil.show("上课提醒手机") }
        })
        actions.add(ActionItem().apply {
            title = "登录密码"
            value = "修改"
            action = { ToastUtil.show("登录密码") }
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