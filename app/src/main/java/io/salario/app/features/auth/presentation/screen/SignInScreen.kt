package io.salario.app.features.auth.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.salario.app.R
import io.salario.app.core.model.UIError
import io.salario.app.core.navigation.Destination
import io.salario.app.core.navigation.FEATURES_GRAPH_ROUTE
import io.salario.app.core.shared_ui.composable.*
import io.salario.app.core.shared_ui.state_holder.TextFieldState
import io.salario.app.features.auth.presentation.viewmodel.SignInViewModel
import kotlinx.coroutines.delay

@ExperimentalComposeUiApi
@Composable
fun SignInScreen(navController: NavController, viewModel: SignInViewModel = hiltViewModel()) {
    viewModel.signInState.apply {
        if (signInSuccess) {
            WelcomeDialog()
            LaunchedEffect(key1 = signInSuccess) {
                delay(3000L)
                navController.navigate(FEATURES_GRAPH_ROUTE) {
                    popUpTo(Destination.SignInDestination.route) {
                        inclusive = true
                    }
                }
            }
        }

        SignInScreenContent(
            isLoading = isLoading,
            error = error,
            onErrorDialogDismiss = {
                viewModel.clearError()
            },
            emailInputFieldState = emailInputState,
            passwordInputFieldState = passwordInputState,
            onSignInPressed = {
                emailInputState.validate()
                passwordInputState.validate()

                if (emailInputState.hasNoError() && passwordInputState.hasNoError()) {
                    viewModel.onSignIn(emailInputState.text, passwordInputState.text)
                }
            },
            onForgotPasswordPressed = {
                emailInputState.apply {
                    validate()
                    if (hasNoError()) {
                        viewModel.onResetPassword(text)
                    }
                }
            },
            onSignUpPressed = {
                navController.navigate(Destination.SignUpDestination.route) {
                    popUpTo(Destination.SignInDestination.route) {
                        inclusive = true
                    }
                }
            }
        )
    }
}

@ExperimentalComposeUiApi
@Composable
fun SignInScreenContent(
    isLoading: Boolean,
    error: UIError,
    onErrorDialogDismiss: () -> Unit,
    emailInputFieldState: TextFieldState,
    passwordInputFieldState: TextFieldState,
    onSignInPressed: () -> Unit,
    onForgotPasswordPressed: () -> Unit,
    onSignUpPressed: () -> Unit
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxHeight()
            .background(MaterialTheme.colors.background)
    ) {
        val (welcomeCard,
            emailTextField,
            passwordTextField,
            signInBtn,
            forgotPasswordLink,
            thirdPartySignInLayout,
            signUpBtn) = createRefs()

        if (isLoading) {
            LoadingDialog(DialogLoadingType.Identification)
        }

        if (error.isActive) {
            InfoDialog(error.dialogType, error.text, onDismissPressed = onErrorDialogDismiss)
        }

        WelcomeCard(
            modifier = Modifier.constrainAs(welcomeCard) {
                top.linkTo(parent.top)
            },
            title = stringResource(id = R.string.sign_in_title),
            subtitle = stringResource(id = R.string.sign_in_subtitle),
            bgColor = MaterialTheme.colors.background
        )

        EmailTextField(
            modifier = Modifier.constrainAs(emailTextField) {
                top.linkTo(welcomeCard.bottom, margin = 42.dp)
            },
            state = emailInputFieldState
        )

        PasswordTextField(
            modifier = Modifier.constrainAs(passwordTextField) {
                top.linkTo(emailTextField.bottom, margin = 8.dp)
            },
            state = passwordInputFieldState
        )

        CornerRoundedButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .constrainAs(signInBtn) {
                    top.linkTo(passwordTextField.bottom, margin = 16.dp)
                },
            text = "Sign In",
            appearance = CornerRoundedButtonAppearance.Filled,
            onClick = {
                onSignInPressed()
            }
        )

        Text(
            text = "Forgot password",
            color = MaterialTheme.colors.primary,
            modifier = Modifier
                .constrainAs(forgotPasswordLink) {
                    top.linkTo(signInBtn.bottom, margin = 16.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .clickable {
                    onForgotPasswordPressed()
                }
        )

        CornerRoundedButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .constrainAs(signUpBtn) {
                    bottom.linkTo(parent.bottom, margin = 16.dp)
                },
            text = "Sign Up",
            appearance = CornerRoundedButtonAppearance.Outlined,
            onClick = {
                onSignUpPressed()
            }
        )
    }
}

@ExperimentalComposeUiApi
@Preview
@Composable
fun PreviewSignInScreen() {
    SignInScreen(navController = NavController(LocalContext.current))
}