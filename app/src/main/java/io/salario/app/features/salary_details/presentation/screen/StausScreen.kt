package io.salario.app.features.salary_details.presentation.screen

import android.util.Base64
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        if (paychecks.isNotEmpty()) {
            Spacer(modifier = Modifier.height(24.dp))
            Text(text = paychecks[0].name)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = paychecks[0].company)
            Spacer(modifier = Modifier.height(24.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.LightGray, RoundedCornerShape(16.dp))
                    .padding(horizontal = 16.dp)
            ) {
                Spacer(modifier = Modifier
                    .fillMaxWidth()
                    .height(32.dp))
                Text(
                    text = "נטו",
                    style = MaterialTheme.typography.h1
                )
                Spacer(modifier = Modifier
                    .fillMaxWidth()
                    .height(16.dp))
                Text(
                    text = "${paychecks[0].paymentNetAmount}₪",
                    style = MaterialTheme.typography.subtitle1
                )
                Spacer(modifier = Modifier
                    .fillMaxWidth()
                    .height(32.dp))
            }
        }
        Spacer(modifier = Modifier
            .fillMaxWidth()
            .height(16.dp))
        Button(onClick = { viewModel.onEvent(StatusEvent.OnLoadPaychecksRequest) }) {
            Text(text = "טען נתונים")
        }

        PickPdfFile("Upload paycheck") {
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
    }
}
