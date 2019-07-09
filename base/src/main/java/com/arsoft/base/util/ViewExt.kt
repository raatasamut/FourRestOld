package com.arsoft.base.util

import android.view.MotionEvent
import android.view.View
import android.widget.LinearLayout

fun View.alphaClick(): View {
    setOnTouchListener { v, event ->
        v.alpha = if (event.action == MotionEvent.ACTION_UP) 1.0f else 0.5f
        false
    }
    return this
}

fun View.fixedWeight(): View {
    layoutParams = LinearLayout.LayoutParams(
        LinearLayout.LayoutParams.MATCH_PARENT,
        LinearLayout.LayoutParams.WRAP_CONTENT, 1f
    )
    return this
}