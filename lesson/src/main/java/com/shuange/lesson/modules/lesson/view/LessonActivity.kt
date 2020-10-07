package com.shuange.lesson.modules.lesson.view

import android.content.Context
import android.content.Intent
import android.os.Handler
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.viewpager2.widget.ViewPager2
import com.shuange.lesson.BR
import com.shuange.lesson.R
import com.shuange.lesson.base.BaseActivity
import com.shuange.lesson.base.adapter.BaseFragmentAdapter
import com.shuange.lesson.base.config.ConfigDef
import com.shuange.lesson.base.config.IntentKey
import com.shuange.lesson.base.viewmodel.BaseShareModelFactory
import com.shuange.lesson.databinding.ActivityLessonBinding
import com.shuange.lesson.modules.lesson.viewmodel.LessonViewModel
import com.shuange.lesson.utils.ToastUtil
import corelib.extension.roundInt
import java.math.BigDecimal

class LessonActivity : BaseActivity<ActivityLessonBinding, LessonViewModel>() {

    companion object {
        fun start(context: Context, moduleId: String, lastQuestionId:String? = null) {
            val i = Intent(context, LessonActivity::class.java)
            i.putExtra(IntentKey.MODULE_ID, moduleId)
            lastQuestionId?.let {
                i.putExtra(IntentKey.LAST_QUESTION_ID, it)

            }
            context.startActivity(i)
        }
    }

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

    override fun initParams() {
        super.initParams()
        viewModel.moduleId = intent.getStringExtra(IntentKey.MODULE_ID) ?: return
        viewModel.lastQuestionId = intent.getStringExtra(IntentKey.LAST_QUESTION_ID)
    }

    override fun initView() {
        initLessons()
        initListener()
        viewModel.loadData()
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
                val lastIndex = viewModel.getLastIndex()
                //预先额外加载3个index跳转
                if (index - lastIndex>= ConfigDef.MIN_LOADED_SIZE) {
                    binding.vp.currentItem = lastIndex
                }
                val lessonBean = viewModel.lessons[index]
                viewModel.targetIndex++
                BaseLessonFragment.newInstance(lessonBean)?.let { f ->
                    lessonAdapter.fragments.add(f)
                    lessonAdapter.notifyItemInserted(index)
                    viewModel.loaded.value = null
                }
                viewModel.loadLessonSource()
            }
            else {
                val lastIndex = viewModel.getLastIndex()
                //最后3个index 在所有资源加载后跳转
                if (lastIndex + ConfigDef.MIN_LOADED_SIZE >= viewModel.lessons.size) {
                    binding.vp.currentItem = lastIndex
                }
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