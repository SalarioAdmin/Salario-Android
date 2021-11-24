package io.salario.app.presentation.navigation

sealed class Destination(val route: String) {
    object IntroDestination : Destination("intro_screen")
    object SignInDestination : Destination("sign_in_screen")
    object SignUpDestination : Destination("sign_up_screen")
    object StatusDestination : Destination("status_screen")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}