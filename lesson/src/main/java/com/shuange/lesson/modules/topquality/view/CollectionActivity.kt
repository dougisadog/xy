package com.shuange.lesson.modules.topquality.view

import androidx.activity.viewModels
import com.shuange.lesson.BR
import com.shuange.lesson.R
import com.shuange.lesson.base.BaseActivity
import com.shuange.lesson.base.viewmodel.BaseShareModelFactory
import com.shuange.lesson.base.viewmodel.BaseViewModel
import com.shuange.lesson.databinding.ActivityCollectionBinding
import com.shuange.lesson.modules.topquality.viewmodel.TopQualityViewModel
import com.shuange.lesson.view.NonDoubleClickListener
import kotlinx.android.synthetic.main.layout_header.view.*

class CollectionActivity : BaseActivity<ActivityCollectionBinding, BaseViewModel>() {

    override val viewModel: TopQualityViewModel by viewModels {
        BaseShareModelFactory()
    }

    override val layoutId: Int
        get() = R.layout.activity_collection

    override var fragmentContainerId: Int? = R.id.fragmentContainerFl

    override fun initView() {
        binding.header.title.text = "收藏"
        initListener()
        showFragment(GalleryFragment())
    }


    private fun initListener() {
        binding.header.back.setOnClickListener(NonDoubleClickListener {
            finish()
        })
    }

    override fun initViewObserver() {
    }


}