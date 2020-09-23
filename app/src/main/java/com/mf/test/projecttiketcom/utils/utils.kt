package com.mf.test.projecttiketcom.utils

import android.util.Log
import android.view.View

fun Int.successCode() : Boolean {
    return this == 200
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun log(where:Class<Any>,content:String?){
    Log.v(where.name,"${content}")
}