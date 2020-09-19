package com.meten.xyh.modules.step.viewmodel

import androidx.databinding.ObservableArrayList
import com.meten.xyh.modules.step.bean.StepBean
import com.shuange.lesson.base.viewmodel.BaseViewModel

open class StepViewModel : BaseViewModel() {

    val type = 0

    val steps = ObservableArrayList<StepBean>()

    init {
        steps.add(StepBean().apply { title = "幼儿（学前儿童）" })
        steps.add(StepBean().apply { title = "小学低年组（1-3年级）" })
        steps.add(StepBean().apply { title = "小学高年组（4-6年级）" })
        steps.add(StepBean().apply { title = "初中" })
        steps.add(StepBean().apply { title = "高中" })
        steps.add(StepBean().apply { title = "大学成人" })
        steps.add(StepBean().apply { title = "我是教师" })

    }


}