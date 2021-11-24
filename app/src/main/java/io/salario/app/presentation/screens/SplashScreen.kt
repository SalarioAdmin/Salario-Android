package io.salario.app.presentation.screens

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.salario.app.R
import io.salario.app.presentation.navigation.Destination
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {
    val scaleAnimation = remember {
        Animatable(0.1f)
    }

    LaunchedEffect(key1 = true) {
        scaleAnimation.animateTo(
            targetValue = 3f,
            animationSpec = tween(
                durationMillis = 800,
                easing = {
                    OvershootInterpolator(2f).getInterpolation(it)
                }
            ))
        delay(300L)
        navController.navigate(Destination.IntroDestination.route) {
            popUpTo(Destination.SplashDestination.route) {
                inclusive = true
            }
        }
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(brush = Brush.verticalGradient(
                colors = listOf(
                    MaterialTheme.colors.primaryVariant,
                    MaterialTheme.colors.primary
                )
            ))
    ) {
        Image(
            painter = painterResource(id = R.drawable.designed_salario_logo),
            contentDescription = stringResource(R.string.splash_screen_icon_content_description),
            Modifier.scale(scaleAnimation.value)
        )
    }
}
