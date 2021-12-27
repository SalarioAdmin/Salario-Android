package io.salario.app.core.navigation

sealed class Destination(val route: String, val label: String = "") {

    // Splash
    object SplashDestination : Destination("splash_screen")

    // Auth
    object IntroDestination : Destination("intro_screen")
    object SignInDestination : Destination("sign_in_screen")
    object SignUpDestination : Destination("sign_up_screen")

    // Features
    object StatusDestination : Destination("status_screen", "Home")
    object AnalyticsDestination : Destination("analytics_screen", "Analytics")
    object NotificationsDestination : Destination("notification_screen", "Notifications")
    object SettingsDestination : Destination("settings_screen", "Settings")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}