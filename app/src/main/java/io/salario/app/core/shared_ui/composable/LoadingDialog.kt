package io.salario.app.core.shared_ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
fun LoadingDialog(loadingType: LoadingDialogType) {
    val composition by rememberLottieComposition(
        when (loadingType) {
            LoadingDialogType.General -> {
                LottieCompositionSpec.RawRes(R.raw.loading_animation)
            }
            LoadingDialogType.Identification -> {
                LottieCompositionSpec.RawRes(R.raw.identification_loading_animation)
            }
            LoadingDialogType.ScanningFile -> {
                LottieCompositionSpec.RawRes(R.raw.scanning_file_animation)
            }
        }
    )

    Dialog(
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
            dismissOnBackPress = false,
            dismissOnClickOutside = false
        ),
        onDismissRequest = {},
        content = {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Transparent)
            ) {
                LottieAnimation(
                    iterations = LottieConstants.IterateForever,
                    composition = composition,
                    modifier = Modifier.size(200.dp)
                )
            }
        }
    )
}

sealed class LoadingDialogType {
    object Identification : LoadingDialogType()
    object General : LoadingDialogType()
    object ScanningFile : LoadingDialogType()
}