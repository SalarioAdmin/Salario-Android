package io.salario.app.core.shared_ui.composable

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.salario.app.R

@ExperimentalComposeUiApi
@Composable
fun InfoDialog(infoType: DialogInfoType, errorText: String, onDismissPressed: () -> Unit) {
    val composition by rememberLottieComposition(
        when (infoType) {
            DialogInfoType.ErrorGeneral -> {
                LottieCompositionSpec.RawRes(R.raw.something_went_wrong_animation)
            }
            DialogInfoType.ErrorNoConnection -> {
                LottieCompositionSpec.RawRes(R.raw.no_connection_animation)
            }
            DialogInfoType.ErrorWrongCredentials -> {
                LottieCompositionSpec.RawRes(R.raw.wrong_action_animation)
            }
        }
    )

    Dialog(
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
            dismissOnBackPress = false,
            dismissOnClickOutside = false
        ),
        onDismissRequest = {
            onDismissPressed.invoke()
        },
        content = {
            Card(
                modifier = Modifier.padding(horizontal = 16.dp),
                backgroundColor = Color.White,
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    LottieAnimation(
                        iterations = LottieConstants.IterateForever,
                        composition = composition,
                        modifier = Modifier
                            .size(200.dp)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = errorText,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.subtitle2
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    CornerRoundedButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        appearance = CornerRoundedButtonAppearance.Filled,
                        text = "Ok",
                        onClick = { onDismissPressed.invoke() }
                    )
                }
            }
        }
    )
}

sealed class DialogInfoType {
    object ErrorGeneral : DialogInfoType()
    object ErrorWrongCredentials : DialogInfoType()
    object ErrorNoConnection : DialogInfoType()
}