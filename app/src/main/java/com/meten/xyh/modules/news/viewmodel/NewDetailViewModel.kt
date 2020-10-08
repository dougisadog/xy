package com.meten.xyh.modules.news.viewmodel

import androidx.lifecycle.MutableLiveData
import com.shuange.lesson.base.viewmodel.BaseViewModel

class NewDetailViewModel : BaseViewModel() {

    var htmlContent = MutableLiveData<String>()

    var title = MutableLiveData<String>()

    fun loadData() {

    }
}