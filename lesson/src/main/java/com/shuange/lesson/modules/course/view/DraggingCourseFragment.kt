package com.shuange.lesson.modules.course.view

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.databinding.ObservableArrayList
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.shuange.lesson.R
import com.shuange.lesson.base.viewmodel.BaseShareModelFactory
import com.shuange.lesson.modules.course.adapter.DraggingCourseListAdapter
import com.shuange.lesson.modules.course.bean.DraggingCourseBean
import com.shuange.lesson.modules.course.viewmodel.VideoCourseViewModel
import corelib.util.DeviceUtils


class DraggingCourseFragment(
    val courses: ObservableArrayList<DraggingCourseBean>
) :
    BottomSheetDialogFragment() {

    private val draggingCourseListAdapter: DraggingCourseListAdapter by lazy {
        DraggingCourseListAdapter(
            layout = R.layout.layout_media_course_item,
            data = courses
        )
    }

    private val videoCourseViewModel: VideoCourseViewModel by activityViewModels {
        BaseShareModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.layout_media_course, container, false)
        initViews(view)
        return view
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val dialog = dialog as BottomSheetDialog
        val bottomSheet = dialog.delegate.findViewById<FrameLayout>(R.id.design_bottom_sheet)
        bottomSheet?.let {
            val behavior = BottomSheetBehavior.from(it)
            it.layoutParams.height = DeviceUtils.toPx(requireContext(), 304)
        }
    }

    fun initViews(view: View) {
        with(view.findViewById<RecyclerView>(R.id.rv)) {
            layoutManager = androidx.recyclerview.widget.LinearLayoutManager(
                requireContext(),
                RecyclerView.VERTICAL,
                false
            )
            draggingCourseListAdapter.setOnItemClickListener { adapter, view, position ->
                val data = draggingCourseListAdapter.data[position]
                val isFree = data.isFree
//                com.shuange.lesson.utils.ToastUtil.show("item click  topQuality:${topQualityAdapter.data[position].title}")
                if (isFree == true) {
                    videoCourseViewModel.resetMedia(position)
                } else {
                    videoCourseViewModel.buyCourse.value = data.id
                }
            }
//            isNestedScrollingEnabled = false
            adapter = draggingCourseListAdapter
        }
    }

}