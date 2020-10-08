package com.shuange.lesson.modules.topquality.view

import android.content.Context
import android.content.Intent
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.shuange.lesson.BR
import com.shuange.lesson.R
import com.shuange.lesson.base.BaseActivity
import com.shuange.lesson.base.adapter.RecyclePagerAdapter
import com.shuange.lesson.base.adapter.registerRecycleOnPageChangeCallback
import com.shuange.lesson.base.viewmodel.BaseShareModelFactory
import com.shuange.lesson.databinding.ActivityTopQualityMainBinding
import com.shuange.lesson.modules.course.adapter.CourseInfoAdapter
import com.shuange.lesson.modules.course.view.CourseInfoActivity
import com.shuange.lesson.modules.course.view.CourseListActivity
import com.shuange.lesson.modules.topquality.adapter.TopQualityAdapter
import com.shuange.lesson.modules.topquality.viewmodel.TopQualityMainViewModel
import com.shuange.lesson.utils.ToastUtil
import com.shuange.lesson.utils.extension.initAdapter
import com.shuange.lesson.utils.extension.setOnSearchListener
import com.shuange.lesson.view.NonDoubleClickListener
import corelib.util.DeviceUtils
import kotlinx.android.synthetic.main.layout_header.view.*

/**
 * 精品课程
 */
class TopQualityMainActivity :
    BaseActivity<ActivityTopQualityMainBinding, TopQualityMainViewModel>() {

    companion object {
        fun start(context: Context) {
            val i = Intent(context, TopQualityMainActivity::class.java)
            context.startActivity(i)
        }
    }

    override val viewModel: TopQualityMainViewModel by viewModels {
        BaseShareModelFactory()
    }

    override val layoutId: Int
        get() = R.layout.activity_top_quality_main
    override val viewModelId: Int
        get() = BR.topQualityMainViewModel

    private val topQualityAdapter: TopQualityAdapter by lazy {
        TopQualityAdapter(
            layout = R.layout.layout_top_quality_item,
            data = viewModel.topQualityItems
        )
    }

    private val courseInfoAdapter: CourseInfoAdapter by lazy {
        CourseInfoAdapter(
            layout = R.layout.layout_top_quality_main_course,
            data = viewModel.courses
        )
    }

    override fun initView() {
        binding.header.title.text = "精品课程"
        viewModel.loadData()
        initListener()
        initViewPager()
        initTopQuality()
        initCourse()
//        initTabIndicator()
    }

    fun initTopQuality() {
        with(binding.topQualityRv) {
            layoutManager =
                LinearLayoutManager(this@TopQualityMainActivity, RecyclerView.VERTICAL, false)
            topQualityAdapter.setOnItemClickListener { adapter, view, position ->
                CourseInfoActivity.start(context)
            }
            isNestedScrollingEnabled = false
            adapter = topQualityAdapter
        }
    }

    fun initCourse() {
        with(binding.suggestCourseRv) {
            layoutManager = LinearLayoutManager(
                this@TopQualityMainActivity,
                RecyclerView.HORIZONTAL,
                false
            )
            courseInfoAdapter.setOnItemClickListener { adapter, view, position ->
                TopQualityActivity.start(this@TopQualityMainActivity)
            }
            adapter = courseInfoAdapter
        }
    }

    private fun initViewPager() {
        binding.vp.initAdapter(this, viewModel.pagerData)
        val currentItem = binding.vp.currentItem
        binding.vp.setOnClickListener(NonDoubleClickListener {
            val id = viewModel.pagerData[currentItem].id
            val title = viewModel.pagerData[currentItem].title
            CourseListActivity.start(this, id, title)
        })
        bindIndicatorToViewPager(binding.indicatorContainerLl, binding.vp)
    }

    private fun bindIndicatorToViewPager(
        indicatorContainer: LinearLayout,
        viewPager2: ViewPager2,
        selectorRes: Int = R.drawable.selector_indicator
    ) {
        val a = viewPager2.adapter as? RecyclePagerAdapter<*> ?: return
        val size = a.data.size
        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        val margin = DeviceUtils.toPx(this, 4)
        params.setMargins(margin, 0, margin, 0)
        for (i in 0 until size) {
            val indicator = ImageView(this)
            indicator.setImageResource(selectorRes)
            indicator.layoutParams = params
            indicatorContainer.addView(indicator)
        }
        viewPager2.registerRecycleOnPageChangeCallback { position ->
            for (i in 0 until size) {
                indicatorContainer.getChildAt(i).isSelected = i == position
            }
        }
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