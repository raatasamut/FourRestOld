package com.arsoft.api.model

import java.util.*

open class BaseDataModel {
    private var uniqueID: Long = -1
    private var updateDate: Long = 0

    fun getUniqueID() = this.uniqueID

    fun getUpdateDate() = Date(updateDate * 1000)

    fun setUniqueID(id: Long) {
        this.uniqueID = id
    }

    fun setUpdateDate(date: Date) {
        this.updateDate = date.time
    }
}