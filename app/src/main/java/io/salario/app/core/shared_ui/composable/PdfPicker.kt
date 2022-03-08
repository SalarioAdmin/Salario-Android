package io.salario.app.core.shared_ui.composable

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@Composable
fun PickPdfFile(
    buttonText: String,
    onFilePickerResult: (result: FilePickerResult) -> Unit
) {
    val context = LocalContext.current

    var fileUri by remember {
        mutableStateOf<Uri?>(null)
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri: Uri? ->
        fileUri = uri
    }

    Column {
        FloatingActionButton(
            backgroundColor = MaterialTheme.colors.primary,
            onClick = {
                launcher.launch(arrayOf("application/pdf"))
            }
        ) {
            Text(
                text = buttonText,
                style = MaterialTheme.typography.subtitle2
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        LaunchedEffect(key1 = fileUri) {
            fileUri?.let { uri ->
                val stream = context.applicationContext.contentResolver.openInputStream(uri)
                stream?.readBytes()?.let {
                    onFilePickerResult(FilePickerResult.FilePickerSuccess(it))
                    fileUri = null
                } ?: run {
                    onFilePickerResult(FilePickerResult.FilePickerFailed("Something went wrong"))
                    fileUri = null
                }
            } ?: run {
                onFilePickerResult(FilePickerResult.FilePickerFailed("Something went wrong"))
                fileUri = null
            }
        }
    }
}

sealed class FilePickerResult(val result: ByteArray? = null, val message: String? = null) {
    class FilePickerSuccess(result: ByteArray) : FilePickerResult(result = result)
    class FilePickerFailed(message: String) : FilePickerResult(message = message)
}