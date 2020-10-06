package com.meten.xyh.modules.search.view

import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import com.meten.xyh.BR
import com.meten.xyh.R
import com.meten.xyh.base.config.IntentKey
import com.meten.xyh.databinding.ActivityBaseSearchBinding
import com.meten.xyh.modules.search.viewmodel.BaseSearchViewModel
import com.shuange.lesson.base.BaseActivity
import com.shuange.lesson.base.adapter.BaseFragmentAdapter
import com.shuange.lesson.base.viewmodel.BaseShareModelFactory
import com.shuange.lesson.modules.topquality.view.TopQualityCourseFragment
import com.shuange.lesson.utils.ToastUtil
import com.shuange.lesson.utils.extension.bind
import com.shuange.lesson.utils.extension.setOnSearchListener
import kotlinx.android.synthetic.main.layout_header.view.*

/**
 * 搜索
 */
class BaseSearchActivity : BaseActivity<ActivityBaseSearchBinding, BaseSearchViewModel>() {

    companion object {
        fun start(context: Context, defaultType: Int? = null) {
            val i = Intent(context, BaseSearchActivity::class.java)
            defaultType?.let {
                i.putExtra(IntentKey.SEARCH_TYPE, defaultType)
            }
            context.startActivity(i)
        }
    }

    override val viewModel: BaseSearchViewModel by viewModels {
        BaseShareModelFactory()
    }

    override val layoutId: Int
        get() = R.layout.activity_base_search
    override val viewModelId: Int
        get() = BR.baseSearchViewModel

    lateinit var fragmentAdapter: BaseFragmentAdapter

    override fun initParams() {
        super.initParams()
        intent.getIntExtra(IntentKey.SEARCH_TYPE, -1).let {
            if (it != -1) {
                viewModel.defaultTypeId = it
            }
        }
    }

    override fun initView() {
        binding.header.title.text = "搜索"
        viewModel.loadData()
        initViewPager()
        initTabIndicator()
        initListener()
    }

    private fun initViewPager() {
        val fragments = arrayListOf<Fragment>()
        viewModel.pager.forEach {
            //TODO 搜索
//            pager.add(Pair(0, "课程"))
//            pager.add(Pair(1, "直播课程"))
//            pager.add(Pair(2, "资讯"))
//            pager.add(Pair(3, "老师"))
            fragments.add(TopQualityCourseFragment.newInstance(it.first))
            fragments.add(TopQualityCourseFragment.newInstance(it.first))

        }
        fragmentAdapter = BaseFragmentAdapter(this, fragments)
        with(binding.vp) {
            adapter = fragmentAdapter
            setCurrentItem(viewModel.getDefaultPageIndex())
        }
    }

    private fun initTabIndicator() {
//        binding.tabTl.init(binding.vp, viewModel.pager)
        binding.indicators.bind(binding.vp, viewModel.pager.map {
            it.second
        }.toMutableList())

    }

    private fun initListener() {
        binding.searchEt.setOnSearchListener {
            search(it.trim())
        }
    }

    fun search(text: String) {
        ToastUtil.show(text)
    }

    override fun initViewObserver() {
    }


}