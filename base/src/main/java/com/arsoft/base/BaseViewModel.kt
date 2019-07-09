package com.arsoft.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel : ViewModel() {
    val errorObj: MutableLiveData<Triple<Any, Int, String>> by lazy {
        MutableLiveData<Triple<Any, Int, String>>()
    }
}