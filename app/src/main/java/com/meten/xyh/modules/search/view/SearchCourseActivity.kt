package com.meten.xyh.modules.search.view

import androidx.activity.viewModels
import com.meten.xyh.R
import com.meten.xyh.modules.search.viewmodel.SearchCourseViewModel
import com.shuange.lesson.base.adapter.BaseListAdapter
import com.shuange.lesson.base.viewmodel.BaseShareModelFactory
import com.shuange.lesson.modules.topquality.adapter.TopQualityAdapter
import com.shuange.lesson.utils.BusinessUtil


/**
 * 搜索课程
 */
class SearchCourseActivity :
    SearchBaseActivity<SearchCourseViewModel>() {

    private val courseAdapter: TopQualityAdapter by lazy {
        TopQualityAdapter(
            layout = R.layout.layout_top_quality_item_for_search,
            data = viewModel.courses
        )
    }

    override val viewModel: SearchCourseViewModel by viewModels {
        BaseShareModelFactory()
    }

    override fun getSearchAdapter(): BaseListAdapter<*> {
        return courseAdapter
    }

    override fun onItemClick(id: String) {
        viewModel.getLessonPackageDetail(id) {
            BusinessUtil.startCourse(this, it)
        }
    }


}