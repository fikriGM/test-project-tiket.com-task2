package com.mf.test.projecttiketcom.base

import com.mf.test.projecttiketcom.utils.CoroutineContextProvider

/*
* Created by Fikri on 20/09/2020
* */

abstract class TCBasePresenter (view: TCBaseView){
    protected val provider = CoroutineContextProvider()
    private val injector: PresenterInjector = DaggerPresenterInjector
        .builder()
        .baseView(view)
        .contextModule(ContextModule)
//            .networkModule(NetworkModule)
        .build()

    init {
        inject()
    }

    open fun onViewCreated(){}

    private fun inject() {
        injector.inject(this)
    }
}