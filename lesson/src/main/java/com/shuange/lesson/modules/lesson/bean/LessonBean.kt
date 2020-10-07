package com.shuange.lesson.modules.lesson.bean

import com.shuange.lesson.modules.lesson.other.LessonType
import com.shuange.lesson.service.response.bean.Lesson
import java.io.File
import java.io.Serializable

/**
 * question 结构
 * @param id  question id
 * @param lessonId module Id
 */
class LessonBean(var lessonType: LessonType, var id: String, var lessonId:String? = null) : Serializable {

    var text = ""
    var img: SourceData? = null
    var audio: SourceData? = null
    var selections = arrayListOf<Selection>()
    var video: SourceData? = null
    var inputData: InputData? = null

    var record: SourceData? = null

    var score: Double? = null

    fun setLesson(lesson:Lesson) {
        id = lesson.id.toString()
        text = lesson.description
        //TODO  01 Lesson 没有图片地址
        setImage(lesson.resourceId.toString())
        setVideo(lesson.resourceVideoUrl)
        setAudio(lesson.resourceAudioUrl)
    }

    val defaultDic:String
    get() {
        return lessonType.name + File.separator + this.id
    }

    private fun buildSourceDataByLink(
        link: String,
        id: String,
        dic: String = lessonType.name + File.separator + this.id
    ): SourceData {
        return SourceData().apply {
            name = id
            dictionary = dic
            url = link

            val arr = link.split(".")
            suffix = if (arr.size > 1) arr[arr.size - 1] else ""
        }
    }

    fun setImage(link: String) {
        img = buildSourceDataByLink(link, "img_$id")
    }

    fun setAudio(link: String) {
        audio = buildSourceDataByLink(link, "audio_$id")
    }

    fun setVideo(link: String) {
        video = buildSourceDataByLink(link, "video_$id")
    }

    fun initRecord() {
        record =  SourceData().apply {
            name =  "record_$id"
            dictionary = lessonType.name + File.separator + id
            suffix = "wav"
        }
    }

    fun getAllSource(): ArrayList<SourceData> {
        val result = arrayListOf<SourceData>()
        img?.let { result.add(it) }
        audio?.let { result.add(it) }
        video?.let { result.add(it) }
        selections.forEach { selection ->
            selection.img?.let { result.add(it) }
            selection.audio?.let { result.add(it) }
        }
        return result
    }

}