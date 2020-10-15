package com.shuange.lesson.modules.lesson.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.util.Log
import android.view.MotionEvent
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.viewpager2.widget.ViewPager2
import com.shuange.lesson.BR
import com.shuange.lesson.R
import com.shuange.lesson.base.BaseActivity
import com.shuange.lesson.base.adapter.BaseFragmentAdapter
import com.shuange.lesson.base.config.AppConfig
import com.shuange.lesson.base.config.ConfigDef
import com.shuange.lesson.base.config.IntentKey
import com.shuange.lesson.base.viewmodel.BaseShareModelFactory
import com.shuange.lesson.databinding.ActivityLessonBinding
import com.shuange.lesson.modules.lesson.viewmodel.LessonViewModel
import com.shuange.lesson.utils.ToastUtil

class LessonActivity : BaseActivity<ActivityLessonBinding, LessonViewModel>() {

    companion object {
        fun start(context: Context, moduleId: Long, lastQuestionIndex: Int) {
            val i = Intent(context, LessonActivity::class.java)
            i.putExtra(IntentKey.MODULE_ID, moduleId)
            lastQuestionIndex.let {
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
        viewModel.moduleId = intent.getLongExtra(IntentKey.MODULE_ID, 0).toString()
        viewModel.lastQuestionIndex = intent.getIntExtra(IntentKey.LAST_QUESTION_ID, 0)
    }

    override fun initView() {
        viewModel.loadData()
        viewModel.getLessonsData {
            initLessons()
            initListener()
        }
    }

    var lastX = 0f

    @SuppressLint("ClickableViewAccessibility")
    private fun initLessons() {
        val lessonsFragments = arrayListOf<Fragment>()
        lessonAdapter = BaseFragmentAdapter(this, lessonsFragments)
        with(binding.vp) {
            adapter = lessonAdapter
            offscreenPageLimit = viewModel.lessons.size
            if (AppConfig.DEBUG) {
                isUserInputEnabled = true
            } else {
                isUserInputEnabled = false
                setOnTouchListener { v, event ->
                    when (event.action) {
                        MotionEvent.ACTION_DOWN -> {
                            beginFakeDrag()
                        }
                        MotionEvent.ACTION_MOVE -> {
                            Log.e("touch move", "lastX:$lastX event.x:${event.x}")
                            val offset = event.x - lastX
                            //在最新进度左侧，或者 左滑
                            if (offset > 0 || viewModel.newestSavedIndex > currentItem) {
                                //方向左侧滑动
                                fakeDragBy(offset)
                            }
                        }
                        MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                            endFakeDrag()
                        }
                    }
                    lastX = event.x
                    true
                }
            }
        }
    }

    private fun initListener() {
        binding.progressPb.max = viewModel.lessons.size
        binding.vp.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                viewModel.progress.value = position + 1
//                    BigDecimal(100.0 * (position + 1) / viewModel.lessons.size).roundInt()
                isChangingPage = false
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
                if (null != lastIndex && index - lastIndex >= ConfigDef.MIN_LOADED_SIZE) {
                    binding.vp.currentItem = lastIndex
                    viewModel.lastQuestionIndex = null
                }
                val lessonBean = viewModel.lessons[index]
                viewModel.targetIndex++
                BaseLessonFragment.newInstance(lessonBean)?.let { f ->
                    lessonAdapter.fragments.add(f)
                    lessonAdapter.notifyItemInserted(index)
                    viewModel.loaded.value = null
                }
                viewModel.loadLessonSource()
            } else {
                val lastIndex = viewModel.getLastIndex()
                //最后3个index 在所有资源加载后跳转
                if (null != lastIndex && lastIndex + ConfigDef.MIN_LOADED_SIZE >= viewModel.lessons.size) {
                    binding.vp.currentItem = lastIndex
                    viewModel.lastQuestionIndex = null
                }
            }
        })
        viewModel.next.observe(this, Observer {
            if (isChangingPage) return@Observer
            isChangingPage = true
            handler.postDelayed({
                next(it)
            }, 0)
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

    fun next(index: Int) {
        if (viewModel.targetIndex <= ConfigDef.MIN_LOADED_SIZE) return
        val current = index
        if (current < lessonAdapter.itemCount) {
            viewModel.newestSavedIndex = current
            binding.vp.let {
                if (it.isFakeDragging) {
                    it.endFakeDrag()
                }
                binding.vp.setCurrentItem(current, true)
            }
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