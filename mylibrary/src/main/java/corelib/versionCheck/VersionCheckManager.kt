package corelib.versionCheck

import android.app.Activity
import android.content.Intent
import android.net.Uri

object VersionCheckManager {

    var packageName:String? = ApkInfo.packageName

    enum class Channel {
        GOOGLE_PLAY,
        ;
    }

    interface UpdateCallback {

        //no update
        fun onNoUpdate()

        //needUpdate
        fun needUpdate(info:VersionInfo)
    }

    fun checkVersion(updateCallback: UpdateCallback ,channel: Channel?) {
        if (null == channel || channel == VersionCheckManager.Channel.GOOGLE_PLAY) {
            checkVersionInGooglePlayStore(updateCallback)
        } else {
            updateCallback.onNoUpdate()
        }
    }

    private fun checkVersionInGooglePlayStore(updateCallback: UpdateCallback) {
        val versionChecker = VersionChecker()
        val latestVersion = versionChecker.execute().get()
        val currentVersion = ApkInfo.versionName
        val info = VersionInfo()
        info.packageName = packageName
        info.versionName = latestVersion
        if (compareVersionName(latestVersion, currentVersion) > 0) {
            updateCallback.needUpdate(info)
        }
        else {
            updateCallback.onNoUpdate()
        }
    }

    fun showGoogleForceUpdateDialog(context: Activity) {
        context.startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse("market://details?id=" + context.packageName)
            )
        )
    }

    private fun compareVersionName(v1 :String?, v2:String?) :Int {
        if (null == v1 || null == v2) return 0
        val arrV1 = v1.split(".")
        val arrV2 = v2.split(".")
        if (arrV1.size != arrV2.size) {
            return arrV1.size.compareTo(arrV2.size)
        }
        else {
            for (i in 0 .. arrV1.size) {
                if (arrV1[i] != arrV2[i]){
                    return arrV1[i].compareTo(arrV2[i])
                }
            }
        }
        return 0
    }
}


