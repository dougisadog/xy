package com.shuange.lesson.taioral

import android.content.Context
import com.shuange.lesson.utils.PhraseMatcher
import com.tencent.taisdk.*
import java.util.*
import kotlin.collections.ArrayList


object TAIOralManager {

    val oral: TAIOralEvaluation = TAIOralEvaluation()
        get() {
            field.apply { setListener { data, result, error -> } }
//            if (field.isRecording) {
//                field.stopRecord()
//            }
            return field
        }

    fun createParam(context: Context, target: String): TAIOralEvaluationParam {
        return TAIOralEvaluationParam().also { param ->
            param.context = context;
            param.appId = "";
            param.sessionId = UUID.randomUUID().toString();
            param.workMode = TAIOralEvaluationWorkMode.ONCE;
            param.evalMode = TAIOralEvaluationEvalMode.SENTENCE;
            param.storageMode = TAIOralEvaluationStorageMode.DISABLE;
            param.serverType = TAIOralEvaluationServerType.ENGLISH;
            param.fileType = TAIOralEvaluationFileType.MP3;//只支持mp3
            param.scoreCoeff = 1.0;
            param.refText = target;
            param.secretId = "";
            param.secretKey = "";
        }
    }

}

fun TAIOralEvaluation.startRecord(
    context: Context,
    target: String,
    onResult: (Double, PhraseMatcher) -> Unit
) {
    setListener { data, result, error ->
        if (data.bEnd) {
            onResult.invoke(result.pronCompletion, PhraseMatcher(target, result.words.map { it.word }.toMutableList()))
        }
    }
    startRecordAndEvaluation(
        TAIOralManager.createParam(
            context,
            target
        )
    ) {
        //结果返回
    }
}

fun TAIOralEvaluation.stopRecord() {
    //3.结束录制
    stopRecordAndEvaluation {
        //结果返回
    }
}
