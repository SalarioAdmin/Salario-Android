package io.salario.app.core.customui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun WelcomeCard(title: String, subtitle: String, bgColor: Color, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(bgColor, RoundedCornerShape(bottomEnd = 56.dp))
            .padding(horizontal = 16.dp)
    ) {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(32.dp)
        )

        Text(
            text = title,
            style = MaterialTheme.typography.h1,
            color = MaterialTheme.colors.primary
        )

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(16.dp)
        )

        Text(
            text = subtitle,
            style = MaterialTheme.typography.subtitle1
        )

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(32.dp)
        )
    }
}

@Preview
@Composable
fun PreviewWelcomeCard() {
    Column(
        verticalArrangement = Arrangement.Top,
        modifier = Modifier.fillMaxHeight()
    ) {
        WelcomeCard(
            title = "Welcome",
            subtitle = "Hello",
            bgColor = Color.White
        )
    }
}