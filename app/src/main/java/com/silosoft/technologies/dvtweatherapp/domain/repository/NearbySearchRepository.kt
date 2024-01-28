package com.silosoft.technologies.dvtweatherapp.domain.repository

import com.silosoft.technologies.dvtweatherapp.data.Result
import com.silosoft.technologies.dvtweatherapp.data.response.nearbysearch.NearbySearchResponse

interface NearbySearchRepository {
    suspend fun getNearbyRestaurants(location: String): Result<NearbySearchResponse>
}
