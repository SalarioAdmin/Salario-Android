package io.salario.app.features.auth.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
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
import io.salario.app.core.customui.textfields.SampleTextField
import io.salario.app.core.customui.textfields.rememberTextFieldState
import io.salario.app.core.navigation.Destination
import io.salario.app.core.util.getPasswordValidationError
import io.salario.app.core.util.isValidEmail
import io.salario.app.features.auth.presentation.viewmodel.AuthViewModel

@Composable
fun SignUpScreen(navController: NavController, viewModel: AuthViewModel) {
    if (viewModel.signUpState.value.shouldNavigateToValidation) {
        navController.navigate(Destination.EmailValidationDestination.route) {
            popUpTo(Destination.SignUpDestination.route) {
                inclusive = true
            }
        }
        viewModel.signUpState.value.shouldNavigateToValidation = false
    }

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

        if (viewModel.signUpState.value.isLoading) {
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
            modifier = Modifier.constrainAs(emailTextField) {
                top.linkTo(welcomeCard.bottom, margin = 42.dp)
            },
            state = emailState
        )

        val firstNameState = rememberTextFieldState(
            initialText = "",
            validate = { firstName ->
                when {
                    firstName.isEmpty() || firstName.isBlank() -> "First Name should not be empty."
                    else -> null
                }
            })

        val lastNameState = rememberTextFieldState(
            initialText = "",
            validate = { lastName ->
                when {
                    lastName.isEmpty() || lastName.isBlank() -> "Last Name should not be empty."
                    else -> null
                }
            })

        Row(
            modifier = Modifier
                .constrainAs(nameLayout) {
                    top.linkTo(emailTextField.bottom, margin = 8.dp)
                }
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            SampleTextField(Modifier.weight(1f), label = "First Name", state = firstNameState)
            SampleTextField(Modifier.weight(1f), label = "Last Name", state = lastNameState)
        }

        val passwordState = rememberTextFieldState(
            initialText = "",
            validate = { password ->
                if (password.isEmpty() || password.isBlank()) {
                    "Password should not be empty."
                } else {
                    getPasswordValidationError(password)
                }
            })

        PasswordTextField(
            modifier = Modifier.constrainAs(passwordTextField) {
                top.linkTo(nameLayout.bottom, margin = 8.dp)
            },
            state = passwordState
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
                emailState.validate()
                passwordState.validate()
                firstNameState.validate()
                lastNameState.validate()

                if (emailState.error == null &&
                    passwordState.error == null &&
                    firstNameState.error == null &&
                    lastNameState.error == null
                ) {
                    viewModel.onSignUp(
                        firstNameState.text,
                        lastNameState.text,
                        emailState.text,
                        passwordState.text
                    )
                }
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
                // TODO show dialog that all data will be deleted
                navController.navigate(Destination.SignInDestination.route) {
                    popUpTo(Destination.SignUpDestination.route) {
                        inclusive = true
                    }
                }
            }
        )
    }
}

@Preview
@Composable
fun PreviewSignUpScreen() {
    SignUpScreen(
        navController = NavController(LocalContext.current),
        viewModel = hiltViewModel()
    )
}