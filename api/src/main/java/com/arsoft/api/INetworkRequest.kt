package com.arsoft.api

interface INetworkRequest {
    fun onFinish(tag: String)
    fun onRequest(tag: String)
}