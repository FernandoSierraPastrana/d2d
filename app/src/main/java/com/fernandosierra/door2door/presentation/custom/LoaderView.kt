package com.fernandosierra.door2door.presentation.custom

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.util.AttributeSet
import com.airbnb.lottie.LottieAnimationView

class LoaderView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : LottieAnimationView(context, attrs, defStyleAttr) {

    fun cancelAnimation(callback: (() -> Unit)?) {
        if (callback == null) {
            cancelAnimation()
        } else {
            val animatorListener = object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    removeAnimatorListener(this)
                    callback.invoke()
                }
            }
            loop(false)
            addAnimatorListener(animatorListener)
        }
    }
}