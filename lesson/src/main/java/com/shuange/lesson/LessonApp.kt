package com.shuange.lesson

import android.app.Application
import android.os.Build
import android.webkit.WebView
import com.youdao.sdk.app.YouDaoApplication
import corelib.util.ApplicationUtil
import corelib.util.ContextManager
import corelib.util.Log
import io.realm.Realm
import io.realm.RealmConfiguration

open class LessonApp: Application() {

    override fun onCreate() {
        super.onCreate()
        ContextManager.init(this)
        initRealm()
        ApplicationUtil.setApkInfo(this)
        //真机限定
//        try {
//            YouDaoApplication.init(this, "5f3a565644f41fc2");
//        } catch (e: Exception) {
//            Log.e("ttt", e.message.toString())
//        }


        //开启webview chrome调试
        WebView.setWebContentsDebuggingEnabled(true);
    }

    private fun initRealm() {
        Realm.init(this)
        val config = RealmConfiguration.Builder()
            .build()
        Realm.setDefaultConfiguration(config)
    }
}