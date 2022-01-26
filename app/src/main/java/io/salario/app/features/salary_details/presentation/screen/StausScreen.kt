package io.salario.app.features.salary_details.presentation.screen

import android.util.Base64
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import io.salario.app.core.shared_ui.composable.*
import io.salario.app.features.salary_details.presentation.event.StatusEvent
import io.salario.app.features.salary_details.presentation.viewmodel.StatusViewModel

@ExperimentalPermissionsApi
@ExperimentalComposeUiApi
@Composable
fun StatusScreen(
    navController: NavController,
    viewModel: StatusViewModel = hiltViewModel()
) {
    val paychecks by viewModel.paychecks.collectAsState(initial = emptyList())

    BackHandler(enabled = true) {
        viewModel.onEvent(StatusEvent.OnBackPressed)
    }

    if (viewModel.showExitDialog) {
        ExitAlertDialog(
            onExitPressed = { viewModel.onEvent(StatusEvent.OnExitPressed) },
            onDismissPressed = { viewModel.onEvent(StatusEvent.OnDialogDismiss) }
        )
    }

    if (viewModel.loadingDialogConfig.isActive) {
        LoadingDialog(viewModel.loadingDialogConfig.loadingType)
    }

    if (viewModel.infoDialogConfig.isActive) {
        InfoDialog(
            infoType = viewModel.infoDialogConfig.infoType,
            title = viewModel.infoDialogConfig.title,
            subtitle = viewModel.infoDialogConfig.subtitle,
            onDismissPressed = {
                viewModel.onEvent(StatusEvent.OnDialogDismiss)
            }
        )
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        PickPdfFile {
            when (it) {
                is FilePickerResult.FilePickerSuccess -> {
                    val pdfBytes = it.result
                    val pdfBase64 = Base64.encodeToString(pdfBytes, Base64.DEFAULT)
                    viewModel.onEvent(StatusEvent.OnFilePicked(pdfBase64))
                }
                is FilePickerResult.FilePickerFailed -> {
                    Log.e("Status screen", it.message!!) // TODO
                }
            }
        }

        Button(onClick = { viewModel.onEvent(StatusEvent.OnLoadPaychecksRequest) }) {
            Text(text = "Load paychecks")
        }

        LazyColumn {
            items(paychecks) {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .background(Color.LightGray),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(text = it.period, textAlign = TextAlign.Center)
                    Text(text = it.name)
                    Text(text = it.id)
                    Text(text = it.address)
                    Text(text = it.paymentNetAmount.toString())
                    Text(text = it.company)
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}
