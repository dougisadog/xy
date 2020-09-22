package com.meten.xyh.modules.user.viewmodel

import androidx.databinding.ObservableArrayList
import androidx.lifecycle.MutableLiveData
import com.meten.xyh.base.DataCache
import com.meten.xyh.base.bean.ActionItem
import com.shuange.lesson.base.viewmodel.BaseViewModel
import com.shuange.lesson.modules.lesson.bean.SourceData
import com.shuange.lesson.utils.ToastUtil

class UserInfoViewModel : BaseViewModel() {

    val actionItems = ObservableArrayList<ActionItem>()

    var headerImg = MutableLiveData<SourceData>()

    fun loadData() {
        val user =DataCache.currentUser()
        val actions = arrayListOf<ActionItem>()
        actions.add(ActionItem().apply {
            title = "昵称"
            value = user?.userName?:""
            action = { ToastUtil.show("昵称") }
        })
        actions.add(ActionItem().apply {
            title = "个性签名"
            value = user?.introduction?:""
            action = { ToastUtil.show("个性签名") }
        })
        actions.add(ActionItem().apply {
            title = "学习阶段"
            value = user?.subUser?.stage?:""
            action = { ToastUtil.show("学习阶段") }
        })
        actions.add(ActionItem().apply {
            title = user?.subUser?.interest?:""
            action = { ToastUtil.show("感兴趣的") }
        })
        actions.add(ActionItem().apply {
            title = "需提升的"
            action = { ToastUtil.show("需提升的") }
        })
        actions.add(ActionItem().apply {
            title = "上课提醒手机"
            value = user?.subUser?.phone?:""
            action = { ToastUtil.show("上课提醒手机") }
        })
        actions.add(ActionItem().apply {
            title = "登录密码"
            value = "修改"
            action = { ToastUtil.show("登录密码") }
        })
        actionItems.addAll(actions)
    }
}