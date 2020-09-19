package corelib.http

import corelib.TimeInterval

sealed class Setting {

    companion object {
        // 自動更新ON / OFF
        val autoRefresh: Boolean = true
        // 自動更新間隔
        val autoRefreshInterval: Int = 5

        //http default setting
        var timeoutIntervalForRequest: TimeInterval = 20.0
        var timeoutIntervalForResource: TimeInterval = 30.0
        var retryTimes: Int = 3
        //user auth header
        var auth: Pair<String, String>? = null

    }



}