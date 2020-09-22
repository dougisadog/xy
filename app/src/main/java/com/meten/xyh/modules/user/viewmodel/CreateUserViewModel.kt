package com.meten.xyh.modules.user.viewmodel

import androidx.databinding.ObservableArrayList
import com.meten.xyh.base.DataCache
import com.meten.xyh.base.bean.ActionItem
import com.meten.xyh.service.api.SubUserEditApi
import com.meten.xyh.service.response.bean.SubUser
import com.shuange.lesson.base.viewmodel.BaseViewModel
import com.shuange.lesson.service.api.base.suspendExecute
import com.shuange.lesson.utils.ToastUtil

class CreateUserViewModel : BaseViewModel() {

    val actionItems = ObservableArrayList<ActionItem>()

    fun loadData() {
        val actions = arrayListOf<ActionItem>()
        actions.add(ActionItem().apply {
            title = "姓名"
            action = { ToastUtil.show("姓名") }
        })
        actions.add(ActionItem().apply {
            title = "昵称"
            action = { ToastUtil.show("昵称") }
        })
        actions.add(ActionItem().apply {
            title = "学习阶段"
            action = { ToastUtil.show("学习阶段") }
        })
        actions.add(ActionItem().apply {
            title = "感兴趣的"
            action = { ToastUtil.show("感兴趣的") }
        })
        actions.add(ActionItem().apply {
            title = "需提升的"
            action = { ToastUtil.show("需提升的") }
        })
        actionItems.addAll(actions)
    }

    fun save(user: SubUser) {
        startBindLaunch {
            val result = SubUserEditApi(user).suspendExecute()
            result.exception
        }
    }
}