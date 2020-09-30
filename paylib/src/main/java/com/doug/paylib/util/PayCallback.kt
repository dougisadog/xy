package com.doug.paylib.util

interface PayCallback {

    fun onSuccess()
    fun onFailed(error: String)

}