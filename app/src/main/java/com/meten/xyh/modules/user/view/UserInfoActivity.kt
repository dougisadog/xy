package com.meten.xyh.modules.user.view

import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import com.meten.xyh.BR
import com.meten.xyh.R
import com.meten.xyh.base.adapter.ActionAdapter
import com.meten.xyh.base.bean.ActionItem
import com.meten.xyh.databinding.ActivityUserInfoBinding
import com.meten.xyh.modules.user.viewmodel.UserInfoViewModel
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

    private fun initListener() {
        binding.header.back.setOnClickListener {
            finish()
        }
    }


    override fun initViewObserver() {
    }


}