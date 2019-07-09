package com.arsoft.base

import com.arsoft.api.INetworkCallback
import com.arsoft.api.WebAPI
import com.arsoft.api.model.BaseDataModel
import com.arsoft.api.model.BaseRequestModel
import com.arsoft.base.intf.IAppCallback

abstract class BaseRepository(val module: String) {

    private var masterList: BaseArrayList<BaseMaster> = BaseArrayList()

    abstract fun initMaster(masterList: BaseArrayList<BaseMaster>)

    init {
        this.initMaster(masterList)
    }

    fun getMasterByTarget(target: String): BaseMaster? = masterList.find { it ->
        it.target == target
    }

    fun shouldUpdateMaster(): Boolean {
        var should = false
        masterList.forEach {

            if (it.data is BaseArrayList<*>) {
                if ((it.data as BaseArrayList<*>).isEmpty())
                    should = true
            } else if (it.data is BaseDataModel) {
                if ((it.data as BaseDataModel).getUpdateDate().time == 0L)
                    should = true
            }
        }
        return should
    }

    fun updateMaster(callback: IAppCallback) {
        var countDown = masterList.size
        var failedCode = 0
        var hasFailed = false
        var failedFor = ""
        val apiCallback = object : INetworkCallback {
            override fun onSuccess(tag: Any, statCode: Int, message: String, entries: Any) {
                masterList.forEach {
                    if (it.target.equals(tag.toString())) {
                        it.setValue(entries.toString())
                        countDown--
                        if (countDown <= 0) {
                            if (hasFailed)
                                callback.updated("master", failedCode, failedFor)
                            else
                                callback.updated("master", 200, "done")
                        }
                    }
                }
            }

            override fun onFailed(tag: Any, statCode: Int, message: String) {
                masterList.forEach {
                    if (it.target.equals(tag.toString())) {

                        hasFailed = true
                        failedCode = statCode
                        failedFor += "[" + it.module + ":" + it.target + "->" + message + "]" + System.lineSeparator()

                        countDown--
                        if (countDown <= 0) {
                            if (hasFailed)
                                callback.updated("master", failedCode, failedFor)
                            else
                                callback.updated("master", 200, "done")
                        }
                    }
                }
            }
        }
        masterList.forEach {
            WebAPI.request(
                it.url,
                BaseRequestModel(
                    it.module,
                    it.target,
                    "",
                    BaseConfig.APIVersion
                ),
                it.target,
                apiCallback
            )
        }
    }
}