package com.meten.xyh.modules.user.viewmodel

import androidx.lifecycle.MutableLiveData
import com.meten.xyh.base.DataCache
import com.meten.xyh.modules.user.bean.UserBean
import com.shuange.lesson.base.LessonDataCache
import com.shuange.lesson.base.viewmodel.BaseViewModel

class UserAccountViewModel : BaseViewModel() {

    val subUser = MutableLiveData<UserBean>(DataCache.currentUser())

    val account = LessonDataCache.account


}