package com.mf.test.projecttiketcom.utils.view

import android.app.Activity
import android.app.Dialog
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.annotation.StyleRes
import com.mf.test.projecttiketcom.R
import kotlinx.android.synthetic.main.tc_loading_bar.*
import java.util.*

/*
* Created by Fikri on 20/09/2020
* */
class TCTransparentLoading(@NonNull activity: Activity, @StyleRes themeResId:Int):
    Dialog(activity, themeResId) {
    private val context:Activity

    init{
        this.context = activity
        setCancelable(false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tc_loading_bar)
        tc_loading_bar_image.setAnimatedGif(R.drawable.tc_loading_bar, AnimatedGifImageView.TYPE.AS_IS)
        Objects.requireNonNull(getWindow())?.setBackgroundDrawable(ColorDrawable(0))
        val layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT)
        getWindow()?.setLayout(layoutParams.width, layoutParams.height)
    }
    private fun showHourglass(isVisible:Boolean) {
        if (isVisible)
        {
            if (!isShowing())
            {
                this.show()
            }
        }
        else
        {
            if (this.isShowing())
            {
                this.dismiss()
            }
        }
    }
    fun start() {
        context.runOnUiThread({ showHourglass(true) })
    }
    fun stop() {
        context.runOnUiThread({ showHourglass(false) })
    }
}