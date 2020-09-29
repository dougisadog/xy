package com.shuange.lesson.view.navigator

import android.annotation.SuppressLint
import android.content.Context
import android.view.Gravity
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.shuange.lesson.R
import corelib.util.DeviceUtils
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.CommonPagerTitleView

@SuppressLint("ViewConstructor")
class MyCommonNavigator(
    dataList: MutableList<String>,
    viewPager2: ViewPager2
) : BaseNavigator(viewPager2.context) {

    init {
        this.isAdjustMode = true
        this.adapter = object : CommonNavigatorAdapter() {
            override fun getCount(): Int {
                return dataList.size
            }

            override fun getTitleView(context: Context, index: Int): IPagerTitleView {
                val titleView = CommonPagerTitleView(context)

                val context = viewPager2.context
                val tv = TextView(context)
                tv.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                tv.gravity = Gravity.CENTER
                tv.text = dataList[index]
                tv.setTextColor(ContextCompat.getColor(context, R.color.hex_2FD393))
                tv.textSize = 18f
                titleView.setContentView(tv)
                titleView.onPagerTitleChangeListener = object :
                    CommonPagerTitleView.OnPagerTitleChangeListener {
                    override fun onSelected(index: Int, totalCount: Int) {
                        tv.textSize = 18f
                        tv.setTextColor(ContextCompat.getColor(context, R.color.hex_30D393))

                    }

                    override fun onDeselected(index: Int, totalCount: Int) {
                        tv.textSize = 14f
                        tv.setTextColor(ContextCompat.getColor(context, R.color.hex_2B2E35))
                    }

                    override fun onLeave(
                        index: Int,
                        totalCount: Int,
                        leavePercent: Float,
                        leftToRight: Boolean
                    ) {
                    }

                    override fun onEnter(
                        index: Int,
                        totalCount: Int,
                        enterPercent: Float,
                        leftToRight: Boolean
                    ) {
                    }
                }

                titleView.setOnClickListener {
                    viewPager2.currentItem = index
                }
                return titleView
            }

            override fun getIndicator(context: Context): IPagerIndicator {
                val indicator = LinePagerIndicator(context)
                indicator.mode = LinePagerIndicator.MODE_EXACTLY
                indicator.lineHeight = DeviceUtils.toPx(context,2).toFloat()
                indicator.lineWidth = DeviceUtils.toPx(context,24).toFloat()
                indicator.setColors(ContextCompat.getColor(context, R.color.hex_00E0A4))
                return indicator
            }

            override fun getTitleWeight(context: Context, index: Int): Float {
                return 1.0f
            }
        }
    }
}

