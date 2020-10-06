package com.meten.xyh.modules.discovery.view

import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.meten.xyh.BR
import com.meten.xyh.R
import com.meten.xyh.databinding.FragmentDiscoveryBinding
import com.meten.xyh.modules.discovery.adapter.BaseItemAdapter
import com.meten.xyh.modules.discovery.adapter.MenuPageAdapter
import com.meten.xyh.modules.discovery.adapter.StreamLessonAdapter
import com.meten.xyh.modules.discovery.adapter.TeacherAdapter
import com.meten.xyh.modules.discovery.bean.MenuItem
import com.meten.xyh.modules.discovery.viewmodel.DiscoveryViewModel
import com.meten.xyh.modules.news.view.NewsListActivity
import com.meten.xyh.modules.search.view.SearchActivity
import com.meten.xyh.modules.teacher.view.TeacherInfoActivity
import com.meten.xyh.modules.teacher.view.TeacherListActivity
import com.shuange.lesson.base.BaseFragment
import com.shuange.lesson.base.ImageFragment
import com.shuange.lesson.base.adapter.RecyclePagerAdapter
import com.shuange.lesson.base.adapter.registerRecycleOnPageChangeCallback
import com.shuange.lesson.base.adapter.setRecycleAdapter
import com.shuange.lesson.base.adapter.starAuto
import com.shuange.lesson.base.viewmodel.BaseShareModelFactory
import com.shuange.lesson.modules.course.view.CourseAllActivity
import com.shuange.lesson.modules.course.view.CourseListActivity
import com.shuange.lesson.modules.topquality.adapter.TopQualityAdapter
import com.shuange.lesson.modules.topquality.view.TopQualityActivity
import com.shuange.lesson.utils.ToastUtil
import com.shuange.lesson.utils.extension.setOnSearchListener
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

    lateinit var fragmentAdapter: RecyclePagerAdapter<String>

    private val streamLessonAdapter: StreamLessonAdapter by lazy {
        StreamLessonAdapter(
            layout = R.layout.layout_stream_lesson_item,
            data = viewModel.streamLessons
        )
    }

    private val topQualityAdapter: TopQualityAdapter by lazy {
        TopQualityAdapter(
            layout = com.shuange.lesson.R.layout.layout_top_quality_item,
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
        initViewPager()
        initMenu()
        initTabIndicator()
        initStreamLessons()
        initTopQuality()
        initTeachers()
        initNews()
    }

    private fun initViewPager() {
        fragmentAdapter = RecyclePagerAdapter(this, viewModel.pagerData.map {
            it.image
        }.toMutableList()) {
            ImageFragment.newInstance(it)
        }
        with(binding.vp) {
            setRecycleAdapter(fragmentAdapter)
            starAuto()
            setOnClickListener(NonDoubleClickListener {
                val id = viewModel.pagerData[currentItem].id
                val title = viewModel.pagerData[currentItem].title
                CourseListActivity.start(context, id, title)
            })
        }
        bindIndicatorToViewPager(binding.indicatorContainerLl, binding.vp)
    }

    private fun bindIndicatorToViewPager(
        indicatorContainer: LinearLayout,
        viewPager2: ViewPager2,
        selectorRes: Int = com.shuange.lesson.R.drawable.selector_indicator
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
                ToastUtil.show("item click  topQuality:${topQualityAdapter.data[position].title}")
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
                ToastUtil.show("item click  teacher:${newsAdapter.data[position].title}")
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
            itemsR1.add(MenuItem("赛培课程", R.drawable.icon_home_lv) {
                CourseAllActivity.start(requireActivity(), 0)
            })
            itemsR1.add(MenuItem("教师中心", R.drawable.icon_home_dz) {
                TeacherListActivity.start(requireActivity())
            })


            val itemsR2 = mutableListOf<MenuItem>()
            itemsR2.add(MenuItem("幼儿课程", R.drawable.icon_home_mryj) {
                CourseAllActivity.start(requireActivity(), 1)
            })
            itemsR2.add(MenuItem("小学课程", R.drawable.icon_home_ss) {
                CourseAllActivity.start(requireActivity(), 2)
            })
            itemsR2.add(MenuItem("高中大学", R.drawable.icon_home_fy) {
                CourseAllActivity.start(requireActivity(), 3)
            })
            itemsR2.add(MenuItem("大学课程", R.drawable.icon_home_fyxl) {
                CourseAllActivity.start(requireActivity(), 4)
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
            CourseAllActivity.start(requireActivity())
        })
        binding.teacherLl.setOnClickListener(NonDoubleClickListener {
            TeacherListActivity.start(requireActivity())
        })
        binding.newsLl.setOnClickListener(NonDoubleClickListener {
            NewsListActivity.start(requireContext())
        })
        binding.searchEt.setOnSearchListener {
            search(it.trim())
        }

    }

    fun search(text: String) {
        SearchActivity.start(requireContext(), text)
    }

    override fun initViewObserver() {
    }
}