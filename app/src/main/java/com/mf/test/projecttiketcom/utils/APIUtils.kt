package com.mf.test.projecttiketcom.utils

import android.content.Context
import com.google.gson.Gson
import com.mf.test.projecttiketcom.network.ServiceBuilder
import com.mf.test.projecttiketcom.network.users.GetUsersInterface
import com.mf.test.projecttiketcom.network.users.GetUsersResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.log

/*
* Created by Fikri on 20/09/2020
* */

class APIUtils {

    enum class API {
        GET_USERS
    }

    interface APIListener {
        fun onSuccess(string: String)
        fun onFailed(string: String?)
    }

    interface DataAPIListener : APIListener {
        fun <O : Any> onSuccessGetData(data: ArrayList<O>) {}
        fun <O : Any> onSuccessGetObject(obj: O) {}
    }

    fun execute(
        endpoint: API,
        listener: APIListener
    ) {
        when (endpoint) {
            API.GET_USERS -> {
                ServiceBuilder.buildService(GetUsersInterface::class.java)
                    .getUsers(50).enqueue(
                        object : Callback<List<GetUsersResponse>> {
                            override fun onFailure(
                                call: Call<List<GetUsersResponse>>,
                                t: Throwable
                            ) {
                                listener.onFailed(t.localizedMessage)
                            }

                            override fun onResponse(
                                call: Call<List<GetUsersResponse>>,
                                response: Response<List<GetUsersResponse>>
                            ) {
                                if (response.code().successCode()) {
                                    if (listener is DataAPIListener) {
                                        val subscriptions =
                                            arrayListOf<GetUsersResponse>()
                                        subscriptions.addAll(response.body()!!)
                                        listener.onSuccessGetData(subscriptions)
                                    }
                                    listener.onSuccess(Gson().toJson(response.body()))
                                } else {
                                    listener.onFailed(Gson().toJson(response.body()))
                                }
                            }

                        }
                    )

            }
        }
    }
}