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
        binding.saveTv.setOnClickListener(NonDoubleClickListener {
//            viewModel.save()
        })
    }


    override fun initViewObserver() {
    }


}