package com.shuange.lesson.modules.lesson.view

import android.os.Handler
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.viewpager2.widget.ViewPager2
import com.shuange.lesson.BR
import com.shuange.lesson.R
import com.shuange.lesson.base.BaseActivity
import com.shuange.lesson.base.BaseFragment
import com.shuange.lesson.base.adapter.BaseFragmentAdapter
import com.shuange.lesson.base.config.ConfigDef
import com.shuange.lesson.base.viewmodel.BaseShareModelFactory
import com.shuange.lesson.databinding.ActivityLessonBinding
import com.shuange.lesson.modules.lesson.viewmodel.LessonViewModel
import com.shuange.lesson.utils.ToastUtil
import corelib.extension.roundInt
import java.math.BigDecimal

class LessonActivity : BaseActivity<ActivityLessonBinding, LessonViewModel>() {

    override val viewModel: LessonViewModel by viewModels {
        BaseShareModelFactory()
    }


    override val layoutId: Int
        get() = R.layout.activity_lesson
    override val viewModelId: Int
        get() = BR.lessonViewModel


    lateinit var lessonAdapter: BaseFragmentAdapter

    var isChangingPage = false

    val handler by lazy { Handler() }

    override fun initView() {
        initLessons()
        initListener()
        viewModel.initData()
    }

    private fun initLessons() {
        val lessonsFragments = arrayListOf<Fragment>()
        lessonAdapter = BaseFragmentAdapter(this, lessonsFragments)
        with(binding.vp) {
            adapter = lessonAdapter
        }
    }

    private fun initListener() {
        binding.vp.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                viewModel.progress.value =
                    BigDecimal(100.0 * (position + 1) / viewModel.lessons.size).roundInt()
            }
        })
        binding.closeIv.setOnClickListener {
            finish()
        }
    }

    override fun initViewObserver() {
        viewModel.loaded.observe(this, Observer {
            if (null != it) {
                val index = viewModel.targetIndex
                val lessonBean = viewModel.lessons[index]
                viewModel.targetIndex++
                BaseLessonFragment.newInstance(lessonBean)?.let { f ->
                    lessonAdapter.fragments.add(f)
                    lessonAdapter.notifyItemInserted(index)
                    viewModel.loaded.value = null
                }
                viewModel.loadLessonSource()
            }
        })
        viewModel.next.observe(this, Observer {
            if (isChangingPage) return@Observer
            if (it == true) {
                isChangingPage = true
                handler.postDelayed({
                    next()
                    viewModel.next.value = false
                }, ConfigDef.pagerDelay)
            }
        })

        viewModel.wrong.observe(this, Observer {
            if (it == true) {
                val currentLife = viewModel.life.value ?: return@Observer
                viewModel.life.value = currentLife - 1
                if (currentLife == 1) {
                    dead()
                }
            }
        })
    }

    fun next() {
        if (viewModel.targetIndex <= ConfigDef.MIN_LOADED_SIZE) return
        val current = binding.vp.currentItem
        if (current < lessonAdapter.itemCount - 1) {
            binding.vp.setCurrentItem(current + 1, true)
        }
    }

    fun dead() {
        ToastUtil.show("dead")
    }


    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null)
    }
}