package com.arsoft.base.intf

interface IAppCallback {
    fun updated(tag: String, code: Int, data: Any)
}