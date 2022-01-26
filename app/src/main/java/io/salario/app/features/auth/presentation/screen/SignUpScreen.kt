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
import io.salario.app.R
import io.salario.app.core.domain.model.UIEvent
import io.salario.app.core.navigation.Destination
import io.salario.app.core.shared_ui.composable.*
import io.salario.app.features.auth.presentation.event.SignUpEvent
import io.salario.app.features.auth.presentation.viewmodel.SignUpViewModel
import kotlinx.coroutines.flow.collect

@ExperimentalComposeUiApi
@Composable
fun SignUpScreen(
    navController: NavController,
    viewModel: SignUpViewModel = hiltViewModel()
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
            nameLayout,
            passwordTextField,
            signUpBtn,
            thirdPartySignInLayout,
            signInBtn) = createRefs()

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
                    viewModel.onEvent(SignUpEvent.OnDialogDismiss)
                }
            )
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
            state = viewModel.emailInputState
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
                state = viewModel.firstNameInputState
            )

            SampleTextField(
                Modifier.weight(0.5f),
                label = "Last Name",
                state = viewModel.lastNameInputState
            )
        }

        PasswordTextField(
            modifier = Modifier.constrainAs(passwordTextField) {
                top.linkTo(nameLayout.bottom, margin = 8.dp)
            },
            state = viewModel.passwordInputState
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
                viewModel.apply {
                    firstNameInputState.validate()
                    lastNameInputState.validate()
                    emailInputState.validate()
                    passwordInputState.validate()

                    if (firstNameInputState.hasNoError() &&
                        lastNameInputState.hasNoError() &&
                        emailInputState.hasNoError() &&
                        passwordInputState.hasNoError()
                    ) {
                        onEvent(
                            SignUpEvent.OnSignUpPressed(
                                firstNameInputState.text,
                                lastNameInputState.text,
                                emailInputState.text,
                                passwordInputState.text
                            )
                        )
                    }
                }
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
                viewModel.onEvent(SignUpEvent.OnSignInPressed)
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