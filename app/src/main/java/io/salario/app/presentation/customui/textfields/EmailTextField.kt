package io.salario.app.presentation.customui.textfields

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun EmailTextField(
    modifier: Modifier = Modifier,
    state: TextFieldState,
    maxLength: Int = 30
) {
    val maxPasswordLength by remember { mutableStateOf(maxLength) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = state.text,
            label = { Text("Email", style = MaterialTheme.typography.body1) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            singleLine = true,
            isError = state.error != null,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = "Email icon"
                )
            },
            onValueChange = {
                if (it.length <= maxPasswordLength) {
                    state.updateText(it)
                }
            }
        )

        state.error?.let {
            Text(it, color = Color.Red, style = MaterialTheme.typography.body2)
        } ?: run {
            Text("", style = MaterialTheme.typography.body2)
        }
    }
}

@Preview
@Composable
fun PreviewEmailTextField() {
    val emailState = rememberTextFieldState(
        initialText = "",
        validate = { null })
    EmailTextField(state = emailState)
}