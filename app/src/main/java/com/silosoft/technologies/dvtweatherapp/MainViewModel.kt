package com.silosoft.technologies.dvtweatherapp

import android.location.Location
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.silosoft.technologies.dvtweatherapp.domain.repository.LocationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val locationRepository: LocationRepository
): ViewModel() {

    var currentLocation by mutableStateOf<Location?>(null)
        private set

    fun getCurrentLocation() = viewModelScope.launch {
        currentLocation = locationRepository.getCurrentLocation()
    }

}