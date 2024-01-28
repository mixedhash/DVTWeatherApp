package com.silosoft.technologies.dvtweatherapp.data.response.nearbysearch

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Geometry(
    val location: Location,
    val viewport: Viewport
)
