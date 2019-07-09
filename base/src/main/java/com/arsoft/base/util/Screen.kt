package com.arsoft.base.util

import android.content.Context
import android.util.DisplayMetrics

fun Float.toPX(context: Context) =
    this * (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)

fun Float.toDP(context: Context) =
    this / (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)