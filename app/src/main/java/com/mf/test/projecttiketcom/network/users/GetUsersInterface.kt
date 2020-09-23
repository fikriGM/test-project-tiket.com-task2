package com.mf.test.projecttiketcom.network.users

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/*
* Created by Fikri on 20/09/2020
* */

interface GetUsersInterface {

    @GET("users?per_page=100")
    fun getUsers(@Query("per_page") per_page: Int): Call<List<GetUsersResponse>>
}