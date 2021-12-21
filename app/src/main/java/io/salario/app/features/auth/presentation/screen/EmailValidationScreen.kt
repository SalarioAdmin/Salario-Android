package io.salario.app.features.auth.presentation.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.airbnb.lottie.LottieComposition
import com.airbnb.lottie.compose.*
import com.salario.app.R
import io.salario.app.core.customui.composable.CornerRoundedButton
import io.salario.app.core.customui.composable.CornerRoundedButtonAppearance
import io.salario.app.core.customui.composable.EmailTextField
import io.salario.app.core.customui.composable.SampleTextField
import io.salario.app.core.customui.state_holder.TextFieldState
import io.salario.app.core.navigation.Destination
import io.salario.app.features.auth.presentation.viewmodel.EmailValidationViewModel

@Composable
fun EmailValidationScreen(
    navController: NavController,
    viewModel: EmailValidationViewModel = hiltViewModel()
) {
    viewModel.emailValidationState.apply {
        if (shouldNavigateForward) {
            LaunchedEffect(key1 = shouldNavigateForward) {
                navController.navigate(Destination.SignInDestination.route) {
                    popUpTo(Destination.EmailValidationDestination.route) {
                        inclusive = true
                    }
                }
            }
        }

        val composition by rememberLottieComposition(
            spec = LottieCompositionSpec.RawRes(R.raw.send_email_animation),
        )

        val progress by animateLottieCompositionAsState(
            composition = composition,
            iterations = LottieConstants.IterateForever
        )

        EmailValidationScreenContent(
            isLoading = isLoading,
            lottieComposition = composition,
            lottieProgress = progress,
            emailInputFieldState = emailInputState,
            tokenInputFieldState = tokenInputState
        ) {
            tokenInputState.validate()
            emailInputState.validate()

            if (emailInputState.hasNoError() && tokenInputState.hasNoError()) {
                viewModel.onValidateUserCreation(
                    email = emailInputState.text,
                    userCreationValidationToken = tokenInputState.text
                )
            }
        }
    }
}

@Composable
fun EmailValidationScreenContent(
    isLoading: Boolean,
    lottieComposition: LottieComposition?,
    lottieProgress: Float,
    emailInputFieldState: TextFieldState,
    tokenInputFieldState: TextFieldState,
    onValidatePressed: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (isLoading) {
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
            composition = lottieComposition,
            progress = lottieProgress,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.40f)
        )

        EmailTextField(
            modifier = Modifier.padding(4.dp),
            state = emailInputFieldState
        )

        SampleTextField(
            modifier = Modifier.fillMaxWidth(),
            label = "Token",
            state = tokenInputFieldState
        )

        CornerRoundedButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            text = "Validate email",
            appearance = CornerRoundedButtonAppearance.Filled,
            onClick = {
                onValidatePressed()
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