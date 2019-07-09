package com.arsoft.base

import android.content.Context

class BaseConfig {
    companion object{
        var APIVersion: String = "V1.0.0"
        lateinit var appContext: Context
    }
}