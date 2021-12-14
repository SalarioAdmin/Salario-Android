package io.salario.app.core.customui.state_holder

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class TextFieldState(
    initialText: String = "",
    private val validate: (String) -> String?,
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
        help = validate(text)
    }

    fun validate() {
        error = validate(text)
        help = null
    }

    fun hasNoError(): Boolean {
        return error == null
    }
}