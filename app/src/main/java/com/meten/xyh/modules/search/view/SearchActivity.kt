package com.meten.xyh.modules.search.view

import android.content.Context
import android.content.Intent
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.RadioButton
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.meten.xyh.BR
import com.meten.xyh.R
import com.meten.xyh.base.config.IntentKey
import com.meten.xyh.databinding.ActivitySearchBinding
import com.meten.xyh.modules.search.viewmodel.SearchViewModel
import com.shuange.lesson.base.BaseActivity
import com.shuange.lesson.base.viewmodel.BaseShareModelFactory
import com.shuange.lesson.modules.teacher.view.TeacherInfoActivity
import com.shuange.lesson.utils.ToastUtil
import com.shuange.lesson.utils.extension.setOnSearchListener
import corelib.util.DeviceUtils


/**
 * 搜索
 */
class SearchActivity : BaseActivity<ActivitySearchBinding, SearchViewModel>() {

    companion object {
        fun start(context: Context, search: String) {
            val i = Intent(context, TeacherInfoActivity::class.java)
            i.putExtra(IntentKey.SEARCH_KEY, search)
            context.startActivity(i)
        }
    }

    override val viewModel: SearchViewModel by viewModels {
        BaseShareModelFactory()
    }


    override val layoutId: Int
        get() = R.layout.activity_search
    override val viewModelId: Int
        get() = BR.loginViewModel


    override fun initParams() {
        super.initParams()
        intent.getStringExtra(IntentKey.SEARCH_KEY)?.let {
            viewModel.searchText.value = it
            search(it)
        }
    }

    override fun initView() {
        initListener()
        initLabels()
    }

    fun initLabels() {
        val margin = DeviceUtils.toPx(this, 6)
        val padding = DeviceUtils.toPx(this, 6)
        viewModel.labels.forEach {
            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(margin, margin, margin, margin)
            val rb = RadioButton(this).apply {
                textSize = 16f
                setTextColor(ContextCompat.getColor(this@SearchActivity, R.color.hex_7A7A7A))

                setPadding(padding, padding, padding, padding)
                layoutParams = params
                gravity = Gravity.CENTER
                text = it
                buttonDrawable = null
            }
            binding.wordsRg.addView(rb)
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