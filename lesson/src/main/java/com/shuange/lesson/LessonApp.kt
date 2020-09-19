package com.shuange.lesson

import android.app.Application
import android.os.Build
import android.webkit.WebView
import com.shuange.lesson.base.LessonDataCache
import com.shuange.lesson.service.api.InitApi
import com.shuange.lesson.service.api.base.suspendExecute
import com.shuange.lesson.service.request.InitRequest
import com.youdao.sdk.app.YouDaoApplication
import corelib.util.ApplicationUtil
import corelib.util.ContextManager
import corelib.util.Log
import io.realm.Realm
import io.realm.RealmConfiguration
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

open class LessonApp : Application() {

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
        init()
    }

    private fun initRealm() {
        Realm.init(this)
        val config = RealmConfiguration.Builder()
            .build()
        Realm.setDefaultConfiguration(config)
    }

    //TODO test
    fun init() {
        GlobalScope.launch(Dispatchers.Main) {
            val request = InitRequest().apply { login = "dougisadog" }
            val suspendResult = InitApi(request).suspendExecute()
            LessonDataCache.token = suspendResult.getResponse()?.id_token ?: ""
        }
    }
}