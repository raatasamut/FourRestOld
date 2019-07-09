package com.arsoft.api

interface INetworkCallback {
    fun onSuccess(tag: Any, statCode: Int, message: String, entries: Any)
    fun onFailed(tag: Any, statCode: Int, message: String)
}

interface INetworkWithProgressCallback {
    fun onProgress(tag: Any, progress: Int)
    fun onSuccess(tag: Any, statCode: Int, message: String, entries: Any)
    fun onFailed(tag: Any, statCode: Int, message: String)
}