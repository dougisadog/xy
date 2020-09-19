package com.shuange.lesson.modules.unit.view

import android.os.Handler
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.viewpager2.widget.ViewPager2
import com.shuange.lesson.BR
import com.shuange.lesson.R
import com.shuange.lesson.base.BaseActivity
import com.shuange.lesson.base.BaseFragment
import com.shuange.lesson.base.adapter.BaseFragmentAdapter
import com.shuange.lesson.base.config.ConfigDef
import com.shuange.lesson.base.viewmodel.BaseShareModelFactory
import com.shuange.lesson.databinding.ActivityUnitBinding
import com.shuange.lesson.modules.lesson.view.BaseLessonFragment
import com.shuange.lesson.modules.unit.viewmodel.UnitViewModel
import com.shuange.lesson.utils.ToastUtil
import com.shuange.lesson.view.gallery.GalleryPageTransformer
import corelib.extension.roundInt
import java.math.BigDecimal


class UnitActivity : BaseActivity<ActivityUnitBinding, UnitViewModel>() {

    override val viewModel: UnitViewModel by viewModels {
        BaseShareModelFactory()
    }


    override val layoutId: Int
        get() = R.layout.activity_unit
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
        val lessonsFragments = arrayListOf<BaseFragment<*, *>>()
        lessonAdapter = BaseFragmentAdapter(this, lessonsFragments)
        with(binding.vp) {
            adapter = lessonAdapter
            clipChildren = false
            setPageTransformer(GalleryPageTransformer())
            offscreenPageLimit = 3
        }
    }

    private fun initListener() {
        binding.vp.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                viewModel.progress.value =
                    BigDecimal(100.0 * (position + 1) / viewModel.lessons.size).roundInt()
            }
        })
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