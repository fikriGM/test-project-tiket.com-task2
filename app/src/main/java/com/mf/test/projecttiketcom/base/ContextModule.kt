package com.mf.test.projecttiketcom.base

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides

/*
* Created by Fikri on 20/09/2020
* */

@Module
@Suppress("unused")
object ContextModule {
    @Provides
    @JvmStatic
    internal fun provideContext(BaseView: TCBaseView): Context {
        return BaseView.getContext()
    }

    @Provides
    @JvmStatic
    internal fun provideApplication(context: Context): Application {
        return context.applicationContext as Application
    }
}