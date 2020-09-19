package com.meten.xyh.modules.main.viewmodel

import androidx.lifecycle.MutableLiveData
import com.shuange.lesson.base.viewmodel.BaseViewModel

open class ShareViewModel : BaseViewModel() {

    val shareValue = MutableLiveData<String>("share")

}