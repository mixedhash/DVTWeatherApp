package com.silosoft.technologies.dvtweatherapp.data.implementations

import com.silosoft.technologies.dvtweatherapp.data.Result
import com.silosoft.technologies.dvtweatherapp.data.response.nearbysearch.NearbySearchResponse
import com.silosoft.technologies.dvtweatherapp.domain.api.PlacesApi
import com.silosoft.technologies.dvtweatherapp.domain.repository.DataStoreRepository
import com.silosoft.technologies.dvtweatherapp.domain.repository.NearbySearchRepository
import kotlinx.coroutines.flow.firstOrNull
import okio.IOException
import timber.log.Timber
import javax.inject.Inject

class NearbySearchRepositoryImpl @Inject constructor(
    private val placesApi: PlacesApi,
    private val dataStoreRepository: DataStoreRepository
) : NearbySearchRepository {
    override suspend fun getNearbyRestaurants(location: String): Result<NearbySearchResponse> =
        try {
            val response = placesApi.getNearbyRestaurants(location = location)
            if (response.isSuccessful) {
                response.body()?.let { responseData ->
                    println(response.body())
                    if (responseData.results.isEmpty()) {
                        Result.Error(IOException("No results"))
                    }
                    dataStoreRepository.storeNearbySearch(responseData)
                    Result.Success(responseData)
                } ?: Result.Error(IOException("Response body is null"))
            } else {
                fetchNearbyRestaurantsFromDataStore()
            }
        } catch (e: Exception) {
            Timber.e(e)
            fetchNearbyRestaurantsFromDataStore()
        }

    private suspend fun fetchNearbyRestaurantsFromDataStore(): Result<NearbySearchResponse> {
        val lastNearbySearchResponse = dataStoreRepository.getNearbySearch().firstOrNull()

        return if (lastNearbySearchResponse != null) {
            Result.Success(lastNearbySearchResponse)
        } else {
            Timber.e("No nearby search response yet in data store!")
            Result.Error(IOException("No data available locally"))
        }
    }
}
