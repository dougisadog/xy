package com.meten.xyh.modules.usersetting.view

import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import com.meten.xyh.BR
import com.meten.xyh.R
import com.meten.xyh.databinding.ActivityInterestBinding
import com.meten.xyh.modules.usersetting.adapter.InterestAdapter
import com.meten.xyh.modules.usersetting.viewmodel.InterestViewModel
import com.shuange.lesson.base.BaseActivity
import com.shuange.lesson.base.viewmodel.BaseShareModelFactory
import com.shuange.lesson.view.NonDoubleClickListener
import kotlinx.android.synthetic.main.layout_header.view.*


/**
 * 请选择您的兴趣爱好
 */
class InterestActivity : BaseActivity<ActivityInterestBinding, InterestViewModel>() {

    companion object {
        fun start(context: Context) {
            val i = Intent(context, InterestActivity::class.java)
            context.startActivity(i)
        }
    }

    override val viewModel: InterestViewModel by viewModels {
        BaseShareModelFactory()
    }

    private val interestAdapter: InterestAdapter by lazy {
        InterestAdapter(
            layout = R.layout.layout_interest_item,
            data = viewModel.interests
        )
    }

    override val layoutId: Int
        get() = R.layout.activity_interest
    override val viewModelId: Int
        get() = BR.interestViewModel


    override fun initView() {
        viewModel.loadData()
        binding.header.title.text = "请选择您的兴趣爱好"
        initInterests()
        initListener()
    }


    private fun initInterests() {
        with(binding.rv) {
            layoutManager = androidx.recyclerview.widget.GridLayoutManager(
                this@InterestActivity,
                3
            )
            interestAdapter.setOnItemClickListener { adapter, view, position ->
                val target = !interestAdapter.data[position].isSelected
                interestAdapter.data[position].isSelected = target
                interestAdapter.notifyDataSetChanged()

                binding.nextTv.isEnabled =
                    interestAdapter.data.any { it.isSelected }
            }
            adapter = interestAdapter
        }
    }

    private fun initListener() {
        binding.header.back.setOnClickListener {
            finish()
        }
        binding.nextTv.setOnClickListener(NonDoubleClickListener {
            viewModel.saveSetting()
        })

    }

    override fun initViewObserver() {
    }


}