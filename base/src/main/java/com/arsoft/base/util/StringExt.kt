package com.arsoft.base.util


fun String.isEmail(): Boolean {
    return Regex("\\b[\\w.%-]+@[-.\\w]+\\.[A-Za-z]{2,4}\\b").matches(this)
}

fun String.isCID(): Boolean {
    if (this.length != 13) return false
    if (!this.matches("-?\\d+(\\.\\d+)?".toRegex())) return false

    var sum = 0
    for (i in 0..11) {
        sum += this[i].toString().toInt() * (13 - i)
    }

    val mod = sum % 11
    val minus = 11 - mod

    return minus.toString().last().toString().toInt() == this.last().toString().toInt()
}

