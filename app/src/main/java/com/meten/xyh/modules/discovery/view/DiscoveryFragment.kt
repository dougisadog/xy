package com.meten.xyh.modules.discovery.view

import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.meten.xyh.BR
import com.meten.xyh.R
import com.meten.xyh.databinding.FragmentDiscoveryBinding
import com.meten.xyh.modules.discovery.adapter.MenuPageAdapter
import com.meten.xyh.modules.discovery.adapter.StreamLessonAdapter
import com.meten.xyh.modules.discovery.bean.MenuItem
import com.meten.xyh.modules.discovery.viewmodel.DiscoveryViewModel
import com.meten.xyh.modules.search.view.SearchActivity
import com.shuange.lesson.base.BaseFragment
import com.shuange.lesson.base.adapter.BaseItemAdapter
import com.shuange.lesson.base.adapter.RecyclePagerAdapter
import com.shuange.lesson.base.adapter.registerRecycleOnPageChangeCallback
import com.shuange.lesson.base.config.ConfigDef
import com.shuange.lesson.base.viewmodel.BaseShareModelFactory
import com.shuange.lesson.modules.course.view.CourseAllActivity
import com.shuange.lesson.modules.course.view.CourseModulesActivity
import com.shuange.lesson.modules.news.view.NewsDetailActivity
import com.shuange.lesson.modules.news.view.NewsListActivity
import com.shuange.lesson.modules.search.view.BaseSearchActivity
import com.shuange.lesson.modules.teacher.adapter.TeacherAdapter
import com.shuange.lesson.modules.teacher.view.TeacherInfoActivity
import com.shuange.lesson.modules.teacher.view.TeacherListActivity
import com.shuange.lesson.modules.topquality.adapter.TopQualityAdapter
import com.shuange.lesson.modules.topquality.view.TopQualityActivity
import com.shuange.lesson.modules.topquality.view.TopQualityMainActivity
import com.shuange.lesson.utils.BusinessUtil
import com.shuange.lesson.utils.ToastUtil
import com.shuange.lesson.utils.extension.force2Long
import com.shuange.lesson.utils.extension.initAdapter
import com.shuange.lesson.view.NonDoubleClickListener
import corelib.util.ContextManager
import corelib.util.DeviceUtils

class DiscoveryFragment : BaseFragment<FragmentDiscoveryBinding, DiscoveryViewModel>() {

    override val viewModel: DiscoveryViewModel by viewModels {
        BaseShareModelFactory()
    }

    override val layoutId: Int
        get() = R.layout.fragment_discovery
    override val viewModelId: Int
        get() = BR.discoveryViewModel

    private val streamLessonAdapter: StreamLessonAdapter by lazy {
        StreamLessonAdapter(
            layout = R.layout.layout_stream_lesson_item,
            data = viewModel.streamLessons
        )
    }

    private val topQualityAdapter: TopQualityAdapter by lazy {
        TopQualityAdapter(
            layout = R.layout.layout_top_quality_item,
            data = viewModel.topQualityItems
        )
    }

    private val teacherAdapter: TeacherAdapter by lazy {
        TeacherAdapter(
            layout = R.layout.layout_teacher_item,
            data = viewModel.teachers
        )
    }

    private val newsAdapter: BaseItemAdapter by lazy {
        BaseItemAdapter(
            layout = R.layout.layout_base_item,
            data = viewModel.newsItems
        )
    }

    override fun initView() {
        viewModel.loadData()
        initListener()
        initMenu()
        initStreamLessons()
        initTopQuality()
        initTeachers()
        initNews()
    }

