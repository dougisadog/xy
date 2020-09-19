package corelib.versionCheck

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import corelib.util.ContextManager

object ApkInfo {

    var packageName: String? = null
    var iconId: Int = 0
    var iconDrawable: Drawable? = null
    var programName: String? = null
    var versionCode: Int = 0
    var versionName: String? = null
    var width: Int = 0
    var height: Int = 0
    var dpi: Int = 0
    var screenWidth: Int = 0
    var screenHeight: Int = 0

    init {
        val context: Context = ContextManager.getContext()!!
        val applicationInfo = context.applicationInfo
        this.packageName = applicationInfo.packageName
        this.iconId = applicationInfo.icon
        this.iconDrawable = context.resources.getDrawable(this.iconId)
        this.programName = context.resources
            .getText(applicationInfo.labelRes).toString()
        var packageInfo: PackageInfo? = null
        try {
            packageInfo = context.packageManager.getPackageInfo(
                this.packageName.toString(), 0
            )
            this.versionCode = packageInfo!!.versionCode
            this.versionName = packageInfo.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
    }


}