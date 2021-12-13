package io.salario.app.features.auth.presentation.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.airbnb.lottie.compose.*
import com.salario.app.R
import io.salario.app.core.customui.buttons.CornerRoundedButton
import io.salario.app.core.customui.buttons.CornerRoundedButtonAppearance
import io.salario.app.core.customui.textfields.EmailTextField
import io.salario.app.core.customui.textfields.SampleTextField
import io.salario.app.core.customui.textfields.rememberTextFieldState
import io.salario.app.core.navigation.Destination
import io.salario.app.core.util.isValidEmail
import io.salario.app.features.auth.presentation.viewmodel.AuthViewModel

@Composable
fun EmailValidationScreen(navController: NavController, viewModel: AuthViewModel) {
    if (viewModel.emailValidationState.value.shouldNavigateToStatus) {
        navController.navigate(Destination.StatusDestination.route) {
            popUpTo(Destination.EmailValidationDestination.route) {
                inclusive = true
            }
        }
        viewModel.emailValidationState.value.shouldNavigateToStatus = false
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
        if (viewModel.emailValidationState.value.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        }

        Text(
            text = "A Validation token has sent to your email.",
            style = MaterialTheme.typography.h1,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(16.dp)
        )

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

        val emailState = rememberTextFieldState(
            initialText = "",
            validate = { email ->
                when {
                    email.isEmpty() || email.isBlank() -> "Email should not be empty."
                    !isValidEmail(email) -> "Please enter a valid Email address."
                    else -> null
                }
            })

        EmailTextField(
            modifier = Modifier.padding(4.dp),
            state = emailState
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
            maxLength = 500
        )

        CornerRoundedButton(
            modifier = Modifier.padding(16.dp),
            text = "Validate email",
            appearance = CornerRoundedButtonAppearance.Filled,
            onClick = {
                tokenState.validate()
                emailState.validate()

                if (tokenState.error == null && emailState.error == null) {
                    viewModel.onValidateUserCreation(
                        email = emailState.text,
                        userCreationValidationToken = tokenState.text
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
        viewModel = hiltViewModel()
    )
}