    private fun initViewPager() {
        binding.vp.initAdapter(this, viewModel.wheels)
        val currentItem = binding.vp.currentItem
        binding.vp.setOnClickListener(NonDoubleClickListener {
            val id = viewModel.wheels[currentItem].id
            val title = viewModel.wheels[currentItem].title
            CourseModulesActivity.start(requireContext(), id.force2Long(), title)
        })
        bindIndicatorToViewPager(binding.indicatorContainerLl, binding.vp)
        initTabIndicator()
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
        val margin = DeviceUtils.toPx(requireContext(), 4)
        params.setMargins(margin, 0, margin, 0)
        for (i in 0 until size) {
            val indicator = ImageView(requireContext())
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

    fun initStreamLessons() {
        with(binding.streamRv) {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
            streamLessonAdapter.setOnItemClickListener { adapter, view, position ->
                ToastUtil.show("item click stream:${streamLessonAdapter.data[position].title}")
            }
            adapter = streamLessonAdapter
        }
    }

    fun initTopQuality() {
        with(binding.topQualityRv) {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            topQualityAdapter.setOnItemClickListener { adapter, view, position ->
                BusinessUtil.startCourse(requireContext(), topQualityAdapter.data[position])
            }
            isNestedScrollingEnabled = false
            adapter = topQualityAdapter
        }
    }

    fun initTeachers() {
        with(binding.teacherRv) {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
            teacherAdapter.setOnItemClickListener { adapter, view, position ->
                TeacherInfoActivity.start(requireContext(), teacherAdapter.data[position].id)
            }
            adapter = teacherAdapter
        }
    }

    fun initNews() {
        with(binding.newsRv) {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            newsAdapter.setOnItemClickListener { adapter, view, position ->
                NewsDetailActivity.start(requireContext(), newsAdapter.data[position])
            }
            isNestedScrollingEnabled = false
            adapter = newsAdapter
        }
    }

    private fun initMenu() {
        with(binding.menuVp) {
            val itemsR1 = mutableListOf<MenuItem>()
            itemsR1.add(MenuItem("直播课程", R.drawable.icon_home_zb) {
                ToastUtil.show(ContextManager.getContext().getString(R.string.not_support))
            })
            itemsR1.add(MenuItem("每日一句", R.drawable.icon_home_mryj) {
                TopQualityActivity.start(requireActivity())
            })
            itemsR1.add(MenuItem("赛培课程", R.drawable.icon_home_ss) {
                CourseAllActivity.start(requireActivity(), ConfigDef.COURSE_TYPE_MATCH)
            })
            itemsR1.add(MenuItem("教师中心", R.drawable.icon_home_teacher) {
                TeacherListActivity.start(requireActivity())
            })


            val itemsR2 = mutableListOf<MenuItem>()
            itemsR2.add(MenuItem("幼儿课程", R.drawable.icon_home_child) {
                CourseAllActivity.start(requireActivity(), ConfigDef.COURSE_TYPE_CHILD)
            })
            itemsR2.add(MenuItem("小学课程", R.drawable.icon_home_primary) {
                CourseAllActivity.start(requireActivity(), ConfigDef.COURSE_TYPE_PRIMARY)
            })
            itemsR2.add(MenuItem("高中大学", R.drawable.icon_home_senior_high) {
                CourseAllActivity.start(requireActivity(), ConfigDef.COURSE_TYPE_SENIOR_HIGH)
            })
            itemsR2.add(MenuItem("大学课程", R.drawable.icon_home_college) {
                CourseAllActivity.start(requireActivity(), ConfigDef.COURSE_TYPE_COLLEGE)
            })
            val page1 = mutableListOf<MenuItem>()
            page1.addAll(itemsR1)
            page1.addAll(itemsR2)
//            val page2 = mutableListOf<MenuItem>()
//            page2.addAll(itemsR1)
            adapter = MenuPageAdapter(requireContext(), mutableListOf(page1))
        }
    }

    private fun initTabIndicator() {
        binding.tabTl.tabMode = TabLayout.MODE_FIXED
        TabLayoutMediator(
            binding.tabTl, binding.vp
        ) { tab, position ->
        }.attach()
        binding.tabTl.visibility = View.INVISIBLE
    }

    private fun initListener() {
        binding.streamLl.setOnClickListener(NonDoubleClickListener {
            ToastUtil.show(ContextManager.getContext().getString(R.string.not_support))
        })
        binding.topQualityLl.setOnClickListener(NonDoubleClickListener {
            TopQualityMainActivity.start(requireActivity())
        })
        binding.teacherLl.setOnClickListener(NonDoubleClickListener {
            TeacherListActivity.start(requireActivity())
        })
        binding.newsLl.setOnClickListener(NonDoubleClickListener {
            NewsListActivity.start(requireContext())
        })
//        binding.searchEt.setOnSearchListener {
//            search(it.trim())
//        }
        binding.searchRl.setOnClickListener(NonDoubleClickListener {
            BaseSearchActivity.start(requireContext())
        })


    }

    fun search(text: String) {
        SearchActivity.start(requireContext(), text)
    }

    override fun initViewObserver() {
        viewModel.wheelsLoaded.observe(this, Observer {
            initViewPager()
        })
    }
}