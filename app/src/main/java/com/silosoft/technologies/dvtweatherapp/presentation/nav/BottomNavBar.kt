package com.silosoft.technologies.dvtweatherapp.presentation.nav

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.silosoft.technologies.dvtweatherapp.presentation.Constants.HOME_BOTTOM_TAB_ROUTE
import com.silosoft.technologies.dvtweatherapp.R
import com.silosoft.technologies.dvtweatherapp.presentation.Constants.NEARBY_BOTTOM_TAB_ROUTE

@Composable
fun BottomNavBar(
    navController: NavController,
    onNavigateToNearbyScreen: () -> Unit
) {
    NavigationBar {
        val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
        Screen.entries.forEach { screen ->
            NavigationBarItem(
                selected = (currentRoute == screen.route),
                onClick = {
                    if (currentRoute != screen.route) {
                        if (screen.route == NEARBY_BOTTOM_TAB_ROUTE) onNavigateToNearbyScreen()
                        navController.navigate(screen.route) {
                            // Avoid multiple copies of the same destination
                            popUpTo(navController.graph.startDestinationId)
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                icon = { BottomTabIcon(screen) }
            )
        }
    }
}

@Composable
private fun BottomTabIcon(screen: Screen) =
    when (screen.route) {
        HOME_BOTTOM_TAB_ROUTE -> Icon(
            painter = painterResource(id = R.drawable.bottom_tab_home),
            contentDescription = "Main screen icon"
        )

        NEARBY_BOTTOM_TAB_ROUTE -> Icon(
            painter = painterResource(id = R.drawable.bottom_tab_maps),
            contentDescription = "Map screen icon"
        )

        else -> null
    }
