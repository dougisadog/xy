package corelib.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Rect
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Build
import android.view.View
import com.sbi.base.mylibrary.BuildConfig
import corelib.extension.check
import java.io.IOException


class OSUtils {

    companion object {
        fun openUrl(urlStr: String) {
            var intent: Intent = Intent(Intent.ACTION_VIEW, Uri.parse(urlStr))
            ContextManager.getContext()?.startActivity(intent)
        }

        fun openPlayStore(activity: Activity, packageName: String) {
            try {
                activity.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + packageName)))
            } catch (anfe: android.content.ActivityNotFoundException) {
                activity.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + packageName)))
            }
        }

        fun launchExternalApp(activity: Activity, packageName: String) {
            val manager = activity.packageManager
            try {
                val intent = manager.getLaunchIntentForPackage(packageName)
                activity.startActivity(intent)
            } catch (e: Exception) {
                openPlayStore(activity, packageName)
            }
        }

        /**
         * assets ディレクトリよりファイルを読み込む
         * 引数の AssetManager は Context.assets より取得可能
         */
        @Throws(IOException::class)
        fun readAssetString(context: Context, fileName: String): String {
            return context.assets.open(fileName).bufferedReader().use {
                it.readText()
            }
        }

        fun appVersion(): String = BuildConfig.VERSION_NAME

        fun getStatusBarHeight(context: Activity): Int {
            val rect = Rect()
            val window = context.window
            window.decorView.getWindowVisibleDisplayFrame(rect)
            return rect.top
        }

        fun setOrientation(activity: Activity, orientation: Int) {
            activity.setRequestedOrientation(orientation);
        }

        fun getOrientation(activity: Activity): Int {
            return activity.requestedOrientation
        }

        fun isPortrait(activity: Activity): Boolean {
            return activity.requestedOrientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }

        fun isNetworkEnabled(context: Context): Boolean {
            val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
            val networkInfo = manager?.activeNetworkInfo
            return networkInfo?.isConnected.check
        }

        private var id = 1000000000
        fun generateViewId(): Int {
            if (Build.VERSION.SDK_INT < 17) return id++
            return View.generateViewId()
        }
    }
}