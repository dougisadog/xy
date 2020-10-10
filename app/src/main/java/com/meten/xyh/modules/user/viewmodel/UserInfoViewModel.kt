package com.meten.xyh.modules.user.viewmodel

import androidx.lifecycle.MutableLiveData
import com.meten.xyh.base.DataCache
import com.meten.xyh.service.response.bean.SubUser
import com.shuange.lesson.base.viewmodel.BaseViewModel
import com.shuange.lesson.modules.lesson.bean.SourceData

class UserInfoViewModel : BaseViewModel() {

    var user = MutableLiveData<SubUser>(DataCache.currentUser()?.subUser)

    var headerImg = MutableLiveData<SourceData>()

    fun loadData() {

    }
}