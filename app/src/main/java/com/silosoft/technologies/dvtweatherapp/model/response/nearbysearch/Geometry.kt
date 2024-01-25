package com.silosoft.technologies.dvtweatherapp.model.response.nearbysearch

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Geometry(
    val location: Location,
    val viewport: Viewport
)
