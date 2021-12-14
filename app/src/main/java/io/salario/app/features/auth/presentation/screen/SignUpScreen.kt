package io.salario.app.features.auth.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.salario.app.R
import io.salario.app.core.customui.composable.*
import io.salario.app.core.customui.state_holder.TextFieldState
import io.salario.app.core.navigation.Destination
import io.salario.app.features.auth.presentation.viewmodel.SignUpViewModel

@Composable
fun SignUpScreen(navController: NavController, viewModel: SignUpViewModel = hiltViewModel()) {
    val state = viewModel.signUpState

    // TODO understand how to implement proper navigation once.
    LaunchedEffect(key1 = true) {
        if (state.shouldNavigateForward) {
            navController.navigate(Destination.EmailValidationDestination.route) {
                popUpTo(Destination.SignUpDestination.route) {
                    inclusive = true
                }
            }
        }
    }

    SignUpScreenContent(
        isLoading = state.isLoading,
        firstNameInputFieldState = state.firstNameInputState,
        lastNameInputFieldState = state.lastNameInputState,
        emailInputFieldState = state.emailInputState,
        passwordInputFieldState = state.passwordInputState,
        onSignUpPressed = {
            state.apply {
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

@Composable
fun SignUpScreenContent(
    isLoading: Boolean,
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
        val (loadingProgressBar,
            welcomeCard,
            emailTextField,
            nameLayout,
            passwordTextField,
            signUpBtn,
            thirdPartySignInLayout,
            signInBtn) = createRefs()

        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.constrainAs(loadingProgressBar) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            })
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
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            SampleTextField(
                Modifier.weight(1f),
                label = "First Name",
                state = firstNameInputFieldState
            )

            SampleTextField(
                Modifier.weight(1f),
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
                .constrainAs(signUpBtn) {
                    top.linkTo(passwordTextField.bottom, margin = 16.dp)
                }
                .padding(horizontal = 16.dp),
            text = "Sign Up",
            appearance = CornerRoundedButtonAppearance.Filled,
            onClick = {
                onSignUpPressed()
            }
        )

        CornerRoundedButton(
            modifier = Modifier
                .constrainAs(signInBtn) {
                    bottom.linkTo(parent.bottom, margin = 16.dp)
                }
                .padding(horizontal = 16.dp),
            text = "Sign In",
            appearance = CornerRoundedButtonAppearance.Outlined,
            onClick = {
                onSignInPressed()
            }
        )
    }
}

@Preview
@Composable
fun PreviewSignUpScreen() {
    SignUpScreen(
        navController = NavController(LocalContext.current)
    )
}