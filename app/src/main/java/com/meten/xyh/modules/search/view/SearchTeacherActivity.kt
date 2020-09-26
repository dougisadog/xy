package com.meten.xyh.modules.search.view

import androidx.activity.viewModels
import com.meten.xyh.R
import com.meten.xyh.modules.discovery.adapter.TeacherAdapter
import com.meten.xyh.modules.search.viewmodel.SearchTeacherViewModel
import com.meten.xyh.modules.teacher.view.TeacherInfoActivity
import com.shuange.lesson.base.adapter.BaseListAdapter
import com.shuange.lesson.base.viewmodel.BaseShareModelFactory


/**
 * 搜索老师
 */
class SearchTeacherActivity :
    SearchBaseActivity<SearchTeacherViewModel>() {

    private val teacherAdapter: TeacherAdapter by lazy {
        TeacherAdapter(
            layout = R.layout.layout_teacher_list_item,
            data = viewModel.teachers
        )
    }

    override val viewModel: SearchTeacherViewModel by viewModels {
        BaseShareModelFactory()
    }

    override fun getSearchAdapter(): BaseListAdapter<*> {
        return teacherAdapter
    }

    override fun onItemClick(id: String) {
        TeacherInfoActivity.start(this, id)
    }


}