package io.salario.app.presentation.screens

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants.IterateForever
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.salario.app.R
import io.salario.app.presentation.customui.CornerRoundedButton
import io.salario.app.presentation.customui.CornerRoundedButtonAppearance
import io.salario.app.presentation.navigation.Destination

@Composable
fun IntroScreen(navController: NavController) {
    val composition by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(R.raw.welcome_lottie),
    )

    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = IterateForever
    )

    ConstraintLayout(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxHeight()
    ) {
        val (lottieAnimation, title, subtitle, signUpBtn, signInBtn) = createRefs()

        LottieAnimation(
            composition = composition,
            progress = progress,
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
                navController.navigate(Destination.SignUpDestination.route)
            },
            modifier = Modifier.constrainAs(signUpBtn) {
                bottom.linkTo(signInBtn.top)
            })

        CornerRoundedButton(
            text = stringResource(id = R.string.intro_sign_in_btn_text),
            appearance = CornerRoundedButtonAppearance.Outlined,
            onClick = {
                navController.navigate(Destination.SignInDestination.route)
            },
            modifier = Modifier
                .constrainAs(signInBtn) {
                    bottom.linkTo(parent.bottom, margin = 16.dp)
                })
    }
}