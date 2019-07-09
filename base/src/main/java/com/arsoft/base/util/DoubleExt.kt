package com.arsoft.base.util

import java.text.DecimalFormat

fun Long.withComma() = DecimalFormat("#,###,###.00").format(this)