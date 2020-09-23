package com.mf.test.projecttiketcom.base

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mf.test.projecttiketcom.R
import com.mf.test.projecttiketcom.utils.view.TCCenteredToolbarTitle
import com.mf.test.projecttiketcom.utils.view.TCTransparentLoading
import kotlinx.android.synthetic.main.tc_custom_toolbar.*

/*
* Created by Fikri on 20/09/2020
* */
abstract class TCBaseActivity<P:TCBasePresenter> :TCBaseView,AppCompatActivity(){

    protected lateinit var presenter: P
    private var toolbar: TCCenteredToolbarTitle? = null
    protected var transparentLoading : TCTransparentLoading? = null


    protected abstract fun instantiateView(): Int
    protected abstract fun instantiateMenu(): Int?
    protected abstract fun instantiatePresenter(): P
    protected abstract fun instantiateTitle(): String


    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(instantiateView())
        toolbar = findViewById(R.id.toolbar)
        transparentLoading = TCTransparentLoading(this, R.style.transparent_dialog)

        setSupportActionBar(toolbar)
        if (toolbar != null){
            setSupportActionBar(toolbar)
//            supportActionBar?.setDisplayHomeAsUpEnabled(true)
//            supportActionBar?.setHomeButtonEnabled(true)
            if (instantiateMenu() != null){
                toolbar?.inflateMenu(instantiateMenu()!!)
            }

        }

        presenter = instantiatePresenter()

        getExtras()
        initViews()
    }

    override fun onResume() {
        toolbar_title?.text = instantiateTitle()
        resumeViews()
        super.onResume()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun getContext(): Context {
        return this
    }

    override fun showLoading() {
        transparentLoading!!.start()
    }

    override fun hideLoading() {
        transparentLoading!!.stop()
    }

}