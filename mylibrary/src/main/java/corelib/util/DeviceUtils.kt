package corelib.util

import android.content.Context
import android.graphics.Point
import android.graphics.Rect
import android.os.Build
import android.view.Window
import android.view.WindowManager

class DeviceUtils {

    companion object {

        val screenShortLength: Int
            get() = shortLengthPX ?: 0

        private var shortLengthPX: Int? = null

        /**
         * 端末の短辺に対する割合で長さを取得する
         */
        fun vminLength(context: Context = ContextManager.getContext(), rate: Double): Double {
            return shortLengthPX(context) * rate
        }

        /**
         * 端末の長辺に対する割合で長さを取得する
         */
        fun vhLength(context: Context = ContextManager.getContext(), rate: Double): Double {
            return longLengthPX(context) * rate
        }

        fun toPx(context: Context = ContextManager.getContext(), dp: Int): Int {
            return (dp * context.resources.displayMetrics.density).toInt()
        }

        fun toPx(context: Context = ContextManager.getContext(), dp: Double): Int {
            return (dp * context.resources.displayMetrics.density).toInt()
        }

        fun toDp(context: Context = ContextManager.getContext(), px: Int): Int {
            return (px / context.resources.displayMetrics.density).toInt()
        }

        /**
         * 端末の短辺を[PX]で返す
         */
        @Suppress("KDocUnresolvedReference", "DEPRECATION")
        private fun shortLengthPX(context: Context = ContextManager.getContext()): Int {
            val result = shortLengthPX
            if (result != null) return result

            val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val display = windowManager.defaultDisplay
            val pxSize: Int
            pxSize = if (17 <= Build.VERSION.SDK_INT) {
                val size = Point()
                display.getRealSize(size)
                Math.min(size.x, size.y)
            } else {
                Math.min(display.width, display.height)
            }

            if (0 < pxSize) {
                shortLengthPX = pxSize
            }
            return pxSize
        }

        /**
         * 端末の短辺を[PX]で返す
         */
        @Suppress("KDocUnresolvedReference")
        private fun longLengthPX(context: Context): Int {
            val displayRealSize = displayRealSize(context)
            return Math.max(displayRealSize.x, displayRealSize.y)
        }


        /**
         * 端末の短辺を[DP]で返す
         */
        @Suppress("KDocUnresolvedReference")
        fun shortLengthDP(context: Context): Float {
            return shortLengthPX(context) / context.resources.displayMetrics.density
        }

        /**
         * ディスプレイのサイズを返す
         */
        @Suppress("DEPRECATION")
        private fun displayRealSize(context: Context): Point {
            val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val display = windowManager.defaultDisplay
            return if (17 <= Build.VERSION.SDK_INT) {
                val size = Point()
                display.getSize(size)
                size
            } else {
                Point(display.width, display.height)
            }
        }

        /**
         * ソフトウェアボタン部分の高さを返す
         */
        fun softwareKeyButtonsArea(window: Window): Point {
            val windowSize = Rect()
            window.decorView.getWindowVisibleDisplayFrame(windowSize)
            val displaySize = displayRealSize(context = window.context)
            val width = displaySize.x - Math.abs(windowSize.width())
            val height = displaySize.y - Math.abs(windowSize.height())
            return Point(width, height)
        }
    }
}