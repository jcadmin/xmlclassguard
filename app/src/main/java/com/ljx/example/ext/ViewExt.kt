package com.ljx.example.ext

import android.view.View

/**
 *  author : Andy Xuuu
 *  date : 2021/8/9
 *  description :
 */
inline fun View.setOnSingleClickListener(
    delayMillis: Long = 500L, crossinline onClick: () -> Unit
) {
    this.setOnClickListener {
        this.isClickable = false
        onClick()
        this.postDelayed({
            isClickable = true
        }, delayMillis)
    }
}
   