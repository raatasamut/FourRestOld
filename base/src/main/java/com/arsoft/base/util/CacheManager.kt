package com.arsoft.base.util

import android.content.Context
import android.content.SharedPreferences
import com.arsoft.base.BaseConfig
import java.util.*

object CacheManager {
    val BEGINDATE = "BeginDate"
    val EXPIREDATE = "ExpireDate"

    val DAY: Long = 60 * 60 * 24

    val MONTH: Long = DAY * 30

    fun setDate(sp: SharedPreferences, timeInterval: Long) {
        val now = Calendar.getInstance().timeInMillis
        val editor: SharedPreferences.Editor = sp.edit()
        editor.putLong(BEGINDATE, now)

        if (timeInterval != -1L)
            editor.putLong(EXPIREDATE, now + (timeInterval * 1000))
        else
            editor.putLong(EXPIREDATE, timeInterval)

        editor.apply()
    }

    fun getExpireDate(sp: SharedPreferences): Long {
        return sp.getLong(EXPIREDATE, 0)
    }
}

fun String.setCache(obj: String, timeInterval: Long) {
    val sp = BaseConfig.appContext.getSharedPreferences(this, Context.MODE_PRIVATE)
    sp.edit().putString(this, obj).apply()
    CacheManager.setDate(sp, timeInterval)
}

fun String.getCache(): String {
    val sp = BaseConfig.appContext.getSharedPreferences(this, Context.MODE_PRIVATE)
    val expire = CacheManager.getExpireDate(sp)
    if (expire != -1L && expire < Calendar.getInstance().timeInMillis)
        this.clearCache()
    return sp.getString(this, "")!!
}

fun String.clearCache() {
    BaseConfig.appContext.getSharedPreferences(this, Context.MODE_PRIVATE).edit().clear().apply()
}