package io.salario.app.core.customui.textfields

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SampleTextField(
    modifier: Modifier = Modifier,
    state: TextFieldState,
    label: String,
    maxLength: Int = 20
) {
    Column(
        modifier = modifier.padding(horizontal = 16.dp)
    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            value = state.text,
            label = { Text(label, style = MaterialTheme.typography.body1) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            singleLine = true,
            isError = state.error != null,
            onValueChange = {
                if (it.length <= maxLength) {
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
fun PreviewSampleTextField() {
    val textState = rememberTextFieldState(
        initialText = "",
        validate = { null })
    EmailTextField(state = textState)
}