package com.silosoft.technologies.dvtweatherapp.model.response.nearbysearch

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Northeast(
    val lat: Double,
    val lng: Double
)
