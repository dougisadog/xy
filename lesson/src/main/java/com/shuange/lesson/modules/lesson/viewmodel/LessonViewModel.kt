package com.shuange.lesson.modules.lesson.viewmodel

import androidx.lifecycle.MutableLiveData
import com.shuange.lesson.EmptyTask
import com.shuange.lesson.base.viewmodel.BaseViewModel
import com.shuange.lesson.enumeration.InputType
import com.shuange.lesson.modules.lesson.bean.InputData
import com.shuange.lesson.modules.lesson.bean.QuestionBean
import com.shuange.lesson.modules.lesson.bean.Selection
import com.shuange.lesson.modules.lesson.bean.SourceData
import com.shuange.lesson.modules.lesson.other.LessonType
import com.shuange.lesson.service.api.ModuleDetailApi
import com.shuange.lesson.service.api.base.DownloadApi
import com.shuange.lesson.service.api.base.suspendExecute
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll

open class LessonViewModel : BaseViewModel() {

    var moduleId = ""

    var lastQuestionIndex: Int? = null

    //命
    val life = MutableLiveData<Int>()

    //进程
    val progress = MutableLiveData<Int>()

    //指定跳转页
    val next = MutableLiveData<Int>()

    //回答错误
    val wrong = MutableLiveData<Boolean>()

    //课程数据
    val lessons = mutableListOf<QuestionBean>()

    //当前课程加载完成
    val loaded = MutableLiveData<Boolean>()

    //课程加载进度
    var targetIndex = 0

    //最新进度
    var newestSavedIndex = 0

    fun loadData() {
        life.value = 8
//        getLessons()
    }

    /**
     * 上次进度
     */
    fun getLastIndex(): Int? {
        return lastQuestionIndex
    }

    /**
     * 获取所有module下的数据
     */
    fun getLessonsData(onSuccess: EmptyTask) {
        startBindLaunch {
            val result = ModuleDetailApi(moduleId).suspendExecute()
            result.getResponse()?.body?.let {
                val lessonBeans = it.questions.map {
                    var question: QuestionBean? = null
                    var inputType: InputType? = null

                    try {
                        inputType = InputType.valueOf(it.inputType)
                        val lessonType = if (it.showVideo) {
                            LessonType.TYPE_15
                        } else {
                            LessonType.getLessonType(
                                inputType
                            )
                        }
                        lessonType?.let { type ->
                            question = QuestionBean(
                                type,
                                it.id,
                                it.lessonModuleId,
                                it.lessonId,
                                it.lessonPackageId
                            )
                        }
                    } catch (e: Exception) {
                    }
                    question?.run {
                        questionsId = it.id
                        if (it.showText) {
                            text = it.questionResourceContent
                        }
                        if (it.showImage) {
                            it.questionResourceImageUrl?.let { it1 -> setImage(it1) }
                        }
                        if (it.showAudio) {
                            it.questionResourceAudioUrl?.let { it1 -> setAudio(it1) }
                        }
                        if (it.showVideo) {
                            it.questionResourceVideoUrl?.let { it1 -> setVideo(it1) }
                        }
                        it.options.forEach { op ->
                            val s = Selection()
                            s.text = op.resourceContent
                            op.resourceImageUrl?.let {
                                val imageSource = SourceData()
                                imageSource.url = it
                                imageSource.name = op.resourceId.toString()
                                imageSource.dictionary = defaultDic
                                s.img = imageSource
                            }
                            op.resourceAudioUrl?.let {
                                val audioSource = SourceData()
                                audioSource.url = it
                                audioSource.name = op.resourceId.toString()
                                audioSource.dictionary = defaultDic
                                s.audio = audioSource
                            }
                            s.bingo = op.isRight
                            selections.add(s)
                        }
                        //选项随机
                        selections.shuffle()
                        score = it.score.toDouble()
                        if (lessonType == LessonType.TYPE_02 || lessonType == LessonType.TYPE_16) {
                            initRecord()
                        }
                    }
                    question
                }
                lessons.addAll(lessonBeans.filterNotNull())
            }
            if (null == result.exception) {
                onSuccess?.invoke()
                loadLessonSource()
            }
            result.exception
        }
    }

    //TODO TEST
    fun getLessons() {
        arrayListOf(LessonType.TYPE_02).forEachIndexed { index, lessonType ->
//        LessonType.values().forEachIndexed { index, lessonType ->
            lessons.add(QuestionBean(lessonType, index.toLong()).apply {
                text = "My name is Barbara ${lessonType.name}"
                setImage("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=3844276591,3933131866&fm=26&gp=0.jpg")
                setAudio("http://downsc.chinaz.net/Files/DownLoad/sound1/202004/12800.mp3")
                if (lessonType == LessonType.TYPE_15) {
                    setVideo("http://vfx.mtime.cn/Video/2019/03/21/mp4/190321153853126488.mp4")
                }
                if (lessonType == LessonType.TYPE_10) {
                    inputData = InputData().apply {
                        text = "I am going to run away"
                        words = mutableListOf("going", "away")
                    }
                    setVideo("http://vfx.mtime.cn/Video/2019/03/21/mp4/190321153853126488.mp4")
                }
                if (lessonType == LessonType.TYPE_02 || lessonType == LessonType.TYPE_16) {
                    initRecord()
                }

                //TODO
                val size = if (index % 2 == 0) 3 else 4
                for (i in 0 until size) {
//                    var audio: SourceData? = null
//                    var bingo = false
                    selections.add(Selection().also {
                        it.text = lessonType.name + "_" + index + "_" + i.toString()
                        val imgSource = img
                        imgSource?.name = "selection_${index}"
                        it.img = imgSource
                        it.bingo = i == 0
                    })
                }
            })
        }
        loadLessonSource()
    }

    /**
     * 下载指定index的question数据
     */
    fun loadLessonSource() {
        startBindLaunch {
            val lessonData = lessons[targetIndex]
            val tasks = lessonData.getAllSource().map {
                async { DownloadApi(it).suspendDownload() }
            }
            tasks.awaitAll()
//            delay(1000L)
            loaded.value = true
            null
        }
    }

}