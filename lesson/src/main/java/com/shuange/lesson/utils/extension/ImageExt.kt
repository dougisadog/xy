package com.shuange.lesson.utils.extension

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.shuange.lesson.R
import com.shuange.lesson.modules.lesson.bean.LessonBean
import com.shuange.lesson.modules.lesson.bean.Selection
import com.shuange.lesson.modules.lesson.bean.SourceData
import com.shuange.lesson.utils.GlideUtil
import corelib.util.ContextManager
import corelib.util.DeviceUtils

fun ImageView.setCenterImage(lessonBean: LessonBean) {
    setSource(lessonBean.img)
}

fun ImageView.setSource(sourceData: SourceData?) {
    Glide.with(ContextManager.getContext()).load(sourceData?.getSource())
//        .apply(GlideUtil.getRoundCornersTransformer(24))
        .placeholder(drawable).into(this)
}

fun ImageView.setSelectionImage(selection: Selection) {
    setSource(selection.img)
}