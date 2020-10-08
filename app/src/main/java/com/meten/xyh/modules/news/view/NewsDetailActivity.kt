package com.meten.xyh.modules.news.view

import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import com.meten.xyh.R
import com.meten.xyh.base.config.IntentKey
import com.meten.xyh.databinding.ActivityNewsDetailBinding
import com.meten.xyh.modules.news.viewmodel.NewDetailViewModel
import com.shuange.lesson.BR
import com.shuange.lesson.base.BaseActivity
import com.shuange.lesson.base.BaseItemBean
import com.shuange.lesson.base.viewmodel.BaseShareModelFactory
import kotlinx.android.synthetic.main.layout_header.view.*

/**
 * 英语资讯详情
 */
class NewsDetailActivity :
    BaseActivity<ActivityNewsDetailBinding, NewDetailViewModel>() {

    companion object {
        fun start(context: Context, newsBean: BaseItemBean) {
            val i = Intent(context, NewsDetailActivity::class.java)
            i.putExtra(IntentKey.NEWS_KEY, newsBean)
            context.startActivity(i)
        }
    }

    override val viewModel: NewDetailViewModel by viewModels {
        BaseShareModelFactory()
    }

    override val layoutId: Int
        get() = R.layout.activity_news_detail
    override val viewModelId: Int
        get() = BR.newDetailViewModel

    override fun initParams() {
        super.initParams()
        (intent.getSerializableExtra(IntentKey.NEWS_KEY) as? BaseItemBean)?.let {
            viewModel.title.value = it.title
            viewModel.htmlContent.value = it.content
        }
    }

    override fun initView() {
        binding.header.title.text = viewModel.title.value
        binding.wv.loadDataWithBaseURL(
            null,
            viewModel.htmlContent.value ?: "",
            "text/html",
            "utf-8",
            null
        )
        initListener()
    }


    private fun initListener() {
        binding.header.back.setOnClickListener {
            finish()
        }
    }


    override fun initViewObserver() {
    }


}