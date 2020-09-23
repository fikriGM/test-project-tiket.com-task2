package com.mf.test.projecttiketcom.base

import android.content.Context

/*
* Created by Fikri on 20/09/2020
* */

interface TCBaseView {
    fun getContext(): Context
    fun showLoading(){}
    fun hideLoading(){}
    //declare view in onResume
    fun resumeViews(){}
    fun getExtras(){}
    //declare view in onCreate
    fun initViews(){}
}