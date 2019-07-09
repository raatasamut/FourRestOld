package com.arsoft.api

import com.arsoft.api.model.BaseRequestModel
import com.arsoft.api.model.BaseResponseModel
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Url

interface AppAPI {

    @POST
    fun request(@Url url: String, @Body body: BaseRequestModel): Call<BaseResponseModel>

    @POST
    fun requestFormBody(@Url url: String, @Body body: RequestBody): Call<BaseResponseModel>

    @POST
    fun requestUpload(@Url url: String, @Body body: RequestBody): Call<BaseResponseModel>

}