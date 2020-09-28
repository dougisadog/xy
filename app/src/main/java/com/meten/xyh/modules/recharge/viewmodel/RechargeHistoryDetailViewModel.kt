package com.meten.xyh.modules.recharge.viewmodel

import androidx.lifecycle.MutableLiveData
import com.meten.xyh.modules.recharge.bean.RechargeHistoryBean
import com.shuange.lesson.base.viewmodel.BaseViewModel

class RechargeHistoryDetailViewModel : BaseViewModel() {
    val item = MutableLiveData<RechargeHistoryBean>()
}