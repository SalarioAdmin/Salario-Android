package io.salario.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Analytics
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import io.salario.app.core.navigation.Destination
import io.salario.app.core.navigation.bottom_navigation.BottomNavItem
import io.salario.app.core.navigation.bottom_navigation.BottomNavigationBar
import io.salario.app.core.navigation.nav_graph.Navigation
import io.salario.app.theme.SalarioTheme

@AndroidEntryPoint
class ContainerActivity : ComponentActivity() {

    @ExperimentalComposeUiApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SalarioTheme {
                val scaffoldState = rememberScaffoldState()
                var bottomBarVisibilityState by rememberSaveable { (mutableStateOf(false)) }
                val navController = rememberNavController()
                Scaffold(
                    Modifier.background(MaterialTheme.colors.background),
                    scaffoldState = scaffoldState,
                    bottomBar = if (bottomBarVisibilityState) {
                        {
                            BottomNavigationBar(
                                items = listOf(
                                    BottomNavItem(
                                        label = Destination.StatusDestination.label,
                                        route = Destination.StatusDestination.route,
                                        icon = Icons.Outlined.Home,
                                        selectedIcon = Icons.Filled.Home
                                    ),
                                    BottomNavItem(
                                        label = Destination.AnalyticsDestination.label,
                                        route = Destination.AnalyticsDestination.route,
                                        icon = Icons.Outlined.Analytics,
                                        selectedIcon = Icons.Filled.Analytics
                                    ),
                                    BottomNavItem(
                                        label = Destination.NotificationsDestination.label,
                                        route = Destination.NotificationsDestination.route,
                                        icon = Icons.Outlined.Notifications,
                                        selectedIcon = Icons.Filled.Notifications
                                    ),
                                    BottomNavItem(
                                        label = Destination.SettingsDestination.label,
                                        route = Destination.SettingsDestination.route,
                                        icon = Icons.Outlined.Settings,
                                        selectedIcon = Icons.Filled.Settings
                                    )
                                ),
                                navController = navController,
                                onItemClicked = { item ->
                                    navController.navigate(item.route)
                                }
                            )
                        }
                    } else {
                        {}
                    }
                ) {
                    Navigation(navController) {
                        bottomBarVisibilityState = it
                    }
                }
            }
        }
    }
}
