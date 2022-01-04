package io.salario.app.core.shared_ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import io.salario.app.R

@ExperimentalComposeUiApi
@Composable
fun WelcomeDialog() {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.success_animation)
    )

    Dialog(
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
            dismissOnBackPress = false,
            dismissOnClickOutside = false
        ),
        onDismissRequest = {},
        content = {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colors.background)
            ) {
                LottieAnimation(
                    iterations = LottieConstants.IterateForever,
                    composition = composition,
                    modifier = Modifier.size(200.dp)
                )
                Text(
                    text = "Welcome!",
                    style = MaterialTheme.typography.h1,
                    color = MaterialTheme.colors.primary
                )
            }
        }
    )
}
