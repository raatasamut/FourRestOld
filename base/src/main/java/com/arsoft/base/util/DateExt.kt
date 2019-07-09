package com.arsoft.base.util

import java.text.SimpleDateFormat
import java.util.*

const val DAY_IN_MS = (1000 * 60 * 60 * 24).toLong()

fun Long.formatDate(): String {
    return SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date(this))
}

fun String.fromDateFormat(): Date {
    return SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(this)
}

fun Date.dayBefore(num: Int): Date {
    val DAY_IN_MS = (1000 * 60 * 60 * 24).toLong()
    return Date(this.time - (num * DAY_IN_MS))
}

fun Date.dayAfter(num: Int): Date {
    return Date(this.time + (num * DAY_IN_MS))
}

fun Date.dateDiffIn(compareDate: Date, numDays: Int): Boolean {
    val d1 = this.time
    val d2 = compareDate.time
    return (d1-d2)*(-1) > (numDays * DAY_IN_MS)
}

fun Date.readableDate(): String{
    return SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(this)
}