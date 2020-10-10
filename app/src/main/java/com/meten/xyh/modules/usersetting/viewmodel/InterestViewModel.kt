package com.meten.xyh.modules.usersetting.viewmodel

import androidx.databinding.ObservableArrayList
import com.meten.xyh.modules.usersetting.bean.InterestBean
import com.meten.xyh.service.api.InterestApi
import com.meten.xyh.service.response.bean.SubUser
import com.shuange.lesson.base.viewmodel.BaseViewModel
import com.shuange.lesson.service.api.base.suspendExecute

open class InterestViewModel : BaseViewModel() {

    val interests = ObservableArrayList<InterestBean>()

    var default:String? = null
    fun loadData() {
        startBindLaunch {
            val suspendResult = InterestApi().suspendExecute()
            suspendResult.getResponse()?.body?.forEach {
                interests.add(InterestBean().apply {
                    text = it
                    isSelected = it == default
                })
            }
            suspendResult.exception
        }
    }

    fun getResults(): MutableList<InterestBean> {
        return interests.filter { it.isSelected }.toMutableList()
    }

    fun saveSetting(
        user: SubUser?
    ) {
        user?.interest = getInterest()
    }

    fun getInterest(): String {
        val target = StringBuilder()
        getResults().forEachIndexed { index, interestBean ->
            if (index != 0) {
                target.append(",")
            }
            target.append(interestBean.text)
        }
        return target.toString()
    }


}