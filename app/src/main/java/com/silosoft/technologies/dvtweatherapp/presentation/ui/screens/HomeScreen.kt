package com.silosoft.technologies.dvtweatherapp.presentation.ui.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun HomeScreen(location: String) {
    Text(text = "This is $location")
}
