package com.meten.xyh.modules.usersetting.adapter

import android.view.View
import androidx.databinding.ObservableArrayList
import com.meten.xyh.BR
import com.meten.xyh.R
import com.meten.xyh.modules.usersetting.bean.UserSettingBean
import com.shuange.lesson.base.adapter.BaseListAdapter

class BaseUserSettingAdapter(layout: Int, data: ObservableArrayList<UserSettingBean>?) :
    BaseListAdapter<UserSettingBean>(layout, data) {
    override fun convert(helper: ListViewHolder?, item: UserSettingBean?) {
        helper?.getView<View>(R.id.contentTv)?.isSelected = item?.isSelected ?: false
        helper?.binding?.setVariable(BR.userSettingBean, item)
    }
}