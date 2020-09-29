package com.shuange.lesson.utils.extension

import androidx.viewpager2.widget.ViewPager2
import com.shuange.lesson.view.navigator.BaseNavigator
import com.shuange.lesson.view.navigator.MyCommonNavigator
import net.lucode.hackware.magicindicator.MagicIndicator

fun MagicIndicator.bind(
    viewPager2: ViewPager2,
    dataList: MutableList<String>,
    callBack: ViewPager2.OnPageChangeCallback? = null,
    defaultNavigator: BaseNavigator? = MyCommonNavigator(dataList, viewPager2)
) {
    this.navigator = defaultNavigator
    viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
        override fun onPageScrollStateChanged(state: Int) {
            callBack?.onPageScrollStateChanged(state)
            this@bind.onPageScrollStateChanged(state)
        }

        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
            callBack?.onPageScrolled(position, positionOffset, positionOffsetPixels)
            this@bind.onPageScrolled(position, positionOffset, positionOffsetPixels)
        }

        override fun onPageSelected(position: Int) {
            callBack?.onPageSelected(position)
            this@bind.onPageSelected(position)
        }
    })
}
