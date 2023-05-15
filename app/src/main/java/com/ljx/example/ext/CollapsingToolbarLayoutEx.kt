package com.ljx.example.ext

import android.content.Context
import android.util.AttributeSet
import com.google.android.material.appbar.CollapsingToolbarLayout

class CollapsingToolbarLayoutEx : CollapsingToolbarLayout {
    private var onScrimsShownListener: OnScrimsListener? = null

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    fun setOnScrimsShownListener(onScrimsListener: OnScrimsListener?) {
        onScrimsShownListener = onScrimsListener
    }

    override fun setScrimsShown(shown: Boolean, animate: Boolean) {
        super.setScrimsShown(shown, animate)
        onScrimsShownListener?.onScrimsStateChange(shown)
    }

    /**
     * CollapsingToolbarLayout渐变监听器
     */
    interface OnScrimsListener {
        /**
         * 渐变状态变化
         *
         * @param shown 渐变开关
         */
        fun onScrimsStateChange(shown: Boolean)
    }
}     