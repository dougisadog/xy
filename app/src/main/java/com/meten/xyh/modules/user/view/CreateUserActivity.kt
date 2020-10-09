package com.meten.xyh.modules.user.view

import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import com.meten.xyh.BR
import com.meten.xyh.R
import com.meten.xyh.base.DataCache
import com.meten.xyh.base.adapter.ActionAdapter
import com.meten.xyh.base.bean.ActionItem
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
        fun star(context: Context) {
            val i = Intent(context, CreateUserActivity::class.java)
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
        get() = R.layout.activity_create_user
    override val viewModelId: Int
        get() = BR.createUserViewModel


    override fun initView() {
        binding.header.title.text = "新增用户"
        viewModel.loadData()
        initActions()
        initListener()
    }

    fun initActions() {
        with(binding.actionRv) {
            layoutManager = androidx.recyclerview.widget.LinearLayoutManager(
                this@CreateUserActivity,
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
        binding.saveTv.setOnClickListener(NonDoubleClickListener {
            viewModel.save()
        })
    }

    fun buildActionsBySubUser(subUser: SubUser?): ArrayList<ActionItem> {
        val actions = arrayListOf<ActionItem>()
        actions.add(ActionItem().apply {
            title = "昵称"
            value = subUser?.name ?: ""
            action = { SignatureActivity.start(this@CreateUserActivity, UserSettingType.NICKNAME) }
        })
        actions.add(ActionItem().apply {
            title = "个性签名"
            value = ""
            action = { SignatureActivity.start(this@CreateUserActivity, UserSettingType.SIGNATURE) }
        })
        actions.add(ActionItem().apply {
            title = "学习阶段"
            value = subUser?.stage ?: ""
            action =
                { BaseUserSettingActivity.start(this@CreateUserActivity, UserSettingType.STAGE) }
        })
        actions.add(ActionItem().apply {
            title = "感兴趣的"
            value = subUser?.interest ?: ""
            action =
                { InterestActivity.start(this@CreateUserActivity) }
        })
        actions.add(ActionItem().apply {
            title = "需提升的"
            value = subUser?.objective ?: ""
            action =
                {
                    BaseUserSettingActivity.start(
                        this@CreateUserActivity,
                        UserSettingType.OBJECTIVE
                    )
                }
        })
        return actions
    }


    override fun initViewObserver() {
    }


    override fun onDestroy() {
        super.onDestroy()
        DataCache.newSubUser = null
    }

    override fun onResume() {
        super.onResume()
        val actions = buildActionsBySubUser(DataCache.newSubUser)
        viewModel.actionItems.clear()
        viewModel.actionItems.addAll(actions)
    }
}