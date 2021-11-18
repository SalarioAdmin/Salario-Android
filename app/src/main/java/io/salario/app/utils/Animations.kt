package io.salario.app.utils

import android.view.View
import android.view.animation.AnimationUtils
import com.salario.app.R

fun shakeView(view: View) {
    val anim = AnimationUtils.loadAnimation(view.context, R.anim.shake_animation)
    view.startAnimation(anim)
}