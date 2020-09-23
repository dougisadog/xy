package com.meten.xyh.modules.user.viewmodel

import androidx.databinding.ObservableArrayList
import androidx.lifecycle.MutableLiveData
import com.meten.xyh.base.DataCache
import com.meten.xyh.base.bean.ActionItem
import com.shuange.lesson.base.viewmodel.BaseViewModel
import com.shuange.lesson.modules.lesson.bean.SourceData
import com.shuange.lesson.utils.ToastUtil

class UserInfoViewModel : BaseViewModel() {

    val actionItems = ObservableArrayList<ActionItem>()

    var headerImg = MutableLiveData<SourceData>()

    fun loadData() {

    }
}