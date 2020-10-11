package com.shuange.lesson.modules.search.view

import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import com.shuange.lesson.BR
import com.shuange.lesson.R
import com.shuange.lesson.base.BaseActivity
import com.shuange.lesson.base.adapter.BaseFragmentAdapter
import com.shuange.lesson.base.config.ConfigDef
import com.shuange.lesson.base.config.IntentKey
import com.shuange.lesson.base.viewmodel.BaseShareModelFactory
import com.shuange.lesson.databinding.ActivityBaseSearchBinding
import com.shuange.lesson.modules.news.view.NewsListFragment
import com.shuange.lesson.modules.search.viewmodel.BaseSearchViewModel
import com.shuange.lesson.modules.teacher.view.TeacherListFragment
import com.shuange.lesson.modules.topquality.view.TopQualityCourseFragment
import com.shuange.lesson.utils.ToastUtil
import com.shuange.lesson.utils.extension.bind
import kotlinx.android.synthetic.main.layout_header.view.*

/**
 * 搜索
 */
class BaseSearchActivity : BaseActivity<ActivityBaseSearchBinding, BaseSearchViewModel>() {

    companion object {
        fun start(context: Context, defaultType: String? = null) {
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
        intent.getStringExtra(IntentKey.SEARCH_TYPE).let {
            viewModel.defaultTypeId = it
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
            when (it.first) {
                ConfigDef.TYPE_COURSE, ConfigDef.TYPE_STREAM -> {
                    fragments.add(TopQualityCourseFragment.newInstance(it.first))
                }
                ConfigDef.TYPE_TEACHER -> {
                    fragments.add(TeacherListFragment.newInstance())
                }
                ConfigDef.TYPE_ARTICLE -> {
                    fragments.add(NewsListFragment.newInstance())
                }
            }
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
//        binding.searchEt.setOnSearchListener {
//            search(it.trim())
//        }
    }

    fun search(text: String) {
        val currentFragment = fragmentAdapter.fragments[binding.vp.currentItem]
        val searchText = viewModel.searchText.value ?: ""
        when (currentFragment) {
            is TopQualityCourseFragment -> {
                currentFragment.search(searchText)
            }
            is NewsListFragment -> {
                currentFragment.search(searchText)
            }
            is TeacherListFragment -> {
                currentFragment.search(searchText)
            }
        }

        ToastUtil.show(text)
    }

    override fun initViewObserver() {
    }


}