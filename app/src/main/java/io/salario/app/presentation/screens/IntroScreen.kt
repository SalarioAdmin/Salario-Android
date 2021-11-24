package io.salario.app.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants.IterateForever
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.salario.app.R
import io.salario.app.application.SalarioApplication
import io.salario.app.presentation.customui.CornerRoundedButton
import io.salario.app.presentation.customui.CornerRoundedButtonAppearance
import io.salario.app.presentation.navigation.Destination

@Composable
fun IntroScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center
    ) {
        val composition by rememberLottieComposition(
            spec = LottieCompositionSpec.RawRes(R.raw.welcome_lottie),
        )

        val progress by animateLottieCompositionAsState(
            composition = composition,
            iterations = IterateForever
        )

        LottieAnimation(
            composition = composition,
            progress = progress,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.35f)
        )

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(24.dp)
        )

        Text(
            text = stringResource(id = R.string.intro_title_text),
            style = MaterialTheme.typography.h1,
            modifier = Modifier.padding(horizontal = 8.dp)
        )

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
        )

        Text(
            text = stringResource(id = R.string.intro_subtitle_text),
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier.padding(horizontal = 8.dp)
        )

        // TODO stick to bottom
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
        )

        CornerRoundedButton(
            text = stringResource(id = R.string.intro_sign_up_btn_text),
            appearance = CornerRoundedButtonAppearance.Filled,
            onClick = {
                navController.navigate(Destination.SignUpDestination.route)
            })

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
        )

        CornerRoundedButton(
            text = stringResource(id = R.string.intro_sign_in_btn_text),
            appearance = CornerRoundedButtonAppearance.Outlined,
            onClick = {
                navController.navigate(Destination.SignInDestination.route)
            })
    }
}

@Preview
@Composable
fun ComposablePreview() {
    IntroScreen(NavController(context = SalarioApplication.INSTANCE))
}