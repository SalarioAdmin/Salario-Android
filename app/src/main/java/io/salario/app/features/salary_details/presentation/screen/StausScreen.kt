package io.salario.app.features.salary_details.presentation.screen

import android.util.Base64
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import io.salario.app.core.shared_ui.composable.*
import io.salario.app.features.salary_details.domain.model.Paycheck
import io.salario.app.features.salary_details.presentation.viewmodel.StatusViewModel

@ExperimentalPermissionsApi
@ExperimentalComposeUiApi
@Composable
fun StatusScreen(
    navController: NavController,
    viewModel: StatusViewModel = hiltViewModel()
) {
    BackHandler(enabled = true) {
        viewModel.setExitDialogVisibility(visible = false)
    }

    viewModel.statusState.apply {
        StatusScreenContent(
            paychecks = paychecks,
            showLoadingDialog = isLoading,
            showUploadSuccessDialog = showUploadSuccessDialog,
            showExitDialog = showExitDialog,
            onDismissPressed = {
                if (showExitDialog) {
                    viewModel.setExitDialogVisibility(visible = false)
                }

                if (showUploadSuccessDialog) {
                    viewModel.setUploadSuccessDialogVisibility(visible = false)
                }
            },
            onExitPressed = {
                navController.popBackStack()
            },
            onUploadPdfRequested = {
                when (it) {
                    is FilePickerResult.FilePickerSuccess -> {
                        val pdfBytes = it.result
                        val pdfBase64 = Base64.encodeToString(pdfBytes, Base64.DEFAULT)
                        viewModel.onUploadPaycheck(pdfData = pdfBase64)
                    }
                    is FilePickerResult.FilePickerFailed -> {
                        Log.e("Error", it.message!!) // TODO
                    }
                }
            },
            onLoadPaychecksClicked = {
                viewModel.getUserPaychecks()
            }
        )
    }
}

@ExperimentalPermissionsApi
@ExperimentalComposeUiApi
@Composable
fun StatusScreenContent(
    paychecks: List<Paycheck>?,
    showLoadingDialog: Boolean,
    showUploadSuccessDialog: Boolean,
    showExitDialog: Boolean,
    onExitPressed: () -> Unit,
    onDismissPressed: () -> Unit,
    onUploadPdfRequested: (filePickerResult: FilePickerResult) -> Unit,
    onLoadPaychecksClicked: () -> Unit // TODO temp
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (showLoadingDialog) {
            LoadingDialog(loadingType = DialogLoadingType.ScanningFile)
        }

        if (showUploadSuccessDialog) {
            InfoDialog(
                infoType = DialogInfoType.InfoValidationEmailSent,
                "Upload success!",
                "Your can see the extracted data on the App.",
                onDismissPressed = onDismissPressed
            )
        }

        if (showExitDialog) {
            ExitAlertDialog(onExitPressed, onDismissPressed)
        }

        paychecks?.let {
            LazyColumn {
                items(it) {
                    Column(
                        Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
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

        PickPdfFile {
            onUploadPdfRequested(it)
        }

        Button(onClick = { onLoadPaychecksClicked.invoke() }) {
            Text(text = "Load paychecks")
        }
    }
}
