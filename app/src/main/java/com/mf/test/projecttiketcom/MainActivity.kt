package com.mf.test.projecttiketcom

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.mf.test.projecttiketcom.adapter.SimpleRecyclerAdapter
import com.mf.test.projecttiketcom.adapter.UserRecyclerAdapter
import com.mf.test.projecttiketcom.base.TCBaseActivity
import com.mf.test.projecttiketcom.network.users.GetUsersResponse
import com.mf.test.projecttiketcom.utils.APIUtils
import com.mf.test.projecttiketcom.utils.gone
import com.mf.test.projecttiketcom.utils.log
import com.mf.test.projecttiketcom.utils.visible
import kotlinx.android.synthetic.main.activity_main.*

/*
* Created by Fikri on 20/09/2020
* */
class MainActivity : TCBaseActivity<UsersPresenter>(), UsersView {

    private var userAdapter: SimpleRecyclerAdapter<GetUsersResponse>? = null
    private var userList: ArrayList<GetUsersResponse>? = null
    private lateinit var useAdapter: UserRecyclerAdapter
    override fun instantiateView(): Int {
        return R.layout.activity_main
    }

    override fun instantiateMenu(): Int? {
        return null
    }

    override fun instantiateTitle(): String {
        return getString(R.string.home)
    }

    override fun instantiatePresenter(): UsersPresenter {
        return UsersPresenter(this)
    }

    override fun initViews() {
        swipe?.setOnRefreshListener {
            refresh()
        }
        searchUs.setQueryHint("Search Title")
        searchUs.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String): Boolean {
                if (TextUtils.isEmpty(newText)) {
                    useAdapter.getFilter().filter("")
                    presenter.getAllUsers()
                } else {
                    useAdapter.getFilter().filter(newText)
                }
                return true
            }

            override fun onQueryTextSubmit(query: String): Boolean {

                return false
            }

        })

    }

    override fun resumeViews() {
        presenter.getAllUsers()
        userList = arrayListOf()
        rv?.layoutManager = LinearLayoutManager(this)
        useAdapter = UserRecyclerAdapter(getContext())
        rv?.adapter = useAdapter
    }


    private fun refresh() {
        APIUtils().execute(
            APIUtils.API.GET_USERS,
            listener = object : APIUtils.DataAPIListener {
                override fun onSuccess(string: String) {
                    swipe.isRefreshing = false
                }

                override fun onFailed(error: String?) {
                    swipe.isRefreshing = false
                    userList?.clear()
                    rv?.gone()
                }

                override fun <O : Any> onSuccessGetData(data: ArrayList<O>) {
                    swipe.isRefreshing = false
                    useAdapter?.notifyDataSetChanged()

                }
            }
        )
    }

    override fun onSuccessGetAccounts(accountList: ArrayList<GetUsersResponse>) {
        if (accountList.isEmpty()) {
            rv?.gone()
            txtEmpty?.visible()
        } else {
            txtEmpty?.gone()
            rv?.visible()
        }
        userList?.clear()
        userList?.addAll(accountList)
        useAdapter.setDataValue(accountList)
        useAdapter?.notifyDataSetChanged()

    }

    override fun onFailedGetAccounts(error: String?) {
        userList?.clear()
        rv?.gone()
        txtEmpty?.visible()

    }
}