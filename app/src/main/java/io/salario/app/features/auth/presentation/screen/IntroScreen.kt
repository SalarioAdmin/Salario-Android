package io.salario.app.features.auth.presentation.screen

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.airbnb.lottie.LottieComposition
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants.IterateForever
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.salario.app.R
import io.salario.app.core.navigation.Destination
import io.salario.app.core.shared_ui.composable.CornerRoundedButton
import io.salario.app.core.shared_ui.composable.CornerRoundedButtonAppearance

@Composable
fun IntroScreen(navController: NavController) {
    val composition by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(R.raw.welcome_animation),
    )

    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = IterateForever
    )

    IntroScreenContent(
        lottieComposition = composition,
        lottieProgress = progress,
        onSignInPressed = {
            navController.navigate(Destination.SignUpDestination.route)
        },
        onSignUpPressed = {
            navController.navigate(Destination.SignInDestination.route)
        }
    )
}

@Composable
fun IntroScreenContent(
    lottieComposition: LottieComposition?,
    lottieProgress: Float,
    onSignInPressed: () -> Unit,
    onSignUpPressed: () -> Unit
) {
    ConstraintLayout(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxHeight()
    ) {
        val (lottieAnimation, title, subtitle, signUpBtn, signInBtn) = createRefs()

        LottieAnimation(
            composition = lottieComposition,
            progress = lottieProgress,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.35f)
                .constrainAs(lottieAnimation) {
                    top.linkTo(parent.top, margin = 64.dp)
                }
        )

        Text(
            text = stringResource(id = R.string.intro_title_text),
            style = MaterialTheme.typography.h1,
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .constrainAs(title) {
                    top.linkTo(lottieAnimation.bottom, margin = 32.dp)
                }
        )

        Text(
            text = stringResource(id = R.string.intro_subtitle_text),
            style = MaterialTheme.typography.subtitle2,
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .constrainAs(subtitle) {
                    top.linkTo(title.bottom, margin = 8.dp)
                }
        )

        CornerRoundedButton(
            text = stringResource(id = R.string.intro_sign_up_btn_text),
            appearance = CornerRoundedButtonAppearance.Filled,
            onClick = {
                onSignInPressed()
            },
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(signUpBtn) {
                bottom.linkTo(signInBtn.top, margin = 8.dp)
            })

        CornerRoundedButton(
            text = stringResource(id = R.string.intro_sign_in_btn_text),
            appearance = CornerRoundedButtonAppearance.Outlined,
            onClick = {
                onSignUpPressed()
            },
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(signInBtn) {
                    bottom.linkTo(parent.bottom, margin = 16.dp)
                })
    }
}

@Preview
@Composable
fun PreviewIntroScreen() {
    IntroScreen(navController = NavController(LocalContext.current))
}