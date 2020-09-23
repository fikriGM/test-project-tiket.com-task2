package com.mf.test.projecttiketcom.utils.view

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import androidx.annotation.Nullable
import androidx.appcompat.widget.Toolbar

/*
* Created by Fikri on 20/09/2020
* */

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
class TCCenteredToolbarTitle @JvmOverloads constructor(
    context: Context?,
    @Nullable attrs: AttributeSet? = null,
    defStyleAttr: Int = androidx.appcompat.R.attr.toolbarStyle
) :
    Toolbar(context!!, attrs, defStyleAttr) {


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val childCount = childCount
        for (i in 0 until childCount) {
            val view = getChildAt(i)
            if (view is RelativeLayout) {
                val width = measuredWidth
                val layoutParams = view.getLayoutParams()
                layoutParams.width = width // (width * WIDTH_PERCENTAGE);
                view.setLayoutParams(layoutParams)
                break
            }
        }
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
        val childCount = childCount
        for (i in 0 until childCount) {
            val view = getChildAt(i)
            if (view is RelativeLayout) {
                forceTitleCenter(view)
                break
            }
        }
    }

    private fun forceTitleCenter(view: View) {
        val toolbarWidth = measuredWidth
        val relativeLayoutWidth = view.measuredWidth
        val newLeft = (toolbarWidth - relativeLayoutWidth) / 2
        val top = view.top
        val newRight = newLeft + relativeLayoutWidth
        val bottom = view.bottom
        view.layout(newLeft, top, newRight, bottom)
    }
}