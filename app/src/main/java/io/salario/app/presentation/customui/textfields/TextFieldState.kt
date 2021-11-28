package io.salario.app.presentation.customui.textfields

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue

@Composable
fun rememberTextFieldState(
    initialText: String,
    validate: (String) -> String? = { null }
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

    var help by mutableStateOf<String?>(null)
        private set

    fun updateText(newValue: String) {
        text = newValue
        error = null
        help = null
    }

    fun validateForHelp() {
        help = validator(text)
    }

    fun validate() {
        error = validator(text)
        help = null
    }

    companion object {
        fun Saver(
            validate: (String) -> String?,
        ) = Saver<TextFieldState, String>(
            save = { it.text },
            restore = { TextFieldState(it, validate) }
        )
    }
}