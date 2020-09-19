package com.meten.xyh.modules.user.viewmodel

import androidx.databinding.ObservableArrayList
import com.meten.xyh.base.bean.ActionItem
import com.shuange.lesson.base.viewmodel.BaseViewModel

class CreateUserViewModel : BaseViewModel() {

    val actionItems = ObservableArrayList<ActionItem>()
}