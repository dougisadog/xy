package com.shuange.lesson.modules.topquality.adapter

import androidx.databinding.ObservableArrayList
import com.shuange.lesson.R
import com.shuange.lesson.BR
import com.shuange.lesson.base.adapter.BaseListAdapter
import com.shuange.lesson.modules.topquality.bean.GalleryItem

class GalleryAdapter(data: ObservableArrayList<GalleryItem>?) :
    BaseListAdapter<GalleryItem>(R.layout.layout_gallery_item, data) {
    override fun convert(helper: ListViewHolder?, item: GalleryItem?) {
        helper?.binding?.setVariable(BR.galleryItem, item)
    }
}