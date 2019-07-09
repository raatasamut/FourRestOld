package com.arsoft.api.model

data class BaseResponseModel(
    val status: Int = 0,
    val statusCode: Int = 0,
    val message: String,
    val entries: Any
)