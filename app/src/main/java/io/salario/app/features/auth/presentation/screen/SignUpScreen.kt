package io.salario.app.features.auth.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
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
import io.salario.app.features.auth.presentation.viewmodel.SignUpViewModel
import kotlinx.coroutines.delay

@ExperimentalComposeUiApi
@Composable
fun SignUpScreen(navController: NavController, viewModel: SignUpViewModel = hiltViewModel()) {
    viewModel.signUpState.apply {
        if (signUpSuccess) {
            WelcomeDialog()
            LaunchedEffect(key1 = signUpSuccess) {
                delay(3000L)
                navController.navigate(FEATURES_GRAPH_ROUTE) {
                    popUpTo(Destination.SignUpDestination.route) {
                        inclusive = true
                    }
                }
            }
        }

        SignUpScreenContent(
            isLoading = isLoading,
            error = error,
            onErrorDialogDismiss = {
                viewModel.clearError()
            },
            firstNameInputFieldState = firstNameInputState,
            lastNameInputFieldState = lastNameInputState,
            emailInputFieldState = emailInputState,
            passwordInputFieldState = passwordInputState,
            onSignUpPressed = {
                firstNameInputState.validate()
                lastNameInputState.validate()
                emailInputState.validate()
                passwordInputState.validate()

                if (firstNameInputState.hasNoError() &&
                    lastNameInputState.hasNoError() &&
                    emailInputState.hasNoError() &&
                    passwordInputState.hasNoError()
                ) {
                    viewModel.onSignUp(
                        firstNameInputState.text,
                        lastNameInputState.text,
                        emailInputState.text,
                        passwordInputState.text
                    )
                }
            },
            onSignInPressed = {
                navController.navigate(Destination.SignInDestination.route) {
                    popUpTo(Destination.SignUpDestination.route) {
                        inclusive = true
                    }
                }
            }
        )
    }
}

@ExperimentalComposeUiApi
@Composable
fun SignUpScreenContent(
    isLoading: Boolean,
    error: UIError,
    onErrorDialogDismiss: () -> Unit,
    firstNameInputFieldState: TextFieldState,
    lastNameInputFieldState: TextFieldState,
    emailInputFieldState: TextFieldState,
    passwordInputFieldState: TextFieldState,
    onSignUpPressed: () -> Unit,
    onSignInPressed: () -> Unit
) {

    ConstraintLayout(
        modifier = Modifier
            .fillMaxHeight()
            .background(MaterialTheme.colors.background)
    ) {
        val (welcomeCard,
            emailTextField,
            nameLayout,
            passwordTextField,
            signUpBtn,
            thirdPartySignInLayout,
            signInBtn) = createRefs()

        if (isLoading) {
            LoadingDialog(DialogLoadingType.General)
        }

        if (error.isActive) {
            InfoDialog(error.dialogType, error.text, onDismissPressed = onErrorDialogDismiss)
        }

        WelcomeCard(
            modifier = Modifier.constrainAs(welcomeCard) {
                top.linkTo(parent.top)
            },
            title = stringResource(id = R.string.sign_up_title),
            subtitle = stringResource(id = R.string.sign_up_subtitle),
            bgColor = MaterialTheme.colors.background
        )

        EmailTextField(
            modifier = Modifier.constrainAs(emailTextField) {
                top.linkTo(welcomeCard.bottom, margin = 42.dp)
            },
            state = emailInputFieldState
        )

        Row(
            modifier = Modifier
                .constrainAs(nameLayout) {
                    top.linkTo(emailTextField.bottom, margin = 8.dp)
                }
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            SampleTextField(
                Modifier.weight(0.5f),
                label = "First Name",
                state = firstNameInputFieldState
            )

            SampleTextField(
                Modifier.weight(0.5f),
                label = "Last Name",
                state = lastNameInputFieldState
            )
        }

        PasswordTextField(
            modifier = Modifier.constrainAs(passwordTextField) {
                top.linkTo(nameLayout.bottom, margin = 8.dp)
            },
            state = passwordInputFieldState
        )

        CornerRoundedButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .constrainAs(signUpBtn) {
                    top.linkTo(passwordTextField.bottom, margin = 16.dp)
                },
            text = "Sign Up",
            appearance = CornerRoundedButtonAppearance.Filled,
            onClick = {
                onSignUpPressed()
            }
        )

        CornerRoundedButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .constrainAs(signInBtn) {
                    bottom.linkTo(parent.bottom, margin = 16.dp)
                },
            text = "Sign In",
            appearance = CornerRoundedButtonAppearance.Outlined,
            onClick = {
                onSignInPressed()
            }
        )
    }
}

@ExperimentalComposeUiApi
@Preview
@Composable
fun PreviewSignUpScreen() {
    SignUpScreen(
        navController = NavController(LocalContext.current)
    )
}