package corelib.util

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.util.DisplayMetrics
import corelib.versionCheck.ApkInfo


object ApplicationUtil {


    /**
     * 通过包名获取应用程序的名称。
     *
     * @param packageName
     * 包名。
     * @return 返回包名所对应的应用程序的名称。
     */
    fun getProgramNameByPackageName(
        ctx: Context,
        packageName: String?
    ): String? {
        val pm: PackageManager = ctx.getPackageManager()
        var name: String? = null
        try {
            name = pm.getApplicationLabel(
                pm.getApplicationInfo(
                    packageName!!,
                    PackageManager.GET_META_DATA
                )
            ).toString()
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return name
    }

    /**
     * 获取有关本程序的信息。
     *
     * @return 有关本程序的信息。
     */
    fun setApkInfo(ctx: Context) {
        val applicationInfo: ApplicationInfo = ctx.applicationInfo
        ApkInfo.packageName = applicationInfo.packageName
        ApkInfo.iconId = applicationInfo.icon
        ApkInfo.iconDrawable = ctx.resources.getDrawable(ApkInfo.iconId, null)
        ApkInfo.programName = ctx.resources
            .getText(applicationInfo.labelRes).toString()
        var packageInfo: PackageInfo? = null
        try {
            packageInfo = ctx.packageManager.getPackageInfo(
                ApkInfo.packageName!!, 0
            )
            ApkInfo.versionCode = packageInfo.versionCode
            ApkInfo.versionName = packageInfo.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        val displayMetrics: DisplayMetrics = ctx.getResources().getDisplayMetrics()
        ApkInfo.width = displayMetrics.widthPixels
        ApkInfo.height = displayMetrics.heightPixels
        ApkInfo.dpi = displayMetrics.densityDpi
        val density = displayMetrics.density //屏幕密度（0.75 / 1.0 / 1.5）
        //屏幕宽度算法:屏幕宽度（像素）/屏幕密度
        ApkInfo.screenWidth = (ApkInfo.width / density).toInt() //屏幕宽度(dp)
        ApkInfo.screenHeight = (ApkInfo.height / density).toInt() //屏幕高度(dp)
    }

}