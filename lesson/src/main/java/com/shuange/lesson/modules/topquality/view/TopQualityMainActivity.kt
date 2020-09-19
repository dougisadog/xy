package com.shuange.lesson.modules.topquality.view

import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shuange.lesson.BR
import com.shuange.lesson.R
import com.shuange.lesson.base.BaseActivity
import com.shuange.lesson.base.ImageFragment
import com.shuange.lesson.base.adapter.BaseFragmentAdapter
import com.shuange.lesson.base.viewmodel.BaseShareModelFactory
import com.shuange.lesson.databinding.ActivityTopQualityMainBinding
import com.shuange.lesson.modules.course.adapter.CourseInfoAdapter
import com.shuange.lesson.modules.topquality.adapter.TopQualityAdapter
import com.shuange.lesson.modules.topquality.viewmodel.TopQualityMainViewModel
import com.shuange.lesson.utils.ToastUtil
import com.shuange.lesson.utils.extension.setOnSearchListener
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

    lateinit var fragmentAdapter: BaseFragmentAdapter

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
                ToastUtil.show("item click  topQuality:${topQualityAdapter.data[position].title}")
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
        val fragments = arrayListOf<Fragment>()
        val link =
            "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=3844276591,3933131866&fm=26&gp=0.jpg"
        fragments.add(ImageFragment.newInstance(link))
        fragments.add(ImageFragment.newInstance(link))
        fragments.add(ImageFragment.newInstance(link))
        fragments.add(ImageFragment.newInstance(link))
        fragmentAdapter = BaseFragmentAdapter(this, fragments)
        with(binding.vp) {
            adapter = fragmentAdapter
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