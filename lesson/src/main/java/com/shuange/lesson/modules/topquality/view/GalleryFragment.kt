package com.shuange.lesson.modules.topquality.view

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener
import com.shuange.lesson.BR
import com.shuange.lesson.R
import com.shuange.lesson.base.BaseFragment
import com.shuange.lesson.base.config.IntentKey
import com.shuange.lesson.base.viewmodel.BaseShareModelFactory
import com.shuange.lesson.databinding.FragmentGalleryBinding
import com.shuange.lesson.modules.media.bean.VideoData
import com.shuange.lesson.modules.media.view.VideoGalleryActivity
import com.shuange.lesson.modules.topquality.adapter.GalleryAdapter
import com.shuange.lesson.modules.topquality.viewmodel.GalleryViewModel

/**
 * Gallery grid
 */
class GalleryFragment :
    BaseFragment<FragmentGalleryBinding, GalleryViewModel>() {

    companion object {
        fun newInstance(shortVideoType: String): GalleryFragment {
            val f = GalleryFragment()
            Bundle().apply {
                putString(IntentKey.SHORT_VIDEO_TYPE, shortVideoType)
                f.arguments = this
            }
            return f
        }

        var currentVideos = mutableListOf<VideoData>()
    }

    private val galleryAdapter: GalleryAdapter by lazy {
        GalleryAdapter(
            data = viewModel.galleryItems
        )
    }

    override fun initParams() {
        super.initParams()
        viewModel.shortVideoType = arguments?.getString(IntentKey.SHORT_VIDEO_TYPE)
    }

    override fun initView() {
        viewModel.loadData()
        initGallery()
        initListeners()
    }

    private fun initListeners() {
        binding.srl.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
            override fun onLoadMore(refreshLayout: RefreshLayout) {
                viewModel.loadGalleries {
                    binding.srl.finishLoadMore()
                }

            }

            override fun onRefresh(refreshLayout: RefreshLayout) {
                viewModel.loadGalleries("0") {
                    binding.srl.finishRefresh()
                }

            }
        })
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
                val item = galleryAdapter.data[position]
                VideoGalleryActivity.start(requireContext(), item.gid)
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

    override fun onDestroy() {
        super.onDestroy()
        currentVideos.clear()
    }
}