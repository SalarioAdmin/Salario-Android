package io.salario.app.presentation.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.salario.app.R
import io.salario.app.application.SalarioApplication
import io.salario.app.presentation.customui.WelcomeCard
import io.salario.app.presentation.customui.buttons.CornerRoundedButton
import io.salario.app.presentation.customui.buttons.CornerRoundedButtonAppearance
import io.salario.app.presentation.customui.textfields.EmailTextField
import io.salario.app.presentation.customui.textfields.PasswordTextField
import io.salario.app.presentation.customui.textfields.rememberTextFieldState
import io.salario.app.presentation.navigation.Destination
import io.salario.app.utils.*

@Composable
fun SignInScreen(navController: NavController) {
    val context = LocalContext.current

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
                    navController.navigate(Destination.StatusDestination.route)
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
                    Toast
                        .makeText(context, "TODO", Toast.LENGTH_SHORT)
                        .show()
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
                // TODO show dialog that all data will be deleted
                navController.navigate(Destination.SignUpDestination.route)
            }
        )
    }
}

fun getPasswordValidationError(password: String): String? {
    return when (isValidPassword(password)) {
        is PasswordValidationResult.PasswordOk -> {
            null
        }

        is PasswordValidationResult.ErrorPasswordTooShort -> {
            "Password should be minimum " +
                    "$MIN_PASSWORD_LENGTH characters long."
        }

        is PasswordValidationResult.ErrorPasswordTooLong -> {
            "Password should be maximum " +
                    "$MAX_PASSWORD_LENGTH characters long."
        }

        is PasswordValidationResult.ErrorPasswordShouldContainLetters -> {
            "Password should contain letters."
        }

        is PasswordValidationResult.ErrorPasswordShouldContainCapitalLetter -> {
            "Password should contain at least one capital letter."
        }

        is PasswordValidationResult.ErrorPasswordShouldContainLowerCaseLetter -> {
            "Password should contain at least one small letter."
        }

        is PasswordValidationResult.ErrorPasswordShouldContainNumber -> {
            "Password should contain at least one number"
        }

        is PasswordValidationResult.ErrorPasswordShouldContainSpecialCharacter -> {
            "Password should contain at least one special character"
        }
    }
}

@Preview
@Composable
fun PreviewSignInScreen() {
    SignInScreen(navController = NavController(SalarioApplication.INSTANCE))
}