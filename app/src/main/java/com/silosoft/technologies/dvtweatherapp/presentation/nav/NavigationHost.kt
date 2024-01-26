package com.silosoft.technologies.dvtweatherapp.presentation.nav

import android.location.Location
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.silosoft.technologies.dvtweatherapp.presentation.ui.screens.FavouriteScreen
import com.silosoft.technologies.dvtweatherapp.presentation.ui.screens.HomeScreen
import com.silosoft.technologies.dvtweatherapp.presentation.ui.screens.MapScreen

@Composable
fun NavigationHost(
    navHostController: NavHostController,
    paddingValues: PaddingValues,
    currentLocation: Location,
) {
    NavHost(
        modifier = Modifier.padding(paddingValues),
        navController = navHostController,
        startDestination = Screen.HomeScreen.route
    ) {
        composable(Screen.HomeScreen.route) { HomeScreen(currentLocation.toString()) }
        composable(Screen.FavouriteScreen.route) { FavouriteScreen() }
        composable(Screen.MapScreen.route) { MapScreen() }
    }
}
