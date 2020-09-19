package com.meten.xyh.modules.user.view

import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import com.meten.xyh.BR
import com.meten.xyh.R
import com.meten.xyh.databinding.ActivityChangeUserBinding
import com.meten.xyh.databinding.ActivityCreateUserBinding
import com.meten.xyh.modules.user.adapter.UserAdapter
import com.meten.xyh.modules.user.viewmodel.ChangeUserViewModel
import com.shuange.lesson.base.BaseActivity
import com.shuange.lesson.base.viewmodel.BaseShareModelFactory
import com.shuange.lesson.utils.ToastUtil
import com.shuange.lesson.view.NonDoubleClickListener


/**
 * 切换用户
 */
class ChangeUserActivity : BaseActivity<ActivityChangeUserBinding, ChangeUserViewModel>() {

    companion object {
        fun start(context: Context) {
            val i = Intent(context, ChangeUserActivity::class.java)
            context.startActivity(i)
        }
    }

    override val viewModel: ChangeUserViewModel by viewModels {
        BaseShareModelFactory()
    }

    private val userAdapter: UserAdapter by lazy {
        UserAdapter(
            data = viewModel.users
        )
    }

    override val layoutId: Int
        get() = R.layout.activity_change_user
    override val viewModelId: Int
        get() = BR.changeUserViewModel


    override fun initView() {
        initUsers()
        initListener()
    }

    fun initUsers() {
        with(binding.userRv) {
            layoutManager = androidx.recyclerview.widget.LinearLayoutManager(
                this@ChangeUserActivity,
                androidx.recyclerview.widget.RecyclerView.VERTICAL,
                false
            )
            userAdapter.setOnItemClickListener { adapter, view, position ->
                userAdapter.data.forEachIndexed { index, user ->
                    user.current = index == position
                }
                viewModel.saveUser()
                userAdapter.notifyDataSetChanged()
            }
            isNestedScrollingEnabled = false
            adapter = userAdapter
        }
    }

    private fun initListener() {
        binding.closeTv.setOnClickListener(NonDoubleClickListener {
            finish()
        })
    }


    override fun initViewObserver() {
    }


}