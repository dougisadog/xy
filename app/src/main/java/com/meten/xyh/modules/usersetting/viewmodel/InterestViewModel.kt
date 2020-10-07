package com.meten.xyh.modules.usersetting.viewmodel

import androidx.databinding.ObservableArrayList
import com.meten.xyh.base.DataCache
import com.meten.xyh.modules.usersetting.bean.InterestBean
import com.shuange.lesson.base.viewmodel.BaseViewModel

open class InterestViewModel : BaseViewModel() {

    val interests = ObservableArrayList<InterestBean>()
    fun loadData() {
        testData()
    }

    fun getResults(): MutableList<InterestBean> {
        return interests.filter { it.isSelected }.toMutableList()
    }

    fun saveSetting() {

        val target = StringBuilder()
        getResults().forEachIndexed { index, interestBean ->
            if (index != 0) {
                target.append(",")
            }
            target.append(interestBean.text)
        }
        DataCache.users.first {
            it.current
        }.let {
            it.subUser?.interest = target.toString()
        }
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