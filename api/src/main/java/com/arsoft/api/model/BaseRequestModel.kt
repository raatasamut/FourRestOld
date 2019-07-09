package com.arsoft.api.model

open class BaseRequestModel(
    val module: String? = null,
    val target: String? = null,
    val token: String? = null,
    val APIVersion: String? = null,
    val data: BaseDataModel? = null
)

data class BaseDataRequestModel(
    val token: String
) : BaseDataModel()