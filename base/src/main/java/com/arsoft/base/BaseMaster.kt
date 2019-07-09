package com.arsoft.base

import com.google.gson.Gson
import java.lang.reflect.Type

class BaseMaster(val url: String, val module: String, val target: String, var data: Any, var type: Type) {

    fun setValue(json: String) {
        if (data is BaseArrayList<*>) {
            this.data = Gson().fromJson(json, type)
        }
    }
}