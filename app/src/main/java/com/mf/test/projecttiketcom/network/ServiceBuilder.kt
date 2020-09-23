package com.mf.test.projecttiketcom.network

import com.mf.test.projecttiketcom.network.users.GetUsersInterface
import com.mf.test.projecttiketcom.utils.Constants.Companion.BASE_URL_USERS
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import rx.schedulers.Schedulers

/*
* Created by Fikri on 20/09/2020
* */

object ServiceBuilder {
    fun getInterceptor() : OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
        return  okHttpClient
    }

    fun<T> buildService(service: Class<T>): T{
        val baseUrl =
            when(service) {
                GetUsersInterface::class.java -> {
                    BASE_URL_USERS
                }
                else -> ""
            }
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(getInterceptor())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io()))
            .build().create(service)
    }
}