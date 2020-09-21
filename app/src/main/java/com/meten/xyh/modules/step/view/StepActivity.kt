package com.meten.xyh.modules.step.view

import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import com.meten.xyh.BR
import com.meten.xyh.R
import com.meten.xyh.databinding.ActivityStepBinding
import com.meten.xyh.modules.main.view.MainActivity
import com.meten.xyh.modules.step.adapter.StepAdapter
import com.meten.xyh.modules.step.viewmodel.StepViewModel
import com.shuange.lesson.base.BaseActivity
import com.shuange.lesson.base.viewmodel.BaseShareModelFactory
import com.shuange.lesson.utils.ToastUtil
import com.shuange.lesson.view.NonDoubleClickListener


/**
 * 登录
 */
class StepActivity : BaseActivity<ActivityStepBinding, StepViewModel>() {

    companion object {
        fun start(context: Context) {
            val i = Intent(context, StepActivity::class.java)
            context.startActivity(i)
        }
    }

    override val viewModel: StepViewModel by viewModels {
        BaseShareModelFactory()
    }

    private val stepAdapter: StepAdapter by lazy {
        StepAdapter(
            layout = R.layout.layout_step_item,
            data = viewModel.steps
        )
    }


    override val layoutId: Int
        get() = R.layout.activity_step
    override val viewModelId: Int
        get() = BR.stepViewModel


    override fun initView() {
        initSteps()
        initListener()
    }

    fun initSteps() {
        with(binding.stepRv) {
            layoutManager = androidx.recyclerview.widget.LinearLayoutManager(
                this@StepActivity,
                androidx.recyclerview.widget.RecyclerView.VERTICAL,
                false
            )
            stepAdapter.setOnItemClickListener { adapter, view, position ->
                stepAdapter.data.forEachIndexed { index, stepBean ->
                    stepBean.isSelected = position == index
                }
                stepAdapter.notifyDataSetChanged()
            }
            isNestedScrollingEnabled = false
            adapter = stepAdapter
        }
    }

    private fun initListener() {
        binding.nextTv.setOnClickListener(NonDoubleClickListener {
            MainActivity.start(this)
        })
    }


    override fun initViewObserver() {
    }


}