package io.salario.app.presentation.customui.textfields

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue

@Composable
fun rememberTextFieldState(
    initialText: String,
    validate: (String) -> String? = { null },
): TextFieldState {
    return rememberSaveable(saver = TextFieldState.Saver(validate)) {
        TextFieldState(initialText, validate)
    }
}

class TextFieldState(
    initialText: String,
    private val validator: (String) -> String?,
) {
    var text by mutableStateOf(initialText)
        private set

    var error by mutableStateOf<String?>(null)
        private set

    fun updateText(newValue: String) {
        text = newValue
        error = null
    }

    fun validate() {
        error = validator(text)
    }

    companion object {
        fun Saver(
            validate: (String) -> String?,
        ) = androidx.compose.runtime.saveable.Saver<TextFieldState, String>(
            save = { it.text },
            restore = { TextFieldState(it, validate) }
        )
    }
}