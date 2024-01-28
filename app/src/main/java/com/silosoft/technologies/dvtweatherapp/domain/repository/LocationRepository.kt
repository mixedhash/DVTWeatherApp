package com.silosoft.technologies.dvtweatherapp.domain.repository

interface LocationRepository {
    suspend fun getCurrentLocation(): Pair<Double, Double>?
}
