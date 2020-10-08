package com.meten.xyh.modules.usersetting.viewmodel

import androidx.databinding.ObservableArrayList
import com.meten.xyh.modules.usersetting.bean.UserSettingBean
import com.meten.xyh.service.response.bean.SubUser
import com.shuange.lesson.base.viewmodel.BaseViewModel

abstract class BaseUserSettingViewModel : BaseViewModel() {

    val type = 0

    val userSettingItems = ObservableArrayList<UserSettingBean>()

    abstract fun loadData()

    abstract fun saveSetting(text:String, user:SubUser?)

}