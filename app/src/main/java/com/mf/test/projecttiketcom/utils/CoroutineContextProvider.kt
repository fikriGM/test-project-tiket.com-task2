package com.mf.test.projecttiketcom.utils

import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.Dispatchers

/*
* Created by Fikri on 20/09/2020
* */
class CoroutineContextProvider {
    open val main: CoroutineContext by lazy { Dispatchers.Main }
    open val io: CoroutineContext by lazy { Dispatchers.IO }
}