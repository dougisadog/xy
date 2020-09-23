package com.meten.xyh.modules.teacher.view

import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import com.meten.xyh.BR
import com.meten.xyh.R
import com.meten.xyh.base.config.IntentKey
import com.meten.xyh.databinding.ActivityTeacherInfoBinding
import com.meten.xyh.modules.discovery.adapter.TopQualityAdapter
import com.meten.xyh.modules.teacher.viewmodel.TeacherInfoViewModel
import com.shuange.lesson.base.BaseActivity
import com.shuange.lesson.base.viewmodel.BaseShareModelFactory
import com.shuange.lesson.utils.ToastUtil


/**
 * 名师简介
 */
class TeacherInfoActivity : BaseActivity<ActivityTeacherInfoBinding, TeacherInfoViewModel>() {

    companion object {
        fun start(context: Context, id: String) {
            val i = Intent(context, TeacherInfoActivity::class.java)
            i.putExtra(IntentKey.TEACHER_KEY, id)
            context.startActivity(i)
        }
    }

    override val viewModel: TeacherInfoViewModel by viewModels {
        BaseShareModelFactory()
    }

    private val topQualityAdapter: TopQualityAdapter by lazy {
        TopQualityAdapter(
            layout = R.layout.layout_top_quality_item,
            data = viewModel.courses
        )
    }

    override val layoutId: Int
        get() = R.layout.activity_teacher_info
    override val viewModelId: Int
        get() = BR.teacherInfoViewModel

    override fun initParams() {
        super.initParams()
        viewModel.teacherId = intent.getStringExtra(IntentKey.TEACHER_KEY)
    }

    override fun initView() {
        viewModel.loadData()
        initTopQuality()
        initListener()
    }

    fun initTopQuality() {
        with(binding.rv) {
            layoutManager = androidx.recyclerview.widget.LinearLayoutManager(
                this@TeacherInfoActivity,
                androidx.recyclerview.widget.RecyclerView.VERTICAL,
                false
            )
            topQualityAdapter.setOnItemClickListener { adapter, view, position ->
                ToastUtil.show("item click  topQuality:${topQualityAdapter.data[position].title}")
            }
            isNestedScrollingEnabled = false
            adapter = topQualityAdapter
        }
    }

    private fun initListener() {
        binding.back.setOnClickListener {
            finish()
        }
    }

    fun search(text: String) {
        ToastUtil.show(text)
    }

    override fun initViewObserver() {
    }


}