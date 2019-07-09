package com.arsoft.api

import com.arsoft.api.ext.debug
import com.arsoft.api.model.BaseRequestModel
import com.arsoft.api.model.BaseResponseModel
import com.arsoft.api.util.ProgressRequestBody
import com.google.gson.Gson
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File


object WebAPI {

    private const val BASE_URL = "http://app.ar.co.th"

    private var retrofit: Retrofit? = null
    private var appAPI: AppAPI? = null

    var iRequest: INetworkRequest? = null

    fun request(url: String, data: BaseRequestModel, tag: Any, callback: INetworkCallback) {
        iRequest?.onRequest(tag.toString())
        getAPI().request(url, data).enqueue(object : Callback<BaseResponseModel> {
            override fun onFailure(call: Call<BaseResponseModel>, t: Throwable) {
                iRequest?.onFinish(tag.toString())
                "============================ Failed ============================".debug()
                "Throwable : $t".debug()
                "========================== End Failed ==========================".debug()
                val tr = t.toString()
                callback.onFailed(tag, 0, if (tr.contains("timeout")) "Time out" else tr)
            }

            override fun onResponse(call: Call<BaseResponseModel>, response: Response<BaseResponseModel>) {
                iRequest?.onFinish(tag.toString())
                "============================ SCC ============================".debug()
                "Code : ${response.code()}".debug()
                "Message : ${response.message()}".debug()
                "Body : ${response.body()}".debug()
                "========================== End SCC ==========================".debug()
                response.body()?.let {
                    if (it.statusCode == 200 || it.status == 200) {
                        callback.onSuccess(tag, 200, it.message, Gson().toJson(it.entries))
                    } else {
                        callback.onFailed(tag, if (it.statusCode == 0) it.status else it.statusCode, it.message)
                    }
                }
            }
        })
    }

    fun requestFormBody(url: String, data: HashMap<String, Any>, tag: Any, callback: INetworkCallback) {
        iRequest?.onRequest(tag.toString())
        val requestBody = MultipartBody.Builder().apply {
            setType(MultipartBody.FORM)
            for ((key, value) in data) {
                when (value) {
                    is File ->
                        addFormDataPart(key, value.name, RequestBody.create(MultipartBody.FORM, value))
                    else -> addFormDataPart(key, value.toString())
                }

            }
            if (data.isEmpty()) addFormDataPart("REQ", "S/T")
        }.build()

        getAPI().requestFormBody(url, requestBody).enqueue(object : Callback<BaseResponseModel> {
            override fun onFailure(call: Call<BaseResponseModel>, t: Throwable) {
                iRequest?.onFinish(tag.toString())
                "============================ Failed ============================".debug()
                "Throwable : $t".debug()
                "========================== End Failed ==========================".debug()
                val tr = t.toString()
                callback.onFailed(tag, 0, if (tr.contains("timeout")) "Time out" else tr)
            }

            override fun onResponse(call: Call<BaseResponseModel>, response: Response<BaseResponseModel>) {
                iRequest?.onFinish(tag.toString())
                "============================ SCC ============================".debug()
                "Code : ${response.code()}".debug()
                "Message : ${response.message()}".debug()
                "Body : ${response.body()}".debug()
                "========================== End SCC ==========================".debug()
                response.body()?.let {
                    if (it.statusCode == 200 || it.status == 200) {
                        callback.onSuccess(tag, 200, it.message, Gson().toJson(it.entries))
                    } else {
                        callback.onFailed(tag, if (it.statusCode == 0) it.status else it.statusCode, it.message)
                    }
                }
            }
        })
    }

    fun requestUpload(
        url: String,
        data: BaseRequestModel?,
        file: File,
        tag: Any,
        callback: INetworkWithProgressCallback
    ) {
        iRequest?.onRequest(tag.toString())
        val builder = MultipartBody.Builder().setType(MultipartBody.FORM)

        builder.addFormDataPart("json", Gson().toJson(data))
//        builder.addFormDataPart("file", file.name, RequestBody.create(MultipartBody.FORM, file))
        builder.addFormDataPart("file", file.name, ProgressRequestBody(MultipartBody.FORM, file, callback))

        getAPI().requestUpload(url, builder.build()).enqueue(object : Callback<BaseResponseModel> {
            override fun onFailure(call: Call<BaseResponseModel>, t: Throwable) {
                iRequest?.onFinish(tag.toString())
                "============================ Failed ============================".debug()
                "Throwable : $t".debug()
                "========================== End Failed ==========================".debug()
                val tr = t.toString()
                callback.onFailed(tag, 0, if (tr.contains("timeout")) "Time out" else tr)
            }

            override fun onResponse(call: Call<BaseResponseModel>, response: Response<BaseResponseModel>) {
                iRequest?.onFinish(tag.toString())
                "============================ SCC ============================".debug()
                "Code : ${response.code()}".debug()
                "Message : ${response.message()}".debug()
                "Body : ${response.body()}".debug()
                "========================== End SCC ==========================".debug()
                if (response.body() != null)
                    if (response.body()!!.statusCode == 200) {
                        callback.onSuccess(
                            tag,
                            response.body()!!.statusCode,
                            response.body()!!.message,
                            Gson().toJson(response.body()!!.entries)
                        )
                    } else {
                        callback.onFailed(tag, response.body()!!.statusCode, response.body()!!.message)
                    }
            }
        })
    }

    private fun getRetrofit(): Retrofit {
//        if(this.retrofit == null) {
//            val logging = HttpLoggingInterceptor()
//            logging.level = HttpLoggingInterceptor.Level.BODY
//            val httpClient = OkHttpClient.Builder()
//            httpClient.addInterceptor(logging);
//
//            this.retrofit = Retrofit.Builder()
//                .baseUrl(this@WebAPI.BASE_URL)
//                .client(httpClient.build())
//                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .build()
//        }
        if (this.retrofit == null) {
//            val logging = HttpLoggingInterceptor()
//            logging.level = HttpLoggingInterceptor.Level.BODY
            val httpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient()
//            httpClient.addInterceptor(logging);
            this.retrofit = Retrofit.Builder()
                .baseUrl(this@WebAPI.BASE_URL)
//                .client(httpClient.build())
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        }
        return this.retrofit!!
    }

    private fun getAPI(): AppAPI {
        if (this.appAPI == null) {
            this.appAPI = this.getRetrofit().create(AppAPI::class.java)
        }
        return this.appAPI!!
    }
}