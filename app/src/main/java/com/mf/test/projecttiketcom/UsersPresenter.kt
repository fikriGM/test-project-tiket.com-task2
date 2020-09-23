package com.mf.test.projecttiketcom

import com.mf.test.projecttiketcom.base.TCBasePresenter
import com.mf.test.projecttiketcom.network.users.GetUsersResponse
import com.mf.test.projecttiketcom.utils.APIUtils
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/*
* Created by Fikri on 20/09/2020
* */

class UsersPresenter(private val view: UsersView) : TCBasePresenter(view){

    fun getAllUsers() {
        GlobalScope.launch(provider.main) {
            APIUtils().execute(
                APIUtils.API.GET_USERS,
                listener = object : APIUtils.DataAPIListener {
                    override fun onSuccess(string: String) {
                        view.hideLoading()
                    }

                    override fun onFailed(error: String?) {
                        view.onFailedGetAccounts(error)
                    }

                    override fun <O : Any> onSuccessGetData(data: ArrayList<O>) {
                        view.onSuccessGetAccounts(data as ArrayList<GetUsersResponse>)

                    }
                }
            )
        }

    }

}