package com.arsoft.base

import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import es.dmoral.toasty.Toasty

open class BaseFragment : Fragment() {

    companion object {
        val FRAGMENT_TAG_PRIMARY_ACTION = "primary_action"
    }

    lateinit var viewContainer: View

    fun toast(msg: String) {
        Toasty.normal(BaseConfig.appContext, msg, Toast.LENGTH_LONG).show()
    }

    open fun action(tag: String, data: Any) {

    }
}