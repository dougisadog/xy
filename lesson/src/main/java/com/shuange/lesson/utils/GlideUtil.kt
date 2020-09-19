package com.shuange.lesson.utils

import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions


object GlideUtil {

    fun getRoundCornersTransformer(radius: Int): RequestOptions {
        // RequestOptions options = RequestOptions.bitmapTransform(roundedCorners).override(300, 300);
        val roundedCorners = RoundedCorners(radius)
        return RequestOptions.bitmapTransform(roundedCorners)
    }
}