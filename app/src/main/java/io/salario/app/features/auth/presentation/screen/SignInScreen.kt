package io.salario.app.features.auth.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
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
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.salario.app.R
import io.salario.app.core.customui.WelcomeCard
import io.salario.app.core.customui.buttons.CornerRoundedButton
import io.salario.app.core.customui.buttons.CornerRoundedButtonAppearance
import io.salario.app.core.customui.textfields.EmailTextField
import io.salario.app.core.customui.textfields.PasswordTextField
import io.salario.app.core.customui.textfields.rememberTextFieldState
import io.salario.app.core.navigation.Destination
import io.salario.app.core.util.getPasswordValidationError
import io.salario.app.core.util.isValidEmail
import io.salario.app.features.auth.presentation.viewmodel.AuthViewModel

@Composable
fun SignInScreen(navController: NavController, viewModel: AuthViewModel) {
    val state = viewModel.signInState.value

    LaunchedEffect(Unit) {
        if (viewModel.signInState.value.signInSuccess) {
            navController.navigate(Destination.StatusDestination.route) {
                popUpTo(Destination.SignInDestination.route) {
                    inclusive = true
                }
            }
        }
    }

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

        if (state.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .constrainAs(loadingProgressBar) {
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
            title = stringResource(id = R.string.sign_in_title),
            subtitle = stringResource(id = R.string.sign_in_subtitle),
            bgColor = MaterialTheme.colors.background
        )

        val emailState = rememberTextFieldState(
            initialText = "",
            validate = { email ->
                when {
                    email.isEmpty() || email.isBlank() -> "Email should not be empty"
                    !isValidEmail(email) -> "Please enter a valid Email address"
                    else -> null
                }
            })

        EmailTextField(
            modifier = Modifier.constrainAs(emailTextField) {
                top.linkTo(welcomeCard.bottom, margin = 42.dp)
            },
            state = emailState
        )

        val passwordState = rememberTextFieldState(
            initialText = "",
            validate = { password ->
                if (password.isEmpty() || password.isBlank()) {
                    "Password should not be empty"
                } else {
                    getPasswordValidationError(password)
                }
            })

        PasswordTextField(
            modifier = Modifier.constrainAs(passwordTextField) {
                top.linkTo(emailTextField.bottom, margin = 8.dp)
            },
            state = passwordState
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
                emailState.validate()
                passwordState.validate()

                if (emailState.error == null && passwordState.error == null) {
                    viewModel.onSignIn(emailState.text, passwordState.text)
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
                    emailState.validate()
                    if (emailState.error == null) {
                        viewModel.onResetPassword(emailState.text)
                    }
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
                navController.navigate(Destination.SignUpDestination.route) {
                    popUpTo(Destination.SignInDestination.route) {
                        inclusive = true
                    }
                }
            }
        )
    }
}

@Preview
@Composable
fun PreviewSignInScreen() {
    SignInScreen(
        navController = NavController(LocalContext.current),
        viewModel = hiltViewModel()
    )
}