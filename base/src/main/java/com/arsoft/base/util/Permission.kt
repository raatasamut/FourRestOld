package com.arsoft.base.util

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat


fun requestPermission(context: Context, perm: String): Boolean {
    if (ActivityCompat.checkSelfPermission(
            context,
            perm
        ) == PackageManager.PERMISSION_GRANTED
    ) {
        return true
    } else {
        val permission = arrayOf<String>(perm)
        ActivityCompat.requestPermissions(context as Activity, permission, 9)
    }
    return false
}