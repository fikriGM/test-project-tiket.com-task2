package com.mf.test.projecttiketcom.base

import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

/*
* Created by Fikri on 20/09/2020
* */

@Singleton
@Component(modules = [(ContextModule::class)])
interface PresenterInjector {
    fun inject(presenter : TCBasePresenter){}

    @Component.Builder
    interface Builder {
        fun build(): PresenterInjector
        fun contextModule(contextModule: ContextModule): Builder
        @BindsInstance
        fun baseView(BaseView: TCBaseView): Builder
    }
}