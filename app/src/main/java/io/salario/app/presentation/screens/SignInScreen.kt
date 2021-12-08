package io.salario.app.presentation.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
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
import androidx.navigation.NavController
import com.salario.app.R
import io.salario.app.data.utils.Status
import io.salario.app.presentation.customui.WelcomeCard
import io.salario.app.presentation.customui.buttons.CornerRoundedButton
import io.salario.app.presentation.customui.buttons.CornerRoundedButtonAppearance
import io.salario.app.presentation.customui.textfields.EmailTextField
import io.salario.app.presentation.customui.textfields.PasswordTextField
import io.salario.app.presentation.customui.textfields.rememberTextFieldState
import io.salario.app.presentation.navigation.Destination
import io.salario.app.presentation.viewmodels.AuthenticationViewModel
import io.salario.app.utils.getPasswordValidationError
import io.salario.app.utils.isValidEmail
import kotlinx.coroutines.flow.collect

@Composable
fun SignInScreen(navController: NavController, authViewModel: AuthenticationViewModel) {
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        authViewModel.userData.collect {
            when (it.status) {
                is Status.Success -> {
                    navController.navigate(Destination.StatusDestination.route) {
                        popUpTo(Destination.SignInDestination.route) {
                            inclusive = true
                        }
                    }
                }
                is Status.Loading -> {
                    Toast
                        .makeText(context, "Loading...", Toast.LENGTH_SHORT)
                        .show()
                }
                is Status.Error -> {
                    Toast
                        .makeText(context, it.message, Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    LaunchedEffect(key1 = true) {
        authViewModel.resetPasswordResult.collect {
            when (it.status) {
                is Status.Success -> {
                    Toast
                        .makeText(context, it.message, Toast.LENGTH_SHORT)
                        .show()
                }
                is Status.Loading -> {
                    Toast
                        .makeText(context, "Loading...", Toast.LENGTH_SHORT)
                        .show()
                }
                is Status.Error -> {
                    Toast
                        .makeText(context, it.message, Toast.LENGTH_SHORT)
                        .show()
                }
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
                    authViewModel.signIn(emailState.text, passwordState.text)
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
                        authViewModel.resetPassword(emailState.text)
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
        authViewModel = AuthenticationViewModel()
    )
}