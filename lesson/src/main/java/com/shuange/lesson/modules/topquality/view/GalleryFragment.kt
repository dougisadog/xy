package com.shuange.lesson.modules.topquality.view

import android.graphics.Rect
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.shuange.lesson.BR
import com.shuange.lesson.R
import com.shuange.lesson.base.BaseFragment
import com.shuange.lesson.base.viewmodel.BaseShareModelFactory
import com.shuange.lesson.databinding.FragmentGalleryBinding
import com.shuange.lesson.modules.topquality.adapter.GalleryAdapter
import com.shuange.lesson.modules.topquality.viewmodel.GalleryViewModel
import com.shuange.lesson.utils.ToastUtil

/**
 * Gallery grid
 */
class GalleryFragment :
    BaseFragment<FragmentGalleryBinding, GalleryViewModel>() {

    private val galleryAdapter: GalleryAdapter by lazy {
        GalleryAdapter(
            data = viewModel.galleryItems
        )
    }

    override fun initView() {
        viewModel.loadData()
        initGallery()
    }


    override fun initViewObserver() {
    }

    fun initGallery() {
        with(binding.rv) {
            layoutManager = androidx.recyclerview.widget.GridLayoutManager(
                requireContext(),
                2
            )
            galleryAdapter.setOnItemClickListener { adapter, view, position ->
                ToastUtil.show("item click  gallery:${galleryAdapter.data[position].hearts}")
            }
            isNestedScrollingEnabled = false
            adapter = galleryAdapter
        }
    }

    override val viewModel: GalleryViewModel by viewModels {
        BaseShareModelFactory()
    }
    override val layoutId: Int
        get() = R.layout.fragment_gallery
    override val viewModelId: Int
        get() = BR.galleryViewModel
}