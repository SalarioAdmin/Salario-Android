package io.salario.app.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.salario.app.R
import io.salario.app.presentation.customui.CornerRoundedButton
import io.salario.app.presentation.customui.CornerRoundedButtonAppearance
import io.salario.app.presentation.customui.WelcomeCard
import io.salario.app.presentation.customui.textfields.EmailTextField
import io.salario.app.presentation.customui.textfields.PasswordTextField
import io.salario.app.presentation.customui.textfields.rememberTextFieldState
import io.salario.app.presentation.navigation.Destination
import io.salario.app.presentation.theme.White300
import io.salario.app.utils.*

@ExperimentalComposeUiApi
@Composable
fun SignInScreen(navController: NavController) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxHeight()
            .background(White300)
    ) {
        val (welcomeCard, emailTextField, passwordTextField, signInBtn) = createRefs()

        val (passwordFocusRequester) = FocusRequester.createRefs()

        WelcomeCard(
            modifier = Modifier.constrainAs(welcomeCard) {
                top.linkTo(parent.top)
            },
            title = stringResource(id = R.string.sign_in_title),
            subtitle = stringResource(id = R.string.sign_in_subtitle),
            bgColor = Color.White
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
            state = emailState,
            nextFocusRequester = passwordFocusRequester
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
            text = "Sign in",
            appearance = CornerRoundedButtonAppearance.Filled,
            onClick = {
                emailState.validate()
                passwordState.validate()

                if (emailState.error == null && passwordState.error == null) {
                    navController.navigate(Destination.StatusDestination.route)
                }
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