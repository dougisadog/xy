package com.shuange.lesson.modules.topquality.view

import android.content.Context
import android.content.Intent
import android.view.Gravity
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.shuange.lesson.R
import com.shuange.lesson.BR
import com.shuange.lesson.base.BaseActivity
import com.shuange.lesson.base.BaseFragment
import com.shuange.lesson.base.adapter.BaseFragmentAdapter
import com.shuange.lesson.base.viewmodel.BaseShareModelFactory
import com.shuange.lesson.databinding.ActivityTopQualityBinding
import com.shuange.lesson.modules.topquality.viewmodel.TopQualityViewModel
import com.shuange.lesson.utils.ToastUtil
import com.shuange.lesson.utils.extension.setOnSearchListener
import kotlinx.android.synthetic.main.layout_header.view.*

/**
 * "每日一句
 */
class TopQualityActivity : BaseActivity<ActivityTopQualityBinding, TopQualityViewModel>() {

    companion object {
        fun start(context:Context) {
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
        initViewPager()
        initTabIndicator()
    }

    private fun initViewPager() {
        val fragments = arrayListOf<Fragment>()
        fragments.add(GalleryFragment())
        fragments.add(GalleryFragment())
        fragments.add(TopQualityCourseFragment())
        fragments.add(TopQualityCourseFragment())
        fragmentAdapter = BaseFragmentAdapter(this, fragments)
        with(binding.vp) {
            adapter = fragmentAdapter
        }
    }

    private fun initTabIndicator() {
        binding.tabTl.tabMode = TabLayout.MODE_FIXED
        binding.tabTl.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (null == tab) return
                val tv = TextView(this@TopQualityActivity)
                tv.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                tv.gravity = Gravity.CENTER
                tv.text = tab.text
                tv.setTextColor(ContextCompat.getColor(this@TopQualityActivity, R.color.hex_2FD393))
                tv.textSize = 18f
                tab.customView = tv
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                tab?.customView = null
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })
        TabLayoutMediator(
            binding.tabTl, binding.vp
        ) { tab, position ->
            tab.text = viewModel.pager[position]
        }.attach()
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