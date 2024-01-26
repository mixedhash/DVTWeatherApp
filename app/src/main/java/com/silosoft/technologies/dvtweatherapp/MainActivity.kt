package com.silosoft.technologies.dvtweatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
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
                        viewModel.getCurrentLocation()
                    }
                }

                val currentLocation = viewModel.currentLocation

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
                                Text(text = "${currentLocation?.latitude ?: 0.0} ${currentLocation?.longitude ?: 0.0}")
                                Button(
                                    onClick = { viewModel.getCurrentLocation() }
                                ) {
                                    Text(text = "Get current location")
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

//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colorScheme.background
//                ) {
//                    val navController = rememberNavController()
//
//                    Scaffold(bottomBar = { BottomNavBar(navController) }) {
//                        NavigationHost(navController, it)
//                    }
//                }
                }
            }
        }
    }
}
