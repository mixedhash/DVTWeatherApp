package com.silosoft.technologies.dvtweatherapp.domain.repository

import com.silosoft.technologies.dvtweatherapp.data.response_model.nearbysearch.NearbySearchResponse

interface NearbySearchRepository {
    suspend fun getNearbyRestaurants(location: String): NearbySearchResponse?
}
