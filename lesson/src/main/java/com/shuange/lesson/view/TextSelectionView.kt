package com.shuange.lesson.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import androidx.core.content.ContextCompat
import com.shuange.lesson.R
import com.shuange.lesson.modules.lesson.bean.Selection
import com.shuange.lesson.utils.MediaPlayerMgr
import com.shuange.lesson.utils.ToastUtil
import kotlinx.android.synthetic.main.layout_text_selection.view.*


class TextSelectionView : RelativeLayout {

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    fun init() {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.layout_text_selection, this)
    }


    private fun playAudio() {
        val path = selection?.audio?.getFullPath()
        MediaPlayerMgr.getInstance().playMp(path)
    }

    private var selection: Selection? = null

    fun setSelection(selection: Selection) {
        this.selection = selection
        selectionTv.text = selection.text
        if (null == selection.audio) {
            audioIv.visibility = GONE
        }
        else {
            audioIv.visibility = VISIBLE
            audioIv.setOnClickListener(NonDoubleClickListener {
                playAudio()
            })
        }
    }

    fun refreshState() {
        val selection  = selection?:return
        selectionTv.setTextColor(ContextCompat.getColor(context, R.color.white))
        if (selection.bingo) {
            rootRl.setBackgroundResource(R.drawable.bg_text_selection_right)
        }
        else {
            rootRl.setBackgroundResource(R.drawable.bg_text_selection_wrong)
        }
    }
}