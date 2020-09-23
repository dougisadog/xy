package com.meten.xyh.modules.main.view

import android.content.Context
import android.content.Intent
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.meten.xyh.BR
import com.meten.xyh.R
import com.meten.xyh.databinding.ActivityMainBinding
import com.meten.xyh.modules.course.view.MyCourseFragment
import com.meten.xyh.modules.discovery.view.DiscoveryFragment
import com.meten.xyh.modules.main.viewmodel.MainViewModel
import com.meten.xyh.modules.user.view.UserAccountFragment
import com.shuange.lesson.base.BaseActivity
import com.shuange.lesson.base.adapter.BaseFragmentAdapter
import com.shuange.lesson.base.viewmodel.BaseShareModelFactory

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    companion object {
        fun start(context: Context) {
            val i = Intent(context, MainActivity::class.java)
            context.startActivity(i)
        }
    }

    override val viewModel: MainViewModel by viewModels {
        BaseShareModelFactory()
    }

    override val layoutId: Int
        get() = R.layout.activity_main
    override val viewModelId: Int
        get() = BR.mainViewModel

    override var fragmentContainerId: Int? = R.id.fragmentContainerFl

    lateinit var mainAdapter: BaseFragmentAdapter


    override fun initView() {
        viewModel.loadData()
        initListener()
        initViewPager()
    }

    private fun initViewPager() {
        val fragments = arrayListOf<Fragment>()
        fragments.add(DiscoveryFragment())
        fragments.add(MyCourseFragment())
        fragments.add(UserAccountFragment())
        mainAdapter = BaseFragmentAdapter(this, fragments)
        with(binding.vp) {
            adapter = mainAdapter
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    refreshTab()
                }
            })
            //禁止拖动，避免与内部recycleView和viewpager冲突
            isUserInputEnabled = false
        }
        refreshTab()
    }

    private fun initListener() {
        binding.discoveryRl.setOnClickListener {
            binding.vp.setCurrentItem(0, false)
        }
        binding.courseRL.setOnClickListener {
            binding.vp.setCurrentItem(1, false)
        }
        binding.accountRl.setOnClickListener {
            binding.vp.setCurrentItem(2, false)
        }
    }

    private fun refreshTab() {
        val tabCount = binding.tabCl.childCount
        for (i in 0 until tabCount) {
            val selected = i == binding.vp.currentItem
            val container = (binding.tabCl.getChildAt(i) as ViewGroup)
//            (container.getChildAt(0) as ImageView).setImageResource()
            (container.getChildAt(0)).isSelected = selected
        }
    }

    override fun initViewObserver() {
    }


}