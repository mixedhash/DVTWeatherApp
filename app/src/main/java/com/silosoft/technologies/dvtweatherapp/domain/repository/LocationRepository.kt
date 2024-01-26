package com.silosoft.technologies.dvtweatherapp.domain.repository

import android.location.Location

interface LocationRepository {
    suspend fun getCurrentLocation(): Location?
}
