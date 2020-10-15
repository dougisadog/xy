package com.shuange.lesson.modules.topquality.view

import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import com.shuange.lesson.BR
import com.shuange.lesson.R
import com.shuange.lesson.base.BaseActivity
import com.shuange.lesson.base.adapter.BaseFragmentAdapter
import com.shuange.lesson.base.viewmodel.BaseShareModelFactory
import com.shuange.lesson.databinding.ActivityTopQualityBinding
import com.shuange.lesson.modules.topquality.viewmodel.TopQualityViewModel
import com.shuange.lesson.utils.ToastUtil
import com.shuange.lesson.utils.extension.bind
import kotlinx.android.synthetic.main.layout_header.view.*

/**
 * 每日一句
 */
class TopQualityActivity : BaseActivity<ActivityTopQualityBinding, TopQualityViewModel>() {

    companion object {
        fun start(context: Context) {
            val i = Intent(context, TopQualityActivity::class.java)
            context.startActivity(i)
        }
    }

    override val viewModel: TopQualityViewModel by viewModels {
        BaseShareModelFactory()
    }

    override val layoutId: Int
        get() = R.layout.activity_top_quality
    override val viewModelId: Int
        get() = BR.topQualityViewModel

    lateinit var fragmentAdapter: BaseFragmentAdapter


    override fun initView() {
        binding.header.title.text = "每日一句"
        viewModel.loadData()
        initListener()
        viewModel.loadTypes {
            initViewPager()
            initTabIndicator()
        }
    }

    private fun initViewPager() {
        val fragments = arrayListOf<Fragment>()
        viewModel.pager.forEach {
            fragments.add(GalleryFragment.newInstance(it.first))
        }
        fragmentAdapter = BaseFragmentAdapter(this, fragments)
        with(binding.vp) {
            adapter = fragmentAdapter
        }
    }

    private fun initTabIndicator() {
//        binding.tabTl.init(binding.vp, viewModel.pager)
        binding.indicators.bind(binding.vp, viewModel.pager.map { it.second }.toMutableList())

    }

    private fun initListener() {
//        binding.searchEt.setOnSearchListener {
//            search(it.trim())
//        }
        binding.header.back.setOnClickListener {
            finish()
        }
    }

    fun search(text: String) {
        ToastUtil.show(text)
    }

    override fun initViewObserver() {
    }


}