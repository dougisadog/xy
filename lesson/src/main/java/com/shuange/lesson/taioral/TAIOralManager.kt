package com.shuange.lesson.taioral

import android.content.Context
import android.util.Log
import com.shuange.lesson.base.config.ConfigDef
import com.shuange.lesson.utils.PhraseMatcher
import com.tencent.taisdk.*
import java.io.File
import java.io.InputStream
import java.util.*


object TAIOralManager {

    val oral: TAIOralEvaluation = TAIOralEvaluation()

    fun createParam(context: Context, target: String): TAIOralEvaluationParam {
        return TAIOralEvaluationParam().also { param ->
            param.context = context;
            param.appId = ConfigDef.TAI_ORAL_APP_ID;
            param.sessionId = UUID.randomUUID().toString();
            param.workMode = TAIOralEvaluationWorkMode.ONCE;
            param.evalMode = TAIOralEvaluationEvalMode.SENTENCE;
            param.storageMode = TAIOralEvaluationStorageMode.DISABLE;
            param.serverType = TAIOralEvaluationServerType.ENGLISH;
            param.fileType = TAIOralEvaluationFileType.WAV;//只支持mp3
            param.scoreCoeff = 1.0;
            param.refText = target;
//            param.refText = ""

            param.secretId = ConfigDef.TAI_ORAL__SECRET_ID;
            param.secretKey = ConfigDef.TAI_ORAL__SECRET_KEY
        }
    }

}

fun TAIOralEvaluation.addDefaultListener(
    onResult: (Double, List<TAIOralEvaluationWord>) -> Unit
) {
    setListener { data, result, error ->
        if (data.bEnd && null != result) {
            onResult.invoke(
                result.pronCompletion,
                result.words
            )
        }
    }
}

fun TAIOralEvaluation.parser(
    context: Context,
    target: String,
    audio: File,
    onResult: (Double, List<TAIOralEvaluationWord>) -> Unit
) {
    addDefaultListener(onResult)
    try {
        val `is`: InputStream = audio.inputStream()
        val buffer = ByteArray(`is`.available())
        `is`.read(buffer)
        `is`.close()
        val data = TAIOralEvaluationData()
        data.seqId = 1
        data.bEnd = true
        data.audio = buffer
        oralEvaluation(
            TAIOralManager.createParam(
                context,
                target
            ), data
        ) {
            //接口调用结果返回
        }
    } catch (e: Exception) {
    }
}

fun TAIOralEvaluation.startRecord(
    context: Context,
    target: String,
    onResult: (Double, List<TAIOralEvaluationWord>) -> Unit
) {
    addDefaultListener(onResult)
    startRecordAndEvaluation(
        TAIOralManager.createParam(
            context,
            target
        )
    ) {
        //结果返回
        Log.e("TAIOral start", it?.desc.toString())
    }
}

fun TAIOralEvaluation.stopRecord() {
    //3.结束录制
    stopRecordAndEvaluation {
        //结果返回
        Log.e("TAIOral stop", it?.desc.toString())

    }
}
