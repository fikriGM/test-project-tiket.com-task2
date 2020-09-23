package com.mf.test.projecttiketcom

import com.mf.test.projecttiketcom.base.TCBaseView
import com.mf.test.projecttiketcom.network.users.GetUsersResponse

/*
* Created by Fikri on 20/09/2020
* */

interface UsersView :TCBaseView{
    fun onSuccessGetAccounts(accountList : ArrayList<GetUsersResponse>){}
    fun onFailedGetAccounts(error : String?){}

}