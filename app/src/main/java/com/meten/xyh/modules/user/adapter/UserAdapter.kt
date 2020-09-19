package com.meten.xyh.modules.user.adapter

import androidx.databinding.ObservableArrayList
import com.meten.xyh.BR
import com.meten.xyh.R
import com.meten.xyh.modules.user.bean.UserBean
import com.shuange.lesson.base.adapter.BaseListAdapter

class UserAdapter(data: ObservableArrayList<UserBean>?) :
    BaseListAdapter<UserBean>(R.layout.layout_user_item, data) {
    override fun convert(helper: ListViewHolder?, item: UserBean?) {
        helper?.binding?.setVariable(BR.userBean, item)
    }
}