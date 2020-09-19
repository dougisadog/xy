package com.meten.xyh.modules.discovery.view

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.meten.xyh.BR
import com.meten.xyh.R
import com.meten.xyh.databinding.FragmentDiscoveryBinding
import com.meten.xyh.modules.discovery.adapter.*
import com.meten.xyh.modules.discovery.bean.MenuItem
import com.meten.xyh.modules.discovery.viewmodel.DiscoveryViewModel
import com.meten.xyh.modules.search.view.SearchActivity
import com.meten.xyh.modules.teacher.view.TeacherInfoActivity
import com.meten.xyh.modules.teacher.view.TeacherListActivity
import com.shuange.lesson.base.BaseFragment
import com.shuange.lesson.base.viewmodel.BaseShareModelFactory
import com.shuange.lesson.modules.topquality.view.TopQualityActivity
import com.shuange.lesson.utils.ToastUtil
import com.shuange.lesson.utils.extension.setOnSearchListener
import com.shuange.lesson.view.NonDoubleClickListener

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
            layout = R.layout.layout_top_quality_course_item,
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
        initViewPage()
        initTabIndicator()
        initStreamLessons()
        initTopQuality()
        initTeachers()
        initNews()
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

    private fun initViewPage() {
        with(binding.vp) {
            val itemsR1 = mutableListOf<MenuItem>()
            itemsR1.add(MenuItem("直播课程", R.drawable.icon_home_zb) {
                ToastUtil.show("直播课程")
            })
            itemsR1.add(MenuItem("按主题学", R.drawable.icon_home_fl) {
                ToastUtil.show("按主题学")
            })
            itemsR1.add(MenuItem("按等级学", R.drawable.icon_home_lv) {
                ToastUtil.show("按等级学")
            })
            itemsR1.add(MenuItem("定制课程", R.drawable.icon_home_dz) {
                ToastUtil.show("定制课程")
            })
            itemsR1.add(MenuItem("免费专区", R.drawable.icon_home_lw) {
                ToastUtil.show("免费专区")
            })


            val itemsR2 = mutableListOf<MenuItem>()
            itemsR2.add(MenuItem("每日一句", R.drawable.icon_home_mryj) {
                TopQualityActivity.start(requireActivity())
            })
            itemsR2.add(MenuItem("赛事主题", R.drawable.icon_home_ss) {
                ToastUtil.show("赛事主题")
            })
            itemsR2.add(MenuItem("有道翻译", R.drawable.icon_home_fy) {
                ToastUtil.show("有道翻译")
            })
            itemsR2.add(MenuItem("发音训练", R.drawable.icon_home_fyxl) {
                ToastUtil.show("发音训练")
            })
            itemsR2.add(MenuItem("出国旅游", R.drawable.icon_home_cgly) {
                ToastUtil.show("出国旅游")
            })
            val page1 = mutableListOf<MenuItem>()
            page1.addAll(itemsR1)
            page1.addAll(itemsR2)
            val page2 = mutableListOf<MenuItem>()
            page2.addAll(itemsR1)
            adapter = MenuPageAdapter(requireContext(), mutableListOf(page1, page2))
        }
    }

    private fun initTabIndicator() {
        binding.tabTl.tabMode = TabLayout.MODE_FIXED
        TabLayoutMediator(
            binding.tabTl, binding.vp
        ) { tab, position ->
        }.attach()
    }

    private fun initListener() {
        binding.streamLl.setOnClickListener(NonDoubleClickListener {
            ToastUtil.show("直播课程 List")
        })
        binding.resetTopQualityIv.setOnClickListener(NonDoubleClickListener {
            val source = viewModel.topQualityItems.toMutableList()
            source.shuffle()
            viewModel.topQualityItems.clear()
            viewModel.topQualityItems.addAll(source)
            ToastUtil.show("精品课程 换一批")
        })
        binding.teacherLl.setOnClickListener(NonDoubleClickListener {
            TeacherListActivity.start(requireActivity())
        })
        binding.newsLl.setOnClickListener(NonDoubleClickListener {
            ToastUtil.show("英语资讯 List")
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