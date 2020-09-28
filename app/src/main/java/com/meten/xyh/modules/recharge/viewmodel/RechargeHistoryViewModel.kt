package com.meten.xyh.modules.recharge.viewmodel

import androidx.databinding.ObservableArrayList
import com.meten.xyh.enumeration.PayType
import com.meten.xyh.modules.recharge.bean.RechargeHistoryBean
import com.meten.xyh.service.api.TeachersApi
import com.shuange.lesson.EmptyTask
import com.shuange.lesson.base.viewmodel.BaseViewModel
import com.shuange.lesson.service.api.base.suspendExecute

class RechargeHistoryViewModel : BaseViewModel() {

    val rechargeHistoryItems = ObservableArrayList<RechargeHistoryBean>()


    fun loadRechargeHistories(
        startId: String = rechargeHistoryItems.lastOrNull()?.id ?: "0",
        page: Int = 50,
        onFinished: EmptyTask = null
    ) {
        startBindLaunch(onFinish = onFinished) {
            val suspendResult = TeachersApi().suspendExecute()
//            suspendResult.getResponse()?.body?.forEach {
//                teachers.add(TeacherBean().apply {
//                    setTeacher(it)
//                })
//            }
            suspendResult.exception
        }
        testData()
    }

    fun testData() {
        val baseImg =
            "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=3844276591,3933131866&fm=26&gp=0.jpg"
        for (i in 0 until 6) {
            rechargeHistoryItems.add(RechargeHistoryBean().apply {
                id = i.toString()
                xyValue = i * 10
                dateTime = "2020-10-${i}"
                payType = if (i % 2 == 0) PayType.ALIPAY else PayType.WX
            })
        }
    }

}