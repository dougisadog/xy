package com.shuange.lesson.base.adapter

import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import java.util.*

class RecyclePagerAdapter<T> :
    FragmentStateAdapter {
    val fragmentCreator: (T) -> Fragment
    val data: MutableList<T>

    constructor(activity: FragmentActivity, data: MutableList<T>, fragmentCreator: (T) -> Fragment) : super(activity) {
        this.fragmentCreator = fragmentCreator
        this.data = data
    }

    constructor(fragment: Fragment, data: MutableList<T>, fragmentCreator: (T) -> Fragment) : super(fragment) {
        this.fragmentCreator = fragmentCreator
        this.data = data
    }

    override fun getItemCount(): Int {
        return if (data.size > 1) data.size + 2 else 0
    }

    fun getDataCount(): Int {
        return data.size
    }

    override fun createFragment(position: Int): Fragment {
        var realTarget = position - 1
        when {
            position == itemCount - 1 -> {
                realTarget = 0
            }
            position == 0 -> {
                realTarget = itemCount - 3
            }
        }
        realTarget = Math.max(0 , realTarget)
        realTarget = Math.min(data.size - 1 , realTarget)
        return fragmentCreator.invoke(data[realTarget])
    }

}

fun ViewPager2.setRecycleAdapter(adapter: RecyclePagerAdapter<*>) {
    if (adapter.data.size <= 1) {
        this.adapter = adapter
        return
    }
    val currentAdapter = adapter
    this.adapter = currentAdapter
    //make sure all fragments are loaded
    offscreenPageLimit = currentAdapter.getDataCount() - 1
    registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            //check the scroll state is staying
            if (positionOffset != 0f || positionOffsetPixels != 0) return
            if (position == 0) {
                setCurrentItem(currentAdapter.itemCount - 2, false)
            }
            if (position == currentAdapter.itemCount - 1) {
                setCurrentItem(1, false)
            }
        }
    })
    setRecycleCurrentItem(0, false)
}

fun ViewPager2.registerRecycleOnPageChangeCallback(onPageSelected:(Int)->Unit) {
    onPageSelected.invoke(0)
    val currentAdapter = this.adapter as? RecyclePagerAdapter<*> ?: return
    registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            var target = position - 1
            if (position == 0) {
                target = currentAdapter.getDataCount() - 1
            } else if (position == currentAdapter.itemCount - 1) {
                target = 0
            }
            onPageSelected.invoke(target)
        }
    })
}

fun ViewPager2.setRecycleCurrentItem(position: Int, smoothScroll: Boolean = true) {
    if (this.adapter is RecyclePagerAdapter<*>) {
        val currentAdapter = this.adapter as RecyclePagerAdapter<*>
        when {
            position < 0 || position > currentAdapter.data.size - 1 -> {
                return
            }
            else -> {
                setCurrentItem(position + 1, smoothScroll)
            }
        }
    } else {
        setCurrentItem(position, smoothScroll)
    }
}

fun ViewPager2.getRecycleCurrentItem(): Int {
    if (this.adapter is RecyclePagerAdapter<*>) {
        val currentAdapter = this.adapter as RecyclePagerAdapter<*>
        when {
            currentItem < 0 || currentItem > currentAdapter.itemCount - 1 -> {
                throw IndexOutOfBoundsException()
            }
            currentItem == currentAdapter.itemCount - 1 -> {
                return 0
            }
            currentItem == 0 -> {
                return currentAdapter.data.size - 1
            }
            else -> {
                return currentItem - 1
            }
        }
    } else {
        return currentItem
    }
}

fun ViewPager2.starAuto(delay: Long = 1000L, period: Long = 1000L) {
    val count = adapter?.itemCount ?: return
    val timer = Timer()
    timer.schedule(object : TimerTask() {
        override fun run() {
            val target = currentItem
            if (target < count && scrollState != ViewPager2.SCROLL_STATE_DRAGGING) {
                Handler(Looper.getMainLooper()).post {
                    currentItem = target + 1
                }
            }
        }
    }, delay, period)
}