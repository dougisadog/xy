package com.shuange.lesson.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.shuange.lesson.R
import com.shuange.lesson.base.config.IntentKey
import kotlinx.android.synthetic.main.fragment_image.*

class ImageFragment : Fragment() {

    companion object {
        fun newInstance(link: String): ImageFragment {
            val f = ImageFragment()
            Bundle().apply {
                putString(IntentKey.IMAGE_TYPE_KEY, link)
                f.arguments = this
            }
            return f
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_image, null)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val link = arguments?.getString(IntentKey.IMAGE_TYPE_KEY) ?: return
        Glide.with(this).load(link).into(imageIv)
    }
}