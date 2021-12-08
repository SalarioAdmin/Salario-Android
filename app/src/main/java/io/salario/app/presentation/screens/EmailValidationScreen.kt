package io.salario.app.presentation.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.airbnb.lottie.compose.*
import com.salario.app.R
import io.salario.app.data.utils.Status
import io.salario.app.presentation.customui.buttons.CornerRoundedButton
import io.salario.app.presentation.customui.buttons.CornerRoundedButtonAppearance
import io.salario.app.presentation.customui.textfields.SampleTextField
import io.salario.app.presentation.customui.textfields.rememberTextFieldState
import io.salario.app.presentation.navigation.Destination
import io.salario.app.presentation.viewmodels.AuthenticationViewModel
import kotlinx.coroutines.flow.collect

@Composable
fun EmailValidationScreen(navController: NavController, authViewModel: AuthenticationViewModel) {
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        authViewModel.userValidationResult.collect {
            when (it.status) {
                is Status.Success -> {
                    navController.navigate(Destination.StatusDestination.route) {
                        popUpTo(Destination.EmailValidationDestination.route) {
                            inclusive = true
                        }
                    }
                }
                is Status.Loading -> {
                    Toast
                        .makeText(context, "Loading...", Toast.LENGTH_SHORT)
                        .show()
                }
                is Status.Error -> {
                    Toast
                        .makeText(context, it.message, Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    val composition by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(R.raw.lottie_send_email),
    )

    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "A Validation token has sent to your email.",
            style = MaterialTheme.typography.h1,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.fillMaxWidth().height(16.dp))

        Text(
            text = "Please enter the token and press 'Validate email'",
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        LottieAnimation(
            composition = composition,
            progress = progress,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.40f)
        )

        val tokenState = rememberTextFieldState(
            initialText = "",
            validate = { email ->
                when {
                    email.isEmpty() || email.isBlank() -> "Token should not be empty."
                    else -> null
                }
            })

        SampleTextField(
            modifier = Modifier.fillMaxWidth(),
            label = "Token",
            state = tokenState,
            maxLength = 150)

        CornerRoundedButton(
            modifier = Modifier.padding(16.dp),
            text = "Validate email",
            appearance = CornerRoundedButtonAppearance.Filled,
            onClick = {
                tokenState.validate()

                if (tokenState.error == null) {
                    authViewModel.validateNewUser(
                        token = tokenState.text,
                        email = authViewModel.getConnectedUser()!!.email
                    )
                }
            }
        )
    }
}

@Preview
@Composable
fun PreviewEmailValidationScreen() {
    EmailValidationScreen(
        navController = NavController(LocalContext.current),
        authViewModel = AuthenticationViewModel()
    )
}