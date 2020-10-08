package com.meten.xyh.modules.usersetting.viewmodel

import androidx.databinding.ObservableArrayList
import com.meten.xyh.base.DataCache
import com.meten.xyh.modules.usersetting.bean.InterestBean
import com.meten.xyh.service.response.bean.SubUser
import com.shuange.lesson.base.viewmodel.BaseViewModel

open class InterestViewModel : BaseViewModel() {

    val interests = ObservableArrayList<InterestBean>()
    fun loadData() {
        testData()
    }

    fun getResults(): MutableList<InterestBean> {
        return interests.filter { it.isSelected }.toMutableList()
    }

    fun saveSetting(
        user: SubUser?
    ) {
        val targetUser = user ?: DataCache.users.firstOrNull {
            it.current
        }?.subUser
        targetUser?.interest = getInterest()
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

    //TODO
    fun testData() {
        for (i in 0 until 7) {
            interests.add(InterestBean().apply {
                text = "旅游英语$i"
                isSelected = false
            })
        }
    }

}