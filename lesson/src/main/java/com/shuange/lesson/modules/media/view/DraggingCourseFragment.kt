package com.shuange.lesson.modules.media.view

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.databinding.ObservableArrayList
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.shuange.lesson.R
import com.shuange.lesson.modules.topquality.adapter.TopQualityAdapter
import com.shuange.lesson.modules.topquality.bean.CourseBean
import corelib.util.DeviceUtils


class DraggingCourseFragment(val cours: ObservableArrayList<CourseBean>) :
    BottomSheetDialogFragment() {

    private val topQualityAdapter: TopQualityAdapter by lazy {
        TopQualityAdapter(
            layout = R.layout.layout_media_course_item,
            data = cours
        )
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
            topQualityAdapter.setOnItemClickListener { adapter, view, position ->
                com.shuange.lesson.utils.ToastUtil.show("item click  topQuality:${topQualityAdapter.data[position].title}")
            }
//            isNestedScrollingEnabled = false
            adapter = topQualityAdapter
        }
    }

}