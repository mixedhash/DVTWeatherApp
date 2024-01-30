package com.silosoft.technologies.dvtweatherapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.silosoft.technologies.dvtweatherapp.presentation.nav.BottomNavBar
import com.silosoft.technologies.dvtweatherapp.presentation.nav.NavigationHost
import com.silosoft.technologies.dvtweatherapp.presentation.ui.theme.DVTWeatherAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MainViewModel>()

    @OptIn(ExperimentalPermissionsApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DVTWeatherAppTheme {
                val locationPermissions = rememberMultiplePermissionsState(
                    permissions = listOf(
                        android.Manifest.permission.ACCESS_COARSE_LOCATION,
                        android.Manifest.permission.ACCESS_FINE_LOCATION
                    )
                )

                LaunchedEffect(key1 = locationPermissions.allPermissionsGranted) {
                    if (locationPermissions.allPermissionsGranted) {
                        viewModel.onEvent(MainViewModel.Event.OnFetchLocation)
                    }
                }

                val currentLocation = viewModel.locationState.collectAsState()

                LaunchedEffect(key1 = currentLocation.value != null) {
                    if (currentLocation.value != null) {
                        viewModel.onEvent(MainViewModel.Event.OnFetchWeatherData)
                        viewModel.onEvent(MainViewModel.Event.OnFetchForecastData)
                    }
                }

                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    AnimatedContent(
                        targetState = locationPermissions.allPermissionsGranted, label = ""
                    ) { areGranted ->
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            if (areGranted) {
                                Surface(
                                    modifier = Modifier.fillMaxSize(),
                                    color = MaterialTheme.colorScheme.background
                                ) {
                                    val navController = rememberNavController()

                                    Scaffold(bottomBar = { BottomNavBar(navController) }) { paddings ->
                                        if (currentLocation.value != null) {
                                            val weatherState =
                                                viewModel.weatherState.collectAsState()
                                            val forecastState =
                                                viewModel.forecastState.collectAsState()
                                            val timestampState =
                                                viewModel.timestampState.collectAsState()

                                            NavigationHost(
                                                navHostController = navController,
                                                paddingValues = paddings,
                                                timestampState = timestampState.value ?: "Not available",
                                                weatherState = weatherState.value,
                                                forecastState = forecastState.value
                                            )
                                        } else {
                                            Text(text = "Couldn't retrieve location, something went wrong!")
                                        }
                                    }
                                }
                            } else {
                                Text(text = "We need location permissions for this application.")
                                Button(
                                    onClick = { locationPermissions.launchMultiplePermissionRequest() }
                                ) {
                                    Text(text = "Accept")
                                }
                            }
                        }
                    }
                }

                if (viewModel.displayErrorToast.collectAsState().value) {
                    displayErrorToast()
                    viewModel.onEvent(MainViewModel.Event.OnDisplayErrorToastFinished)
                }
            }
        }
    }

    private fun displayErrorToast() =
        Toast.makeText(this, "Something went wrong!", Toast.LENGTH_LONG).show()
}
