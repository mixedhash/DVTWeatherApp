package com.silosoft.technologies.dvtweatherapp.domain.api

import com.silosoft.technologies.dvtweatherapp.BuildConfig
import com.silosoft.technologies.dvtweatherapp.data.response.nearbysearch.NearbySearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PlacesApi {
    @GET("json")
    suspend fun getNearbyRestaurants(
        @Query("key") key: String = BuildConfig.GOOGLE_MAPS_APIS_KEY,
        @Query("location") location: String,
        @Query("type") units: String = "restaurant",
        @Query("radius") radius: String = "10000"
    ): Response<NearbySearchResponse>
}
