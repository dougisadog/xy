package com.shuange.lesson.youdao

import com.shuange.lesson.utils.PhraseMatcher
import com.youdao.sdk.app.Language
import com.youdao.sdk.common.Constants
import com.youdao.voicerecognize.online.*

object YoudaoParser {

    fun parseResult(bases64: String, target: String, onFinish: (PhraseMatcher) -> Unit) {
        val parameters = ASRParameters.Builder()
            .source("youdaoocr")
            .timeout(100000)
            .lanType(Language.ENGLISH.code)//langType支持中文和英文
            .rate(Constants.RATE_16000)
            .build();
        ASRRecognizer.getInstance(parameters).recognize(
            bases64,
            object : ASRListener {
                override fun onResult(result: ASRResult, input: String, requestid: String) {
                    if (result.result != null && result.result.size > 0) {
                        val matcher = PhraseMatcher(target, result.result.toMutableList())
                        onFinish.invoke(matcher)
                    }
                }

                override fun onError(error: ASRErrorCode, requestid: String) {
                }
            }, "requestid"
        )
    }
}