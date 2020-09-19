package com.shuange.lesson.view.gallery

import android.view.View
import androidx.viewpager2.widget.ViewPager2

class GalleryPageTransformer : ViewPager2.PageTransformer {
    override fun transformPage(page: View, position: Float) {
        val scaleFactor = Math.max(min_scale, 1 - Math.abs(position))
        val rotate = 20 * Math.abs(position)
        if (position < -1) {
        } else if (position < 0) {
            page.setScaleX(scaleFactor)
            page.setScaleY(scaleFactor)
            page.setRotationY(rotate)
        } else if (position >= 0 && position < 1) {
            page.setScaleX(scaleFactor)
            page.setScaleY(scaleFactor)
            page.setRotationY(-rotate)
        } else if (position >= 1) {
            page.setScaleX(scaleFactor)
            page.setScaleY(scaleFactor)
            page.setRotationY(-rotate)
        }
    }

    companion object {
        private const val min_scale = 0.85f
    }
}