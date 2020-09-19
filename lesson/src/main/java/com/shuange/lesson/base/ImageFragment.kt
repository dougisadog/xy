package com.shuange.lesson.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.shuange.lesson.R
import kotlinx.android.synthetic.main.fragment_image.*

class ImageFragment(val link: String) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_image, null)
        Glide.with(this).load(link).into(imageIv)
        return root
    }
}