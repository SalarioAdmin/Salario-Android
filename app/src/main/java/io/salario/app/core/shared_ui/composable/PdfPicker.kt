package io.salario.app.core.shared_ui.composable

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState

@ExperimentalPermissionsApi
@Composable
fun PickPdfFile(
    onFilePickerResult: (result: FilePickerResult) -> Unit
) {
    val context = LocalContext.current

    // TODO Check if needed on lower version of Android
    val readPermissionState = rememberPermissionState(
        android.Manifest.permission.READ_EXTERNAL_STORAGE
    )

    var fileUri by remember {
        mutableStateOf<Uri?>(null)
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri: Uri? ->
        fileUri = uri
    }

    Column {
        // TODO Check if needed on lower version of Android
//        Button(onClick = readPermissionState::launchPermissionRequest) {
//            Text("request read permission")
//        }
//
//        PermissionRequired(readPermissionState, {}, {}) {
//            Button(onClick = {
//                launcher.launch(arrayOf("application/pdf"))
//            }) {
//                Text(text = "Pick pdf file")
//            }
//        }

        Button(onClick = {
            launcher.launch(arrayOf("application/pdf"))
        }) {
            Text(text = "Pick pdf file")
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