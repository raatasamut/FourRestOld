package com.arsoft.api.ext

import android.util.Log

fun String.debug(tag: String = "DEBUG") {
    Log.d(tag, this)
}