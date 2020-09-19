package com.meten.xyh.modules.user.view

import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import com.meten.xyh.BR
import com.meten.xyh.R
import com.meten.xyh.base.adapter.ActionAdapter
import com.meten.xyh.base.bean.ActionItem
import com.meten.xyh.databinding.ActivityCreateUserBinding
import com.meten.xyh.modules.user.viewmodel.CreateUserViewModel
import com.shuange.lesson.base.BaseActivity
import com.shuange.lesson.base.viewmodel.BaseShareModelFactory
import com.shuange.lesson.utils.ToastUtil
import com.shuange.lesson.view.NonDoubleClickListener
import kotlinx.android.synthetic.main.layout_header.view.*


/**
 * 用户信息
 */
class UserInfoActivity : BaseActivity<ActivityCreateUserBinding, CreateUserViewModel>() {

    companion object {
        fun startUserInfo(context: Context) {
            val i = Intent(context, UserInfoActivity::class.java)
            context.startActivity(i)
        }
    }

    override val viewModel: CreateUserViewModel by viewModels {
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
        initActions()
        initListener()
    }

    fun initActions() {
        val actions = arrayListOf<ActionItem>()
        actions.add(ActionItem().apply {
            title = "昵称"
            value = "你的名字是什么"
            action = { ToastUtil.show("昵称") }
        })
        actions.add(ActionItem().apply {
            title = "个性签名"
            value = "向阳而生"
            action = { ToastUtil.show("个性签名") }
        })
        actions.add(ActionItem().apply {
            title = "学习阶段"
            value = "幼儿"
            action = { ToastUtil.show("学习阶段") }
        })
        actions.add(ActionItem().apply {
            title = "感兴趣的"
            action = { ToastUtil.show("感兴趣的") }
        })
        actions.add(ActionItem().apply {
            title = "需提升的"
            action = { ToastUtil.show("需提升的") }
        })
        actions.add(ActionItem().apply {
            title = "上课提醒手机"
            value = "18701515190"
            action = { ToastUtil.show("上课提醒手机") }
        })
        actions.add(ActionItem().apply {
            title = "登录密码"
            value = "修改"
            action = { ToastUtil.show("登录密码") }
        })
        viewModel.actionItems.addAll(actions)

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

    private fun initListener() {
        binding.header.back.setOnClickListener {
            finish()
        }
    }


    override fun initViewObserver() {
    }


}