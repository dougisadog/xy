package com.shuange.lesson.modules.lesson.bean

import com.shuange.lesson.modules.lesson.other.LessonType
import com.shuange.lesson.service.response.bean.Lesson
import java.io.File
import java.io.Serializable

/**
 * question 结构
 * @param questionsId  question id
 * @param moduleId module Id
 */
class QuestionBean(
    var lessonType: LessonType,
    var questionsId: Long = 0,
    var moduleId: Long = 0,
    var lessonId: Long = 0,
    var lessonPackageId: Long = 0
) : Serializable {

    var text = ""
    var img: SourceData? = null
    var audio: SourceData? = null
    var selections = arrayListOf<Selection>()
    var video: SourceData? = null
    var inputData: InputData? = null

    var record: SourceData? = null

    var score: Double? = null

    fun setLesson(lesson: Lesson) {
        questionsId = lesson.id
        text = lesson.description
        setImage(lesson.resourceId.toString())
        lesson.resourceVideoUrl?.let { setVideo(it) }
        lesson.resourceAudioUrl?.let { setAudio(it) }
        lesson.resourceImageUrl?.let { setImage(it) }
    }

    val defaultDic: String
        get() {
            return lessonType.name + File.separator + this.questionsId
        }

    private fun buildSourceDataByLink(
        link: String,
        id: String,
        dic: String = lessonType.name + File.separator + this.questionsId
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
        img = buildSourceDataByLink(link, "img_$questionsId")
    }

    fun setAudio(link: String) {
        audio = buildSourceDataByLink(link, "audio_$questionsId")
    }

    fun setVideo(link: String) {
        video = buildSourceDataByLink(link, "video_$questionsId")
    }

    fun initRecord() {
        record = SourceData().apply {
            name = "record_$questionsId"
            dictionary = lessonType.name + File.separator + questionsId
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