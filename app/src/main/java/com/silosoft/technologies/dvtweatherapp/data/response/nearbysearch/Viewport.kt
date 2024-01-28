package com.silosoft.technologies.dvtweatherapp.data.response.nearbysearch

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Viewport(
    val northeast: Northeast,
    val southwest: Southwest
)
