package io.salario.app.presentation.customui.textfields

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun PasswordTextField(
    modifier: Modifier = Modifier,
    state: TextFieldState,
    maxLength: Int = 14
) {
    var showPassword by remember { mutableStateOf(false) }
    val maxPasswordLength by remember { mutableStateOf(maxLength) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = state.text,
            label = { Text("Password", style = MaterialTheme.typography.body1) },
            visualTransformation = if (showPassword)
                VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            singleLine = true,
            isError = state.error != null,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "Lock icon"
                )
            },
            trailingIcon = {
                val image =
                    if (showPassword) Icons.Filled.VisibilityOff else Icons.Filled.Visibility
                IconButton(onClick = {
                    showPassword = !showPassword
                }) {
                    Icon(
                        imageVector = image,
                        contentDescription = if (showPassword)
                            "Visibility icon off" else "Visibility icon on"
                    )
                }
            },
            onValueChange = {
                if (it.length <= maxPasswordLength) {
                    state.apply {
                        updateText(it)
                        validateForHelp()
                    }
                }
            },
            keyboardActions = KeyboardActions {
                state.validate()
            }

        )

        when {
            state.error != null -> {
                Text(
                    state.error!!,
                    color = Color.Red,
                    style = MaterialTheme.typography.body2
                )
            }
            state.help != null -> {
                Text(
                    state.help!!, style = MaterialTheme.typography.body2
                )
            }
            else -> {
                Text("", style = MaterialTheme.typography.body2)
            }
        }
    }
}

@Preview
@Composable
fun PreviewPasswordTextField() {
    val passwordState = rememberTextFieldState(
        initialText = "",
        validate = { null })
    PasswordTextField(state = passwordState)
}