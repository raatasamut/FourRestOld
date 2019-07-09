package com.arsoft.base

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.widget.Toast
import com.arsoft.api.WebAPI
import es.dmoral.toasty.Toasty

open class BaseFragmentActivity : androidx.fragment.app.FragmentActivity() {

    var contentFragment: Int = 0
    var showLoading = true

    var flags: BaseArrayList<String> = BaseArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        super.onCreate(savedInstanceState)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }

    fun initContentView(resId: Int) {
        this.contentFragment = resId
    }

    fun changeContentView(fm: androidx.fragment.app.Fragment): Boolean {
        if (contentFragment == 0) return false

        supportFragmentManager.beginTransaction()
            .replace(contentFragment, fm).commit()
        return true
    }

    fun toast(msg: String?) {
        msg?.let {
            Toasty.normal(this, it, Toast.LENGTH_LONG).show()
        }
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }

    override fun onDestroy() {
        WebAPI.iRequest = null
        super.onDestroy()
    }
}