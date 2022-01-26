package io.salario.app.features.splash_screen.presentation.event

import android.annotation.SuppressLint

@SuppressLint("CustomSplashScreen")
sealed class SplashScreenEvent {
    object OnAnimationFinished : SplashScreenEvent()
}
