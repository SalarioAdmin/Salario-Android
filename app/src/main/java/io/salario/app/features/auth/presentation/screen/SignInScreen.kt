package io.salario.app.features.auth.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.salario.app.R
import io.salario.app.core.customui.composable.*
import io.salario.app.core.customui.state_holder.TextFieldState
import io.salario.app.core.navigation.Destination
import io.salario.app.features.auth.presentation.viewmodel.SignInViewModel

@Composable
fun SignInScreen(navController: NavController, viewModel: SignInViewModel = hiltViewModel()) {
    val state = viewModel.signInState

    // TODO what to do when forgot password success

    // TODO understand how to implement proper navigation once.
    LaunchedEffect(key1 = true) {
        if (state.shouldNavigateForward) {
            navController.navigate(Destination.StatusDestination.route) {
                popUpTo(Destination.SignInDestination.route) {
                    inclusive = true
                }
            }
        }
    }

    SignInScreenContent(
        isLoading = state.isLoading,
        errorMessage = state.errorMessage,
        onDialogDismissed = { state.errorMessage = null },
        emailInputFieldState = state.emailInputState,
        passwordInputFieldState = state.passwordInputState,
        onSignInPressed = {
            state.apply {
                emailInputState.validate()
                passwordInputState.validate()

                if (emailInputState.hasNoError() && passwordInputState.hasNoError()) {
                    viewModel.onSignIn(emailInputState.text, passwordInputState.text)
                }
            }
        },
        onForgotPasswordPressed = {
            state.emailInputState.apply {
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

@Composable
fun SignInScreenContent(
    isLoading: Boolean,
    errorMessage: String?,
    emailInputFieldState: TextFieldState,
    passwordInputFieldState: TextFieldState,
    onDialogDismissed: () -> Unit,
    onSignInPressed: () -> Unit,
    onForgotPasswordPressed: () -> Unit,
    onSignUpPressed: () -> Unit
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxHeight()
            .background(MaterialTheme.colors.background)
    ) {
        val (loadingProgressBar,
            welcomeCard,
            emailTextField,
            passwordTextField,
            signInBtn,
            forgotPasswordLink,
            thirdPartySignInLayout,
            signUpBtn) = createRefs()

        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .constrainAs(loadingProgressBar) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    })
        }

//        errorMessage?.let {
//            AlertDialog(
//                properties = DialogProperties(
//                    dismissOnBackPress = false,
//                    dismissOnClickOutside = true
//                ),
//                onDismissRequest = { onDialogDismissed.invoke() },
//                title = { Text(text = "Oops...") },
//                text = { Text(it) },
//                confirmButton = { },
//                dismissButton = {
//                    CornerRoundedButton(
//                        text = "Close",
//                        appearance = CornerRoundedButtonAppearance.Outlined,
//                        onClick = { onDialogDismissed.invoke() }
//                    )
//                },
//            )
//        }

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
                .constrainAs(signInBtn) {
                    top.linkTo(passwordTextField.bottom, margin = 16.dp)
                }
                .padding(horizontal = 16.dp),
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
                .constrainAs(signUpBtn) {
                    bottom.linkTo(parent.bottom, margin = 16.dp)
                }
                .padding(horizontal = 16.dp),
            text = "Sign Up",
            appearance = CornerRoundedButtonAppearance.Outlined,
            onClick = {
                onSignUpPressed()
            }
        )
    }
}

@Preview
@Composable
fun PreviewSignInScreen() {
    SignInScreen(navController = NavController(LocalContext.current))
}