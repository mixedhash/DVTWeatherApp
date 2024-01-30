package com.silosoft.technologies.dvtweatherapp.presentation.nav

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.silosoft.technologies.dvtweatherapp.domain.model.ForecastUiModel
import com.silosoft.technologies.dvtweatherapp.domain.model.NearbyRestaurantsUiModel
import com.silosoft.technologies.dvtweatherapp.domain.model.WeatherUiModel
import com.silosoft.technologies.dvtweatherapp.presentation.ui.screens.NearbyScreen
import com.silosoft.technologies.dvtweatherapp.presentation.ui.screens.HomeScreen

@Composable
fun NavigationHost(
    navHostController: NavHostController,
    paddingValues: PaddingValues,
    timestampState: String,
    weatherState: WeatherUiModel?,
    forecastState: ForecastUiModel?,
    nearbyRestaurantsState: NearbyRestaurantsUiModel?
) {
    NavHost(
        modifier = Modifier.padding(paddingValues),
        navController = navHostController,
        startDestination = Screen.HomeScreen.route
    ) {
        composable(Screen.HomeScreen.route) { HomeScreen(timestampState, weatherState, forecastState) }
        composable(Screen.NearbyScreen.route) { NearbyScreen(nearbyRestaurantsState) }
    }
}
