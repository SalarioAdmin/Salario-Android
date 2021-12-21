package io.salario.app.core.customui.composable

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.salario.app.R

@ExperimentalComposeUiApi
@Composable
fun ExitAlertDialog(onExitPressed: () -> Unit, onDismissPressed: () -> Unit) {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.exit_door_animation)
    )

    AlertDialog(
        modifier = Modifier.padding(horizontal = 24.dp),
        properties = DialogProperties(usePlatformDefaultWidth = false),
        shape = RoundedCornerShape(30.dp),
        onDismissRequest = {
            onDismissPressed.invoke()
        },
        title = {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                LottieAnimation(
                    iterations = LottieConstants.IterateForever,
                    composition = composition,
                    modifier = Modifier
                        .size(200.dp)
                )
            }
        },
        text = {
            Text(
                text = "Do you want to Leave The App?",
                textAlign = TextAlign.Center,
                fontSize = 24.sp
            )
        },
        buttons = {
            Row(
                modifier = Modifier.padding(all = 24.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                CornerRoundedButton(
                    modifier = Modifier.weight(0.5f),
                    appearance = CornerRoundedButtonAppearance.Outlined,
                    text = "Cancel",
                    onClick = { onDismissPressed.invoke() }
                )

                Spacer(modifier = Modifier.size(8.dp))

                CornerRoundedButton(
                    modifier = Modifier.weight(0.5f),
                    appearance = CornerRoundedButtonAppearance.Filled,
                    text = "Exit",
                    onClick = { onExitPressed.invoke() }
                )
            }
        }
    )
}