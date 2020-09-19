package com.shuange.lesson.modules.unit.viewmodel

import androidx.lifecycle.MutableLiveData
import com.shuange.lesson.base.viewmodel.BaseViewModel
import com.shuange.lesson.modules.lesson.bean.InputData
import com.shuange.lesson.modules.lesson.bean.LessonBean
import com.shuange.lesson.modules.lesson.bean.Selection
import com.shuange.lesson.modules.lesson.other.LessonType
import com.shuange.lesson.service.api.base.DownloadApi
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll

open class UnitViewModel : BaseViewModel() {

    //命
    val life = MutableLiveData<Int>()

    //进程
    val progress = MutableLiveData<Int>()

    //下一页
    val next = MutableLiveData<Boolean>()

    //回答错误
    val wrong = MutableLiveData<Boolean>()

    //课程数据
    val lessons = mutableListOf<LessonBean>()

    //当前课程加载完成
    val loaded = MutableLiveData<Boolean>()

    //课程加载进度
    var targetIndex = 0

    fun initData() {
        life.value = 8
        getLessons()
    }

    fun getLessons() {
        arrayListOf(LessonType.TYPE_02,LessonType.TYPE_02,LessonType.TYPE_02,LessonType.TYPE_02).forEachIndexed { index, lessonType ->
//        LessonType.values().forEachIndexed { index, lessonType ->
            lessons.add(LessonBean(lessonType, index.toString() + "_id").apply {
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
                if(lessonType == LessonType.TYPE_02 || lessonType == LessonType.TYPE_16) {
                    initRecord()
                }

                //TODO
                val size = if (index %2 ==0) 3 else 4
                for(i in 0  until size) {
//                    var audio: SourceData? = null
//                    var bingo = false
                    selections.add(Selection().also {
                        it.text = lessonType.name + "_" + index + "_" + i.toString()
                        val imgSource = img
                        imgSource?.name = "selection_${index}"
                        it.img = imgSource
                        it.bingo = i == 0
                    } )
                }
            })
        }
        loadLessonSource()
    }

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