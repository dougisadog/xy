package com.shuange.lesson

import android.app.Application
import android.webkit.WebView
import com.shuange.lesson.base.config.AppConfig
import com.shuange.lesson.error.ErrLogManager
import corelib.util.ApplicationUtil
import corelib.util.ContextManager
import io.realm.Realm
import io.realm.RealmConfiguration

open class LessonApp : Application() {

    override fun onCreate() {
        super.onCreate()
        ContextManager.init(this)
        initRealm()
        ErrLogManager.registerHandler()
        ApplicationUtil.setApkInfo(this)
        //开启webview chrome调试
        if (AppConfig.DEBUG) {
            WebView.setWebContentsDebuggingEnabled(true);
        }
        if (1/0 ==1) {
            return
        }
    }

    private fun initRealm() {
        Realm.init(this)
        val config = RealmConfiguration.Builder()
            .build()
        Realm.setDefaultConfiguration(config)
    }

}