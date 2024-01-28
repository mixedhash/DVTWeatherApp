package com.silosoft.technologies.dvtweatherapp.data.implementations

import com.silosoft.technologies.dvtweatherapp.data.Result
import com.silosoft.technologies.dvtweatherapp.data.response_model.nearbysearch.NearbySearchResponse
import com.silosoft.technologies.dvtweatherapp.domain.api.PlacesApi
import com.silosoft.technologies.dvtweatherapp.domain.repository.NearbySearchRepository
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class NearbySearchRepositoryImpl @Inject constructor(
    private val placesApi: PlacesApi
): NearbySearchRepository {
    override suspend fun getNearbyRestaurants(location: String): Result<NearbySearchResponse> =
        try {
            val response = placesApi.getNearbyRestaurants(location = location)
            if (response.isSuccessful) {
                response.body()?.let {
                    Result.Success(it)
                } ?: Result.Error(IOException("Response body is null"))
            } else {
                Result.Error(HttpException(response))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }

}
