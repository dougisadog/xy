package com.shuange.lesson.modules.topquality.viewmodel

import androidx.databinding.ObservableArrayList
import com.shuange.lesson.EmptyTask
import com.shuange.lesson.base.viewmodel.BaseViewModel
import com.shuange.lesson.modules.topquality.bean.GalleryItem

class GalleryViewModel : BaseViewModel() {

    val galleryItems = ObservableArrayList<GalleryItem>()


    fun loadData() {
        loadGalleries("0")
    }

    /**
     * TODO
     */
    fun loadGalleries(
        startId: String = galleryItems.lastOrNull()?.getItemId() ?: "0",
        page: Int = 50,
        onFinished: EmptyTask = null
    ) {
//        startBindLaunch(onFinish = onFinished) {
//            val suspendResult = TeachersApi().suspendExecute()
//            suspendResult.getResponse()?.body?.forEach {
////                teachers.add(TeacherBean().apply {
////                    setTeacher(it)
////                })
//            }
//            suspendResult.exception
//        }
        initTest()
    }


    //TODO
    fun initTest() {
        galleryItems.clear()
        val source = mutableListOf<GalleryItem>()
        for (i in 0 until 7) {
            source.add(GalleryItem().apply {
                pic = "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=3844276591,3933131866&fm=26&gp=0.jpg"
                hearts = i
            })
        }
        galleryItems.addAll(source)
    }

}