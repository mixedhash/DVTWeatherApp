package com.silosoft.technologies.dvtweatherapp.data.implementations

import com.silosoft.technologies.dvtweatherapp.data.Result
import com.silosoft.technologies.dvtweatherapp.data.response_model.nearbysearch.NearbySearchResponse
import com.silosoft.technologies.dvtweatherapp.domain.api.PlacesApi
import com.silosoft.technologies.dvtweatherapp.domain.repository.NearbySearchRepository
import timber.log.Timber
import javax.inject.Inject

class NearbySearchRepositoryImpl @Inject constructor(
    private val placesApi: PlacesApi
): NearbySearchRepository {
    override suspend fun getNearbyRestaurants(location: String): NearbySearchResponse? {
        return try {
            when (val response = placesApi.getNearbyRestaurants(location = location)) {
                is Result.Success -> response.data
                is Result.Error -> throw Exception(response.error)
            }
        } catch (e: Exception) {
            Timber.e(e)
            null
        }
    }
}
