package com.shuange.lesson.utils.extension

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.shuange.lesson.modules.lesson.bean.QuestionBean
import com.shuange.lesson.modules.lesson.bean.Selection
import com.shuange.lesson.modules.lesson.bean.SourceData
import corelib.util.ContextManager

fun ImageView.setCenterImage(questionBean: QuestionBean) {
    setSource(questionBean.img)
}

fun ImageView.setSource(sourceData: SourceData?) {
    Glide.with(ContextManager.getContext()).load(sourceData?.getSource())
//        .apply(GlideUtil.getRoundCornersTransformer(24))
        .placeholder(drawable).into(this)
}

fun ImageView.setSelectionImage(selection: Selection) {
    setSource(selection.img)
}