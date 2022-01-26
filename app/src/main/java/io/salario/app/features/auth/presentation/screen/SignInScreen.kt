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
import io.salario.app.R
import io.salario.app.core.domain.model.UIEvent
import io.salario.app.core.navigation.Destination
import io.salario.app.core.shared_ui.composable.*
import io.salario.app.features.auth.presentation.event.SignInEvent
import io.salario.app.features.auth.presentation.viewmodel.SignInViewModel
import kotlinx.coroutines.flow.collect

@ExperimentalComposeUiApi
@Composable
fun SignInScreen(
    navController: NavController,
    viewModel: SignInViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UIEvent.Navigate -> {
                    navController.navigate(event.route) {
                        popUpTo(Destination.SignInDestination.route) {
                            inclusive = true
                        }
                    }
                }
                else -> Unit
            }
        }
    }

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

        if (viewModel.showWelcomeDialog) {
            WelcomeDialog()
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
                    viewModel.onEvent(SignInEvent.OnDialogDismiss)
                }
            )
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
            state = viewModel.emailInputState
        )

        PasswordTextField(
            modifier = Modifier.constrainAs(passwordTextField) {
                top.linkTo(emailTextField.bottom, margin = 8.dp)
            },
            state = viewModel.passwordInputState
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
                viewModel.emailInputState.validate()
                viewModel.passwordInputState.validate()

                if (viewModel.emailInputState.hasNoError() &&
                    viewModel.passwordInputState.hasNoError()
                ) {
                    viewModel.onEvent(
                        SignInEvent.OnSignInPressed(
                            viewModel.emailInputState.text,
                            viewModel.passwordInputState.text
                        )
                    )
                }
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
                    viewModel.emailInputState.apply {
                        validate()
                        if (hasNoError()) {
                            viewModel.onEvent(
                                SignInEvent.OnForgotPasswordPressed(text)
                            )
                        }
                    }
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
                viewModel.onEvent(SignInEvent.OnSignUpPressed)
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