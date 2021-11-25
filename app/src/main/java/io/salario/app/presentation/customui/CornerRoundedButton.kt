package io.salario.app.presentation.customui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.salario.app.presentation.theme.Purple500

@Composable
fun CornerRoundedButton(
    text: String,
    appearance: CornerRoundedButtonAppearance,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    when (appearance) {
        is CornerRoundedButtonAppearance.Filled -> {
            Button(
                onClick = onClick,
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color.White
                ),
                modifier = modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = text,
                    style = MaterialTheme.typography.button
                )
            }
        }

        is CornerRoundedButtonAppearance.Outlined -> {
            OutlinedButton(
                onClick = onClick,
                border = BorderStroke(1.dp, Purple500),
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = MaterialTheme.colors.onPrimary
                ),
                modifier = modifier.fillMaxWidth()
            ) {
                Text(
                    text = text,
                    style = MaterialTheme.typography.button
                )
            }
        }
    }
}

sealed class CornerRoundedButtonAppearance {
    object Filled : CornerRoundedButtonAppearance()
    object Outlined : CornerRoundedButtonAppearance()
}